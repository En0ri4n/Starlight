package fr.eno.starlight.client.handlers;

import fr.eno.starlight.References;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.utils.ClientExecutor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = References.MOD_ID)
public class ClientHandler
{
	@SubscribeEvent
	public static void onMountOnStar(EntityMountEvent event)
	{
		if (event.getEntityBeingMounted() != null)
		{
			if (event.getEntityBeingMounted() instanceof StarEntity)
			{
				DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientExecutor.changePlayerView());
			}
		}
	}
}
