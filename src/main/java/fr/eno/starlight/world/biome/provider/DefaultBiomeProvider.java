package fr.eno.starlight.world.biome.provider;

import java.util.List;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class DefaultBiomeProvider extends BiomeProvider
{
	private final List<Biome> biomeList;
	private final int sizes;

	public DefaultBiomeProvider(DefaultBiomeProviderSettings settings)
	{
		super(ImmutableSet.copyOf(settings.getBiomes()));
		this.biomeList = settings.getBiomes();
		this.sizes = settings.getSize() + 2;
	}

	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.biomeList.get(Math.abs(((x >> this.sizes) + (z >> this.sizes)) % this.biomeList.size()));
	}
}
