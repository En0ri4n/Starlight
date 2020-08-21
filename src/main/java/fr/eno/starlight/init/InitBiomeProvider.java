package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.biome.provider.AstralBiomeProviderSettings;
import fr.eno.starlight.world.biome.provider.DarkBiomeProviderSettings;
import fr.eno.starlight.world.biome.provider.DefaultBiomeProvider;
import fr.eno.starlight.world.biome.provider.UtopianBiomeProviderSettings;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBiomeProvider
{
	public static final DeferredRegister<BiomeProviderType<?, ?>> BIOME_PROVIDER_TYPE = DeferredRegister.create(ForgeRegistries.BIOME_PROVIDER_TYPES, References.MOD_ID);
	
	public static final RegistryObject<BiomeProviderType<UtopianBiomeProviderSettings, DefaultBiomeProvider>> UTOPIAN_PROVIDER = BIOME_PROVIDER_TYPE.register("utopian_provider", () -> new BiomeProviderType<UtopianBiomeProviderSettings, DefaultBiomeProvider>(DefaultBiomeProvider::new, UtopianBiomeProviderSettings::new));
	public static final RegistryObject<BiomeProviderType<DarkBiomeProviderSettings, DefaultBiomeProvider>> DARK_PROVIDER = BIOME_PROVIDER_TYPE.register("dark_provider", () -> new BiomeProviderType<DarkBiomeProviderSettings, DefaultBiomeProvider>(DefaultBiomeProvider::new, DarkBiomeProviderSettings::new));
	public static final RegistryObject<BiomeProviderType<AstralBiomeProviderSettings, DefaultBiomeProvider>> ASTRAL_PROVIDER = BIOME_PROVIDER_TYPE.register("astral_provider", () -> new BiomeProviderType<AstralBiomeProviderSettings, DefaultBiomeProvider>(DefaultBiomeProvider::new, AstralBiomeProviderSettings::new));
}