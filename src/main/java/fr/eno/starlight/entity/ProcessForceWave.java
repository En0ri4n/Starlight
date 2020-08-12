package fr.eno.starlight.entity;

import java.util.List;

import fr.eno.starlight.handlers.IProcess;
import net.minecraft.entity.Entity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ProcessForceWave implements IProcess
{
	private boolean isDead = false;

	private World world;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private float power;

	private double expansion = 0;

	public ProcessForceWave(World world, int x, int y, int z, float power)
	{
		this.world = world;
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.power = power;
		isDead = world.isRemote;
	}

	@Override
	public void updateProcess()
	{
		expansion++;
		
		if(power > 0)
		{
			world.addParticle(RedstoneParticleData.REDSTONE_DUST, xCoord, yCoord, zCoord, expansion, expansion, expansion);
			AxisAlignedBB box = new AxisAlignedBB(new BlockPos(xCoord + expansion, yCoord + expansion, zCoord + expansion), new BlockPos(xCoord - expansion, yCoord - expansion, zCoord - expansion));
			List<Entity> entities = world.getEntitiesInAABBexcluding((Entity) null, box, null);
			
			entities.forEach(entity -> entity.attackEntityFrom(ForceWaveEntity.FORCEWAVE_DAMAGE, (float) (power > expansion ? power - expansion : 1F)));
			
			if(expansion >= power)
			{
				isDead = true;
			}
		}
		else
		{
			isDead = true;
		}
	}

	@Override
	public boolean isDead()
	{
		return isDead;
	}
}