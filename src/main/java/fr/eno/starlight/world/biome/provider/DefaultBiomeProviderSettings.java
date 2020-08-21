package fr.eno.starlight.world.biome.provider;

import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;

public class DefaultBiomeProviderSettings implements IBiomeProviderSettings
{
	private List<Biome> biomes;
	private int size;
	
	public DefaultBiomeProviderSettings(List<Biome> biomes)
	{
		this.biomes = biomes;
	}
	
	public DefaultBiomeProviderSettings setBiomes(List<Biome> biomesIn)
	{
		this.biomes = biomesIn;
		return this;
	}

	public DefaultBiomeProviderSettings setSize(int size)
	{
		this.size = size;
		return this;
	}
	
	public List<Biome> getBiomes()
	{
		return this.biomes;
	}

	public int getSize()
	{
		return this.size;
	}
}
