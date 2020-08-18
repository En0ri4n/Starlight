package fr.eno.starlight.world.biome.provider;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class UtopianBiomeProviderSettings implements IBiomeProviderSettings
{
	private Biome[] biomes = new Biome[] { Biomes.BEACH, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.PLAINS };
	private int size = biomes.length;

	public UtopianBiomeProviderSettings(WorldInfo worldInfo) {}

	public UtopianBiomeProviderSettings setBiomes(Biome[] biomesIn)
	{
		this.biomes = biomesIn;
		return this;
	}

	public UtopianBiomeProviderSettings setSize(int size)
	{
		this.size = size;
		return this;
	}

	public Biome[] getBiomes()
	{
		return this.biomes;
	}

	public int getSize()
	{
		return this.size;
	}
}
