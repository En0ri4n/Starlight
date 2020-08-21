package fr.eno.starlight.client;

import fr.eno.starlight.client.entity.renderer.StarChestTileRenderer;
import fr.eno.starlight.client.entity.renderer.StarRenderer;
import fr.eno.starlight.client.screen.StarChestScreen;
import fr.eno.starlight.init.InitContainers;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("deprecation")
public class ClientProxy
{
	@SuppressWarnings("serial")
	public static DistExecutor.SafeRunnable executeClientTask(IEventBus bus)
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				bus.addListener(ClientProxy::clientSetup);
			}
		};
	}
	
	private static void bindScreenContainers()
	{
		DeferredWorkQueue.runLater(() ->
		{
			ScreenManager.registerFactory(InitContainers.STAR_CHEST.get(), StarChestScreen::new);
		});
	}

	public static void clientSetup(FMLClientSetupEvent event)
	{
		bindScreenContainers();
		
		Minecraft mc = event.getMinecraftSupplier().get();
		RenderingRegistry.registerEntityRenderingHandler(InitEntities.STAR.get(), manager -> new StarRenderer(manager, mc.getItemRenderer()));
		ClientRegistry.bindTileEntityRenderer(InitTileEntities.STAR_CHEST.get(), StarChestTileRenderer::new);
	}
}