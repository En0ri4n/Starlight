package fr.eno.starlight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.eno.starlight.client.ClientProxy;
import fr.eno.starlight.init.InitBiomeProvider;
import fr.eno.starlight.init.InitBiomes;
import fr.eno.starlight.init.InitBlocks;
import fr.eno.starlight.init.InitChunkGenerator;
import fr.eno.starlight.init.InitContainers;
import fr.eno.starlight.init.InitDimensions;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitFeatures;
import fr.eno.starlight.init.InitItems;
import fr.eno.starlight.init.InitSounds;
import fr.eno.starlight.init.InitSurfaceBuilder;
import fr.eno.starlight.init.InitTileEntities;
import fr.eno.starlight.packets.NetworkManager;
import fr.eno.starlight.world.gen.OreGenManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;

@Mod(References.MOD_ID)
public class StarLight
{
	public static final Logger LOGGER = LogManager.getLogger(References.MOD_NAME);

	public StarLight()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::commonSetup);
		
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientProxy.executeClientTask(bus));
		
		InitBiomeProvider.BIOME_PROVIDER_TYPE.register(bus);
		InitBiomes.BIOMES.register(bus);
		InitBlocks.BLOCKS.register(bus);
		InitChunkGenerator.CHUNK_GENERATOR.register(bus);
		InitContainers.CONTAINERS.register(bus);
		InitEntities.ENTITIES.register(bus);
		InitFeatures.FEATURES.register(bus);
		InitItems.ITEMS.register(bus);
		InitDimensions.MOD_DIMENSIONS.register(bus);
		InitSounds.SOUNDS.register(bus);
		InitSurfaceBuilder.SURFACE_BUILDERS.register(bus);
		InitTileEntities.TILE_ENTITIES.register(bus);
	}
	
	private void commonSetup(FMLCommonSetupEvent event)
	{
		NetworkManager.createNetwork(NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(References.MOD_ID, "main_channel"))
				.clientAcceptedVersions(References.MOD_VERSION::equals)
				.serverAcceptedVersions(References.MOD_VERSION::equals)
				.networkProtocolVersion(() -> References.MOD_VERSION)
				.simpleChannel());
		
		NetworkManager.registerMessages();
		
		OreGenManager.generateOres();
	}
}