package fr.eno.starlight.client.utils;

import java.util.UUID;

import fr.eno.starlight.client.screen.SpeechScreen;
import fr.eno.starlight.client.screen.TravelRequestSpaceScreen;
import fr.eno.starlight.utils.Speechs;
import fr.eno.starlight.utils.Travels;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.DistExecutor;

@SuppressWarnings("serial")
public class OpenScreenUtils
{
	final static Minecraft mc = Minecraft.getInstance();
	
	public static DistExecutor.SafeRunnable openSpeechScreen(ResourceLocation loc, UUID starId)
	{
		return new DistExecutor.SafeRunnable()
		{
			@Override
			public void run()
			{
				mc.displayGuiScreen(new SpeechScreen(Speechs.getByDimension(DimensionType.byName(loc)), starId));
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
				mc.displayGuiScreen(new TravelRequestSpaceScreen(Travels.getById(id), starId));
			}
		};
    }
}
