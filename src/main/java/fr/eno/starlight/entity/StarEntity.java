package fr.eno.starlight.entity;

import java.util.Random;

import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class StarEntity extends DamagingProjectileEntity implements IRendersAsItem
{
	public int size = 1;

	public StarEntity(EntityType<? extends DamagingProjectileEntity> type, World world)
	{
		super(type, world);
	}
	
	public StarEntity(World world)
	{
		this(InitEntities.STAR.get(), world);
	}
	
	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand)
	{
		return this.getPassengers().isEmpty() ? player.startRiding(this) : false;
	}
	
	@Override
	protected boolean isFireballFiery()
	{
		return false;
	}

	@Override
	public ItemStack getItem()
	{
		return new ItemStack(InitItems.STAR.get());
	}
	
	public int getSize()
	{
		return this.size;
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		super.onImpact(result);

		if (!this.world.isRemote)
		{
			this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), (float) this.size, true, Explosion.Mode.DESTROY);
			world.addEntity(new ForceWaveEntity(world, size));
			this.remove();
		}

	}

	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putInt("Size", this.size);
	}

	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);

		if (compound.contains("Size"))
		{
			this.size = compound.getInt("Size");
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