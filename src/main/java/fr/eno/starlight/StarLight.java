package fr.eno.starlight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.eno.starlight.client.ClientProxy;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.init.InitBiomeProvider;
import fr.eno.starlight.init.InitBlocks;
import fr.eno.starlight.init.InitChunkGenerator;
import fr.eno.starlight.init.InitContainers;
import fr.eno.starlight.init.InitDimensions;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitItems;
import fr.eno.starlight.init.InitPackets;
import fr.eno.starlight.init.InitSurfaceBuilder;
import fr.eno.starlight.init.InitTileEntities;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.MOD_ID)
public class StarLight
{
	public static final Logger LOGGER = LogManager.getLogger(References.MOD_NAME);

	public StarLight()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::commonSetup);
		bus.addListener(this::serverSetup);
		bus.addListener(ClientProxy::clientSetup);
		
		InitBiomeProvider.BIOME_PROVIDER_TYPE.register(bus);
		InitBlocks.BLOCKS.register(bus);
		InitChunkGenerator.CHUNK_GENERATOR.register(bus);
		InitContainers.CONTAINERS.register(bus);
		InitEntities.ENTITIES.register(bus);
		InitItems.ITEMS.register(bus);
		InitDimensions.MOD_DIMENSIONS.register(bus);
		InitSurfaceBuilder.SURFACE_BUILDERS.register(bus);
		InitTileEntities.TILE_ENTITIES.register(bus);
	}
	
	private void commonSetup(FMLCommonSetupEvent event)
	{
		EntitySpawnPlacementRegistry.register(InitEntities.STAR.get(), PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, StarEntity::getPlacement);
	}
	
	private void serverSetup(FMLServerStartingEvent event)
	{
		InitPackets.registerServerMessages();
	}
}