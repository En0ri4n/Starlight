package fr.eno.starlight.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.ChatFormatting;

import fr.eno.starlight.References;
import fr.eno.starlight.client.utils.Title;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class OverlayHandler
{
	private static Minecraft mc = Minecraft.getInstance();
	private static double fade = 0;
	private static double tick = 0;
	private static List<Title> titleList = new ArrayList<Title>();
	
	@SubscribeEvent
	public static void renderOverlay(RenderGameOverlayEvent event)
	{
		if(event.getType().equals(ElementType.ALL))
		{
			if(!titleList.isEmpty())
			{
				Title title = titleList.get(0);
				
				renderTitle(title, event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight());
			}
		}
	}
	
	private static void renderTitle(Title title, int scaledWidth, int scaledHeight)
	{
		int midWidth = scaledWidth / 2;
		int height = scaledHeight / 3;
		float scale = 2F;
		RenderSystem.pushMatrix();
		RenderSystem.scalef(scale, scale, scale);
		mc.fontRenderer.drawString(ChatFormatting.UNDERLINE + title.getText().getFormattedText(), getStringPos(title.getText(), midWidth, scale) / scale, height / scale, getFadeColor((int) title.getFade()));
		RenderSystem.popMatrix();
		
		
		if(fade < title.getFade())
		{
			fade++;
		}
		else
		{
			tick++;
		}
		
		if(tick >= title.getTime())
		{
			titleList.remove(0);
			tick = 0;
			fade = 0;
		}
	}
	
	private static List<Integer> fades = Lists.newArrayList(0xFFFFFFFF,
			0xF2FFFFFF,
			0xE6FFFFFF,
			0xD9FFFFFF,
			0xCCFFFFFF,
			0xBFFFFFFF,
			0xB3FFFFFF,
			0xA6FFFFFF,
			0x99FFFFFF,
			0x8CFFFFFF,
			0x80FFFFFF,
			0x73FFFFFF,
			0x66FFFFFF,
			0x59FFFFFF,
			0x4DFFFFFF,
			0x40FFFFFF,
			0x33FFFFFF,
			0x26FFFFFF,
			0x1AFFFFFF,
			0x0DFFFFFF,
			0x00FFFFFF);
	
	private static int getFadeColor(int fadeIn)
	{
		int index = 20 - (int) (fade * 20 / fadeIn);
		
		return fades.get(index);
	}
	
	private static int getStringPos(ITextComponent text, int xCoord, float scale)
	{
		return (int) ((scale * (xCoord - mc.fontRenderer.getStringWidth(text.getFormattedText()))) / 2);
	}

	public static void sendTitle(ITextComponent text, int fade, int time)
	{
		titleList.add(new Title(text, fade, time));
	}
}
