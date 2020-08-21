package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.gen.feature.CustomOreFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitFeatures
{
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, References.MOD_ID);

	public static final RegistryObject<CustomOreFeature> ORE_FEATURE = FEATURES.register("custom_ore_feature", () -> new CustomOreFeature(OreFeatureConfig::deserialize));
}
