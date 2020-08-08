package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.dimension.StarModDimension;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitDimensions
{
	public static final DeferredRegister<ModDimension> MOD_DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, References.MOD_ID);
	
	public static final RegistryObject<ModDimension> STAR_DIMENSION = MOD_DIMENSIONS.register("star_dimension", () -> new StarModDimension());
}