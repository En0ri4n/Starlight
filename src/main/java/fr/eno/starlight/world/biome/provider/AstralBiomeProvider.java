package fr.eno.starlight.world.biome.provider;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class AstralBiomeProvider extends BiomeProvider
{
	private final Biome[] biomeList;
	private final int sizes;

	public AstralBiomeProvider(AstralBiomeProviderSettings settings)
	{
		super(ImmutableSet.copyOf(settings.getBiomes()));
		this.biomeList = settings.getBiomes();
		this.sizes = settings.getSize() + 2;
	}

	public Biome getNoiseBiome(int x, int y, int z)
	{
		return this.biomeList[Math.abs(((x >> this.sizes) + (z >> this.sizes)) % this.biomeList.length)];
	}
}
