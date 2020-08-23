package fr.eno.starlight.utils;

import java.util.UUID;

import fr.eno.starlight.client.ClientProxy;
import fr.eno.starlight.client.screen.SimpleSpeechScreen;
import fr.eno.starlight.client.screen.SpeechScreen;
import fr.eno.starlight.client.screen.TravelRequestScreen;
import fr.eno.starlight.client.utils.Speechs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;

public class ClientExecutor
{
	public static DistExecutor.SafeRunnable openSpeechScreen(ResourceLocation loc, UUID starId)
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				Minecraft.getInstance().displayGuiScreen(new SpeechScreen(Speechs.getByDimensionLoc(loc), starId));
			}
		};
	}
	
	public static DistExecutor.SafeRunnable openSimpleSpeechScreen(ITextComponent textIn)
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				Minecraft.getInstance().displayGuiScreen(new SimpleSpeechScreen(textIn));
			}
		};
	}

	public static DistExecutor.SafeRunnable openTravelScreen(int id, UUID starId)
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				Minecraft.getInstance().displayGuiScreen(new TravelRequestScreen(Travels.getById(id), starId));
			}
		};
	}

	public static DistExecutor.SafeRunnable changePlayerView()
	{
		return new DistExecutor.SafeRunnable()
		{
			@SuppressWarnings("resource")
			@Override
			public void run()
			{
				Minecraft.getInstance().gameSettings.thirdPersonView = 1;
			}
		};
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
