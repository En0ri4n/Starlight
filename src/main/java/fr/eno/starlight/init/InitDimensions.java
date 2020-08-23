package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.world.dimension.AstralModDimension;
import fr.eno.starlight.world.dimension.UtopianModDimension;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = References.MOD_ID, bus = Bus.FORGE)
public class InitDimensions
{
	public static final DeferredRegister<ModDimension> MOD_DIMENSIONS = DeferredRegister.create(ForgeRegistries.MOD_DIMENSIONS, References.MOD_ID);

	public static final ResourceLocation UTOPIAN_DIM_LOC = new ResourceLocation(References.MOD_ID, "utopian_dimension");
	public static final RegistryObject<ModDimension> UTOPIAN_DIM = MOD_DIMENSIONS.register(UTOPIAN_DIM_LOC.getPath(), () -> new UtopianModDimension());
	
	public static final ResourceLocation ASTRAL_DIM_LOC = new ResourceLocation(References.MOD_ID, "astral_dimension");
	public static final RegistryObject<ModDimension> ASTRAL_DIM = MOD_DIMENSIONS.register(ASTRAL_DIM_LOC.getPath(), () -> new AstralModDimension());
	
	public static final ResourceLocation DARK_DIM_LOC = new ResourceLocation(References.MOD_ID, "dark_dimension");
	public static final RegistryObject<ModDimension> DARK_DIM = MOD_DIMENSIONS.register(DARK_DIM_LOC.getPath(), () -> new AstralModDimension());
	
	public static final DimensionType getAstralType()
	{
		return DimensionType.byName(ASTRAL_DIM_LOC);
	}
	
	public static final DimensionType getDarkType()
	{
		return DimensionType.byName(DARK_DIM_LOC);
	}
	
	public static final DimensionType getUtopianType()
	{
		return DimensionType.byName(UTOPIAN_DIM_LOC);
	}
	
	@SubscribeEvent
	public static void registerDimensions(RegisterDimensionsEvent event)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		DimensionManager.registerOrGetDimension(UTOPIAN_DIM_LOC, UTOPIAN_DIM.get(), buffer, true);
		DimensionManager.registerOrGetDimension(ASTRAL_DIM_LOC, ASTRAL_DIM.get(), buffer, false);
		DimensionManager.registerOrGetDimension(DARK_DIM_LOC, DARK_DIM.get(), buffer, true);
	}
}