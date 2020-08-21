package fr.eno.starlight.world.biome.provider;

import java.util.Arrays;

import net.minecraft.world.biome.Biomes;
import net.minecraft.world.storage.WorldInfo;

public class UtopianBiomeProviderSettings extends DefaultBiomeProviderSettings
{
	public UtopianBiomeProviderSettings(WorldInfo worldInfo)
	{
		super(Arrays.asList(Biomes.BEACH, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.PLAINS));
	}
}