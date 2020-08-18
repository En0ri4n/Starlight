package fr.eno.starlight.entity;

import java.util.Random;

import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitItems;
import fr.eno.starlight.init.InitPackets;
import fr.eno.starlight.item.StarWandItem;
import fr.eno.starlight.packets.DisplayScreenPacket;
import fr.eno.starlight.utils.ScreenTravelManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

public class StarEntity extends MobEntity implements IRendersAsItem
{
	private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(StarEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Float> SIZE = EntityDataManager.createKey(StarEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> IS_TALKING = EntityDataManager.createKey(StarEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> REQUEST_COOLDOWN = EntityDataManager.createKey(StarEntity.class, DataSerializers.VARINT);
	

	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;

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
		double size = (double) MathHelper.sqrt(accelerationX * accelerationX + accelerationY * accelerationY + accelerationZ * accelerationZ);
		this.accelerationX = accelerationX / size * 0.1D;
		this.accelerationY = accelerationY / size * 0.1D;
		this.accelerationZ = accelerationZ / size * 0.1D;
	}
	
	@Override
	protected void registerData()
	{
		super.registerData();
		this.dataManager.register(LEVEL, 0);
		this.dataManager.register(SIZE, 3.0F);
		this.dataManager.register(IS_TALKING, false);
		this.dataManager.register(REQUEST_COOLDOWN, 0);
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn)
	{
		float size = this.getSize() / 10;
		return new AxisAlignedBB(this.getPosX() - size, this.getPosY(), this.getPosZ() - size, this.getPosX() + size, this.getPosY() + size * 2F, this.getPosZ() + size);
	}

	@Override
	public boolean processInteract(PlayerEntity player, Hand hand)
	{
		Item item = player.getHeldItem(hand).getItem();
		
		if(item instanceof StarWandItem)
		{
			if(((StarWandItem) item).getLevel() == this.getLevel())
			{
				this.setLevel(this.getLevel() + 1);
				this.setSize(this.getSize() + 2F);
				return true;
			}
		}
		
		return this.getPassengers().isEmpty() ? player.startRiding(this) : false;
	}

	@Override
	public void travel(Vec3d vec)
	{
		if (this.isAlive())
		{
			Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
			if (this.isBeingRidden() && this.canBeSteered())
			{
				this.prevRotationYaw = this.rotationYaw;
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.renderYawOffset = this.rotationYaw;
				this.rotationYawHead = this.rotationYaw;

				if (this.canPassengerSteer())
				{

					this.setAIMoveSpeed(3F);
					super.travel(entity.getLookVec());
					this.newPosRotationIncrements = 0;
				} else
				{
					this.setMotion(Vec3d.ZERO);
				}
			} else
			{
				super.travel(vec);
			}
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isInvulnerableTo(source))
		{
			return false;
		}
		else
		{
			this.markVelocityChanged();
			if (source.getTrueSource() != null)
			{
				Vec3d vec3d = source.getTrueSource().getLookVec();
				this.setMotion(vec3d);
				this.accelerationX = vec3d.x * 0.1D;
				this.accelerationY = vec3d.y * 0.1D;
				this.accelerationZ = vec3d.z * 0.1D;

				return true;
			} else
			{
				return false;
			}
		}
	}

	@Override
	public boolean canBeSteered()
	{
		if (this.getControllingPassenger() instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) this.getControllingPassenger();

			if (player.getHeldItemMainhand().getItem() == Items.STICK && !isTalking())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canPassengerSteer()
	{
		return true;
	}

	@Override
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
	}

	@Override
	public void tick()
	{
		super.tick();
		
		this.decreaseRequestCooldown();
		
		if(this.canTellToPlayer())
		{
			this.tellToPlayer();
		}
	}
	
	private void tellToPlayer()
	{
		InitPackets.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) this.getPassengers().get(0)), new DisplayScreenPacket(ScreenTravelManager.getDimensionByLevel(this.getLevel()).getId(), this.getUniqueID()));
		this.setTalking(true);
		this.setRequestCooldown(600);
		this.setMotion(0D, 0D, 0D);
	}
	
	private boolean canTellToPlayer()
	{
		return this.getMotion().y > 0 && canTalk() && this.getPosY() > 200 && !world.isRemote && !isTalking();
	}
	
	public boolean canTalk()
	{
		return this.getRequestCooldown() <= 0 && !this.getPassengers().isEmpty();
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
		if(this.getRequestCooldown() > 0)
		{
			this.dataManager.set(REQUEST_COOLDOWN, this.dataManager.get(REQUEST_COOLDOWN) - 1);
		}
	}
	
	public Boolean isTalking()
	{
		return this.dataManager.get(IS_TALKING);
	}
	
	private void setTalking(boolean isTalking)
	{
		if(!this.getPassengers().isEmpty())
		{
			this.dataManager.set(IS_TALKING, isTalking);
		}
	}
	
	@Override
	public EntitySize getSize(Pose poseIn)
	{
		return super.getSize(poseIn).scale(this.getSize());
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
	
	public void setSize(float size)
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

	protected void onImpact(RayTraceResult result)
	{
		if (!this.world.isRemote)
		{
			this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), (float) this.getSize(), true, Explosion.Mode.DESTROY);

			this.remove();
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		Vec3d vec3d = this.getMotion();
		compound.put("direction", this.newDoubleNBTList(new double[] { vec3d.x, vec3d.y, vec3d.z }));
		compound.put("power", this.newDoubleNBTList(new double[] { this.accelerationX, this.accelerationY, this.accelerationZ }));
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

		if (compound.contains("power", 9))
		{
			ListNBT listnbt = compound.getList("power", 6);
			if (listnbt.size() == 3)
			{
				this.accelerationX = listnbt.getDouble(0);
				this.accelerationY = listnbt.getDouble(1);
				this.accelerationZ = listnbt.getDouble(2);
			}
		}

		if (compound.contains("direction", 9) && compound.getList("direction", 6).size() == 3)
		{
			ListNBT listnbt1 = compound.getList("direction", 6);
			this.setMotion(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
		} else
		{
			this.setMotion(0D, 0D, 0D);
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
}