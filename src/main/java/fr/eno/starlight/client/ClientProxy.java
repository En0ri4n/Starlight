package fr.eno.starlight.client;

import fr.eno.starlight.client.entity.renderer.StarRenderer;
import fr.eno.starlight.init.InitEntities;
import fr.eno.starlight.init.InitPackets;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy
{
	public static void clientSetup(FMLClientSetupEvent event)
	{
		InitPackets.registerClientMessages();
		
		Minecraft mc = event.getMinecraftSupplier().get();
		RenderingRegistry.registerEntityRenderingHandler(InitEntities.STAR.get(), manager -> new StarRenderer(manager, mc.getItemRenderer()));
	}
}