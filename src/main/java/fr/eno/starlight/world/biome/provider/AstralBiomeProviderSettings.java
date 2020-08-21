package fr.eno.starlight.world.biome.provider;

import java.util.Arrays;

import fr.eno.starlight.init.InitBiomes;
import net.minecraft.world.storage.WorldInfo;

public class AstralBiomeProviderSettings extends DefaultBiomeProviderSettings
{
	public AstralBiomeProviderSettings(WorldInfo worldInfo)
	{
		super(Arrays.asList(InitBiomes.ASTRAL.get()));
	}
}