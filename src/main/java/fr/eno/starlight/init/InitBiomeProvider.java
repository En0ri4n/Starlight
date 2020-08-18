package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.biome.provider.UtopianBiomeProvider;
import fr.eno.starlight.world.biome.provider.UtopianBiomeProviderSettings;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBiomeProvider
{
	public static final DeferredRegister<BiomeProviderType<?, ?>> BIOME_PROVIDER_TYPE = DeferredRegister.create(ForgeRegistries.BIOME_PROVIDER_TYPES, References.MOD_ID);
	
	public static final RegistryObject<BiomeProviderType<UtopianBiomeProviderSettings, UtopianBiomeProvider>> UTOPIAN = BIOME_PROVIDER_TYPE.register("utopian", () -> new BiomeProviderType<UtopianBiomeProviderSettings, UtopianBiomeProvider>(UtopianBiomeProvider::new, UtopianBiomeProviderSettings::new));
}
