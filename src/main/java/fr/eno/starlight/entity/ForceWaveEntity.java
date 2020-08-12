package fr.eno.starlight.entity;

import fr.eno.starlight.init.InitEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ForceWaveEntity extends Entity
{
	private int size;
	private int maxSize;
	
	public static final DamageSource FORCEWAVE_DAMAGE = new DamageSource("forceWave").setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
	
	public ForceWaveEntity(World worldIn)
	{
		this(InitEntities.FORCEWAVE.get(), worldIn);
	}
	
	public ForceWaveEntity(EntityType<?> entityTypeIn, World worldIn)
	{
		super(entityTypeIn, worldIn);
	}
	
	public ForceWaveEntity(World worldIn, int maxSize)
	{
		this(worldIn);
		this.size = size < 1 ? 1 : size > maxSize ? 1 : size;
		this.maxSize = maxSize < size ? 100 + size : maxSize;
		// ProcessHandler.addProcess(new ProcessForceWave(getEntityWorld(), (int) this.getPosX(), (int) this.getPosY(), (int) this.getPosZ(), maxSize));
	}

	@Override
	protected void registerData()
	{
		
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		// size++;
		/*
		if(size >= maxSize)
		{
			this.remove();
		}*/
	}
	
	@Override
	public boolean isInRangeToRenderDist(double distance)
	{
		return true;
	}
	
	@Override
	protected void readAdditional(CompoundNBT compound)
	{
		this.size = compound.getInt("Size");
		this.maxSize = compound.getInt("MaxSize");
	}

	@Override
	protected void writeAdditional(CompoundNBT compound)
	{
		compound.putInt("Size", this.size);
		compound.putInt("MaxSize", this.maxSize);
	}

	@Override
	public IPacket<?> createSpawnPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
