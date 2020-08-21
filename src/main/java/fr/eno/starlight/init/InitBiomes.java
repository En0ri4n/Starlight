package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.biome.AstralBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBiomes
{
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, References.MOD_ID);
	
	public static final RegistryObject<Biome> ASTRAL = BIOMES.register("astral_biome", () -> new AstralBiome());
}
