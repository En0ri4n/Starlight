package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.gen.surfacebuilder.AstralSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSurfaceBuilder
{
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, References.MOD_ID);
	
	public static final RegistryObject<SurfaceBuilder<?>> ASTRAL_BUILDER = SURFACE_BUILDERS.register("astral_builer", () -> new AstralSurfaceBuilder(SurfaceBuilderConfig::deserialize));
}
