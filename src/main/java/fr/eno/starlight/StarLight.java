package fr.eno.starlight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.eno.starlight.client.ClientProxy;
import fr.eno.starlight.init.InitBlocks;
import fr.eno.starlight.init.InitDimensions;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitItems;
import fr.eno.starlight.init.InitTileEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.MOD_ID)
public class StarLight
{
	public static final Logger LOGGER = LogManager.getLogger(References.MOD_NAME);

	public StarLight()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::commonSetup);
		bus.addListener(ClientProxy.getInstance()::clientSetup);
		
		InitBlocks.BLOCKS.register(bus);
		InitEntities.ENTITIES.register(bus);
		InitItems.ITEMS.register(bus);
		InitDimensions.MOD_DIMENSIONS.register(bus);
		InitTileEntities.TILE_ENTITIES.register(bus);
	}
	
	private void commonSetup(FMLCommonSetupEvent event)
	{
		
	}
}