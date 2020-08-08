package fr.eno.starlight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.eno.starlight.init.InitBlocks;
import fr.eno.starlight.init.InitDimensions;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitItems;
import fr.eno.starlight.init.InitTileEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.MOD_ID)
public class StarLight
{
	public static final Logger LOGGER = LogManager.getLogger(References.MOD_NAME);
	// private ServerProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new); a

	public StarLight()
	{
		IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
		
		InitBlocks.BLOCKS.register(mod);
		InitEntities.ENTITIES.register(mod);
		InitItems.ITEMS.register(mod);
		InitDimensions.MOD_DIMENSIONS.register(mod);
		InitTileEntities.TILE_ENTITIES.register(mod);
	}
}