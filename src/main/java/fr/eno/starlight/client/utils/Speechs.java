package fr.eno.starlight.client.utils;

import fr.eno.starlight.References;
import fr.eno.starlight.init.InitDimensions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.dimension.DimensionType;

public enum Speechs
{
	OVERWORLD(References.getTranslate("screen.SpeechScreen.overworld.speech"), DimensionType.OVERWORLD.getRegistryName()),
	ASTRAL(References.getTranslate("screen.SpeechScreen.astral.speech"), InitDimensions.ASTRAL_DIM_LOC),
	DARK(References.getTranslate("screen.SpeechScreen.dark.speech"), InitDimensions.DARK_DIM_LOC),
	UTOPIAN(References.getTranslate("screen.SpeechScreen.utopian.speech"),InitDimensions.UTOPIAN_DIM_LOC);
	
	private TextComponent speech;
	private ResourceLocation loc;

	private Speechs(TextComponent textComponent, ResourceLocation locIn)
	{
		this.speech = textComponent;
		this.loc = locIn;
	}
	
	public TextComponent getSpeech()
	{
		return this.speech;
	}
	
	public DimensionType getType()
	{
		return DimensionType.byName(loc);
	}
	
	public ResourceLocation getLoc()
	{
		return this.loc;
	}
	
	public static final Speechs getByDimensionLoc(ResourceLocation location)
	{
		for(Speechs speech : Speechs.values())
		{			
			if(speech.getLoc().equals(location))
			{
				return speech;
			}
		}
		
		return OVERWORLD;
	}
}
