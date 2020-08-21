package fr.eno.starlight.world.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.eno.starlight.init.InitBiomes;
import fr.eno.starlight.init.InitBlocks;
import fr.eno.starlight.init.InitFeatures;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenManager
{
	public static void generateOres()
	{
		addOreToGen(new ArrayList<Biome>(ForgeRegistries.BIOMES.getValues()), InitBlocks.FRAGMENTED_STAR_ORE.get(), 1, 11, 48, 2);
		addOreToGen(Arrays.asList(InitBiomes.ASTRAL.get()), InitBlocks.STAR_ORE.get(), 1, 12, 40, 2);
	}
	
	private static void addOreToGen(List<Biome> biomes, Block block, int size, int aboveY, int belowY, int maxSize)
	{
		ForgeRegistries.BIOMES.forEach(biome ->
		{
			if(biomes.contains(biome))
			{
				biome.addFeature(Decoration.UNDERGROUND_ORES, InitFeatures.ORE_FEATURE.get().withConfiguration(new OreFeatureConfig(FillerBlockType.NATURAL_STONE, block.getDefaultState(), size)).withPlacement(Placement.RANDOM_COUNT_RANGE.configure(new CountRangeConfig(size, aboveY, belowY, maxSize))));
			}
		});
	}
}