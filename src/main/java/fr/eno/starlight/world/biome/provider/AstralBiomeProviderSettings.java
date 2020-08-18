package fr.eno.starlight.world.biome.provider;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class AstralBiomeProviderSettings implements IBiomeProviderSettings
{
	private Biome[] biomes = new Biome[] { Biomes.BEACH, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.PLAINS };
	private int size = biomes.length;

	public AstralBiomeProviderSettings(WorldInfo worldInfo) {}

	public AstralBiomeProviderSettings setBiomes(Biome[] biomesIn)
	{
		this.biomes = biomesIn;
		return this;
	}

	public AstralBiomeProviderSettings setSize(int size)
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
