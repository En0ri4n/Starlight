package fr.eno.starlight.client;

import fr.eno.starlight.client.entity.renderer.ForceWaveRenderer;
import fr.eno.starlight.client.entity.renderer.StarRenderer;
import fr.eno.starlight.init.InitEntities;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy
{
	private static ClientProxy INSTANCE;
	
	public ClientProxy()
	{
		INSTANCE = this;
	}
	
	public void clientSetup(FMLClientSetupEvent event)
	{
		Minecraft mc = event.getMinecraftSupplier().get();
		RenderingRegistry.registerEntityRenderingHandler(InitEntities.STAR.get(), manager -> new StarRenderer(manager, mc.getItemRenderer()));
		RenderingRegistry.registerEntityRenderingHandler(InitEntities.FORCEWAVE.get(), manager -> new ForceWaveRenderer(manager));
	}
	
	public static ClientProxy getInstance()
	{
		return INSTANCE == null ? INSTANCE = new ClientProxy() : INSTANCE;
	}
}
