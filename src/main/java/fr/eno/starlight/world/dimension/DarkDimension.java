package fr.eno.starlight.world.dimension;

import fr.eno.starlight.world.biome.provider.DefaultBiomeProvider;
import fr.eno.starlight.world.biome.provider.DarkBiomeProviderSettings;
import fr.eno.starlight.world.gen.DarkChunkGenerator;
import fr.eno.starlight.world.gen.DarkGenSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

public class DarkDimension extends Dimension
{
	public DarkDimension(World world, DimensionType type)
	{
		super(world, type, 1.0F);
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		return new DarkChunkGenerator(world, new DefaultBiomeProvider(new DarkBiomeProviderSettings(world.getWorldInfo())), new DarkGenSettings());
	}
	
	@Override
	public void tick()
	{
		// this.getWorld().setDayTime(13000L);
	}
	
	@Override
	public boolean canDoLightning(Chunk chunk)
	{
		return true;
	}

	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
	{
		return new BlockPos(0, 75, 0);
	}

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
	{
		return new BlockPos(0, 75, 0);
	}

	@Override
	public int getSeaLevel()
	{
		return 70;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk)
	{
		return true;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		return 0.0F;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks)
	{
		return new Vec3d(0D, 0D, 0D);
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return true;
	}
}