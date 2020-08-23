package fr.eno.starlight.client;

import java.util.function.Predicate;

import fr.eno.starlight.client.entity.renderer.StarChestTileRenderer;
import fr.eno.starlight.client.entity.renderer.StarRenderer;
import fr.eno.starlight.client.screen.containers.StarChestScreen;
import fr.eno.starlight.init.InitBlocks;
import fr.eno.starlight.init.InitContainers;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy
{
	private static final Minecraft mc = Minecraft.getInstance();
	
	private static void clientSetup(FMLClientSetupEvent event)
	{
		setRenderLayers();
		registerEntityRenderers();
		bindTileEntityRenderers();
		bindScreenContainers();
	}
	
	private static void setRenderLayers()
	{
		Predicate<RenderType> solid = (render) -> render == RenderType.getSolid();
		
		RenderTypeLookup.setRenderLayer(InitBlocks.STAR_STONE.get(), solid);
	}
	
	private static void registerEntityRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(InitEntities.STAR.get(), manager -> new StarRenderer(manager, mc.getItemRenderer()));
	}
	
	private static void bindScreenContainers()
	{
		DeferredWorkQueue.runLater(() ->
		{
			ScreenManager.registerFactory(InitContainers.STAR_CHEST.get(), StarChestScreen::new);
		});
	}
	
	private static void bindTileEntityRenderers()
	{
		ClientRegistry.bindTileEntityRenderer(InitTileEntities.STAR_CHEST.get(), StarChestTileRenderer::new);
	}
	
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
}