package fr.eno.starlight.entity;

import java.util.Random;

import fr.eno.starlight.init.InitDimensions;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitItems;
import fr.eno.starlight.item.StarControllerItem;
import fr.eno.starlight.item.StarWandItem;
import fr.eno.starlight.packets.DisplaySpeechScreenPacket;
import fr.eno.starlight.packets.DisplayTravelScreenPacket;
import fr.eno.starlight.packets.NetworkManager;
import fr.eno.starlight.utils.TravelUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class StarEntity extends LivingEntity implements IRendersAsItem
{
	private static final DataParameter<Boolean> HAS_REWARD = EntityDataManager.createKey(StarEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_TALKING = EntityDataManager.createKey(StarEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(StarEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Float> SIZE = EntityDataManager.createKey(StarEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> REQUEST_COOLDOWN = EntityDataManager.createKey(StarEntity.class, DataSerializers.VARINT);

	public StarEntity(EntityType<? extends StarEntity> type, World world)
	{
		super(type, world);
	}

	public StarEntity(World world)
	{
		this(InitEntities.STAR.get(), world);
	}

	public StarEntity(World world, double posX, double posY, double posZ, double accelerationX, double accelerationY, double accelerationZ)
	{
		this(InitEntities.STAR.get(), world);
		this.setLocationAndAngles(posX, posY, posZ, this.rotationYaw, this.rotationPitch);
		this.setPosition(posX, posY, posZ);
	}

	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(LEVEL, 0);
		this.dataManager.register(HAS_REWARD, true);
		this.dataManager.register(SIZE, 6.0F);
		this.dataManager.register(IS_TALKING, false);
		this.dataManager.register(REQUEST_COOLDOWN, 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox()
	{
		float size = 3F;
		return new AxisAlignedBB(this.getPosX() - size / 2, this.getPosY(), this.getPosZ() - size / 2, this.getPosX() + size / 2, this.getPosY() + size / 2, this.getPosZ() + size / 2);
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand)
	{
		if (hand == Hand.OFF_HAND)
			return false;

		ItemStack stack = player.getHeldItem(hand);
		
		if(stack.getItem() instanceof StarWandItem)
		{
			return false;
		}

		if (stack.getItem() instanceof StarControllerItem)
		{
			return this.getPassengers().isEmpty() ? player.startRiding(this) : false;
		}
		
		if (!world.isRemote)
		{
			NetworkManager.getNetwork().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new DisplaySpeechScreenPacket(this.dimension.getRegistryName(), this.getUniqueID()));
			return true;
		}

		return false;
	}

	public void growUp()
	{
		this.setLevel(this.getLevel() + 1);
		this.setSize(this.getSize() + 3F);
	}

	@Override
	protected int calculateFallDamage(float p_225508_1_, float p_225508_2_)
	{
		return 0;
	}

	public boolean canBeSteered()
	{
		return true;
	}

	@Override
	public boolean canPassengerSteer()
	{
		if (this.getControllingPassenger() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) this.getControllingPassenger();

			if (player.getHeldItemMainhand().getItem() instanceof StarControllerItem && !isTalking())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	/*
	 * Sorry this ugly code :(
	 */	
	@Override
	public void livingTick()
	{
		if (this.isAlive())
		{
			if (!this.hasNoGravity())
				this.setNoGravity(true);
			
			Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

			if (this.isBeingRidden() && this.canBeSteered() && this.canPassengerSteer() && this.isOnePlayerRiding() && !isTalking())
			{
				PlayerEntity player = (PlayerEntity) entity;
				Vec3d vec = this.getMotion();
				double x = this.getPosX() + vec.x;
				double y = this.getPosY() + vec.y;
				double z = this.getPosZ() + vec.z;
				this.setMotion(player.getLookVec().scale(0.5F));
				this.updatePassenger(player);
				this.setPositionAndRotation(x, y, z, player.rotationYaw, player.rotationPitch);
				this.fallDistance = 0F;
				player.fallDistance = 0F;
			}
			else
			{
				this.setMotion(Vec3d.ZERO);
			}
		}
		
		if (!world.isRemote)
		{
			this.decreaseRequestCooldown();

			if (this.canTellToPlayer() && this.hasLevel())
			{
				this.tellToPlayer();
			}
		}

		super.livingTick();
	}

	private void tellToPlayer()
	{
		NetworkManager.getNetwork().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new DisplayTravelScreenPacket(TravelUtils.getDimensionByLevel(this.getLevel()).getId(), this.getUniqueID()));
		this.setTalking(true);
		this.setRequestCooldown(600);
		this.setMotion(0D, 0D, 0D);
	}
	
	private boolean hasLevel()
	{
		return this.getLevel() > TravelUtils.getLevelByDimension(this.dimension);
	}

	private boolean canTellToPlayer()
	{
		return this.getMotion().y > 0 && canTalk() && this.getPosY() > 200 && !world.isRemote && !isTalking();
	}

	public boolean canTalk()
	{

		return this.getRequestCooldown() <= 0 && !this.getPassengers().isEmpty();
	}

	public boolean isTalking()
	{
		return this.dataManager.get(IS_TALKING);
	}

	public void setTalking(boolean isTalking)
	{
		this.dataManager.set(IS_TALKING, isTalking);
	}

	public void setRequestCooldown(int cooldown)
	{
		this.dataManager.set(REQUEST_COOLDOWN, cooldown);
	}

	public int getRequestCooldown()
	{
		return this.dataManager.get(REQUEST_COOLDOWN);
	}

	public void decreaseRequestCooldown()
	{
		if (this.getRequestCooldown() > 0)
		{
			this.dataManager.set(REQUEST_COOLDOWN, this.dataManager.get(REQUEST_COOLDOWN) - 1);
		}
	}

	public boolean canGiveReward()
	{
		return this.hasReward() && this.dimension == InitDimensions.UTOPIAN_DIM_TYPE;
	}

	public boolean hasReward()
	{
		return this.dataManager.get(HAS_REWARD);
	}

	public void setNoReward()
	{
		this.dataManager.set(HAS_REWARD, false);
	}

	@Override
	public EntitySize getSize(Pose poseIn)
	{
		return new EntitySize(1, this.getSize() / 5F, false);
	}

	@Override
	public ItemStack getItem()
	{
		return new ItemStack(InitItems.STAR.get());
	}

	public float getSize()
	{
		return this.dataManager.get(SIZE);
	}

	private void setSize(float size)
	{
		this.dataManager.set(SIZE, size);
	}

	private void setLevel(int level)
	{
		this.dataManager.set(LEVEL, level);
	}

	public int getLevel()
	{
		return this.dataManager.get(LEVEL);
	}

	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putInt("Level", this.getLevel());
		compound.putBoolean("isTalking", this.isTalking());
		compound.putInt("RequestCooldown", this.getRequestCooldown());
		compound.putFloat("Size", this.getSize());
	}

	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);

		if (compound.contains("Size"))
		{
			this.setSize(compound.getFloat("Size"));
		}

		if (compound.contains("isTalking"))
		{
			this.setTalking(compound.getBoolean("isTalking"));
		}

		if (compound.contains("RequestCooldown"))
		{
			this.setRequestCooldown(compound.getInt("RequestCooldown"));
		}

		if (compound.contains("Size"))
		{
			this.setSize(compound.getFloat("Size"));
		}
	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	public static boolean getPlacement(EntityType<StarEntity> entity, IWorld world, SpawnReason reason, BlockPos pos, Random random)
	{
		return random.nextInt(2) == 0 && pos.getY() > 100 && world.getWorld().isNightTime();
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList()
	{
		final Iterable<ItemStack> list = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
		return list;
	}

	@Override
	public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack)
	{
	}

	@Override
	public HandSide getPrimaryHand()
	{
		return HandSide.RIGHT;
	}
}