package fr.eno.starlight.utils;

import java.util.UUID;

import fr.eno.starlight.client.screen.SimpleSpeechScreen;
import fr.eno.starlight.client.screen.SpeechScreen;
import fr.eno.starlight.client.screen.TravelRequestScreen;
import fr.eno.starlight.client.utils.Speechs;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.DistExecutor;

public class ClientExecutor
{
	final static Minecraft mc = Minecraft.getInstance();

	public static DistExecutor.SafeRunnable openSpeechScreen(ResourceLocation loc, UUID starId)
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				mc.displayGuiScreen(new SpeechScreen(Speechs.getByDimensionLoc(loc), starId));
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
				mc.displayGuiScreen(new SimpleSpeechScreen(textIn));
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
				mc.displayGuiScreen(new TravelRequestScreen(Travels.getById(id), starId));
			}
		};
	}

	public static DistExecutor.SafeRunnable changePlayerView()
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				mc.gameSettings.thirdPersonView = 1;
			}
		};
	}
}
