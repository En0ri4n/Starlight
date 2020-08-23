package fr.eno.starlight.world.biome.provider;

import java.util.Arrays;

import net.minecraft.world.biome.Biomes;
import net.minecraft.world.storage.WorldInfo;

public class DarkBiomeProviderSettings extends DefaultBiomeProviderSettings
{
	public DarkBiomeProviderSettings(WorldInfo worldInfo)
	{
		super(Arrays.asList(Biomes.PLAINS, Biomes.DARK_FOREST, Biomes.BADLANDS, Biomes.BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU));
	}
}