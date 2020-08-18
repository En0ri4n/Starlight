package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.gen.UtopianChunkGenerator;
import fr.eno.starlight.world.gen.UtopianGenSettings;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitChunkGenerator
{
	public static final DeferredRegister<ChunkGeneratorType<?, ?>> CHUNK_GENERATOR = DeferredRegister.create(ForgeRegistries.CHUNK_GENERATOR_TYPES, References.MOD_ID);
	
	public static final RegistryObject<ChunkGeneratorType<UtopianGenSettings, UtopianChunkGenerator>> UTOPIAN = CHUNK_GENERATOR.register("utopian", () -> new ChunkGeneratorType<UtopianGenSettings, UtopianChunkGenerator>(UtopianChunkGenerator::new, true, () -> new UtopianGenSettings()));
}
