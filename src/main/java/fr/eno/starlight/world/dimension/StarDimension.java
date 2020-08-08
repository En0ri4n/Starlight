package fr.eno.starlight.world.dimension;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

public class StarDimension extends Dimension
{

	public StarDimension(World world, DimensionType type)
	{
		super(world, type, 1.0F);
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canRespawnHere()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
