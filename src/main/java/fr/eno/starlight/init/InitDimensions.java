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

	public static final ResourceLocation UTOPIAN_LOC = new ResourceLocation(References.MOD_ID, "utopian_dimension");
	public static final RegistryObject<ModDimension> UTOPIAN = MOD_DIMENSIONS.register(UTOPIAN_LOC.getPath(), () -> new UtopianModDimension());
	public static DimensionType UTOPIAN_DIM_TYPE = null;
	
	public static final ResourceLocation ASTRAL_LOC = new ResourceLocation(References.MOD_ID, "astral_dimension");
	public static final RegistryObject<ModDimension> ASTRAL = MOD_DIMENSIONS.register(ASTRAL_LOC.getPath(), () -> new AstralModDimension());
	public static DimensionType ASTRAL_DIM_TYPE = null;
	
	@SubscribeEvent
	public static void registerDimensions(RegisterDimensionsEvent event)
	{
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		UTOPIAN_DIM_TYPE = DimensionManager.registerOrGetDimension(UTOPIAN_LOC, UTOPIAN.get(), buffer, true);
		ASTRAL_DIM_TYPE = DimensionManager.registerOrGetDimension(ASTRAL_LOC, ASTRAL.get(), buffer, true);
	}
}