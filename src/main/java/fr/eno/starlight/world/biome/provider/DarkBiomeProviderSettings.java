package fr.eno.starlight.world.biome.provider;

import java.util.Arrays;

import fr.eno.starlight.init.InitBiomes;
import net.minecraft.world.storage.WorldInfo;

public class DarkBiomeProviderSettings extends DefaultBiomeProviderSettings
{
	public DarkBiomeProviderSettings(WorldInfo worldInfo)
	{
		super(Arrays.asList(InitBiomes.ASTRAL.get()));
	}
}