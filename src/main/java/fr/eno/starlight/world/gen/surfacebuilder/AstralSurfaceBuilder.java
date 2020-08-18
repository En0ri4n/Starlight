package fr.eno.starlight.world.gen.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import fr.eno.starlight.init.InitBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class AstralSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public AstralSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> function)
	{
		super(function);
	}

	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		SurfaceBuilderConfig builder = new SurfaceBuilderConfig(InitBlocks.STAR_GRASS.get().getDefaultState(), InitBlocks.STAR_STONE.get().getDefaultState(), Blocks.WATER.getDefaultState());
		SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, builder);
	}
}
