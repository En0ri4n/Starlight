package fr.eno.starlight.utils;

import fr.eno.starlight.References;
import fr.eno.starlight.init.InitDimensions;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.dimension.DimensionType;

public enum Speechs
{
	OVERWORLD(References.getTranslate("screen.SpeechScreen.overworld.speech"), DimensionType.OVERWORLD),
	ASTRAL(References.getTranslate("screen.SpeechScreen.astral.speech"), InitDimensions.ASTRAL_DIM_TYPE),
	DARK(References.getTranslate("screen.SpeechScreen.dark.speech"), InitDimensions.DARK_DIM_TYPE),
	UTOPIAN(References.getTranslate("screen.SpeechScreen.utopian.speech"),InitDimensions.UTOPIAN_DIM_TYPE);
	
	private TextComponent speech;
	private DimensionType type;

	private Speechs(TextComponent textComponent, DimensionType typeIn)
	{
		this.speech = textComponent;
		this.type = typeIn;
	}
	
	public TextComponent getSpeech()
	{
		return this.speech;
	}
	
	public DimensionType getType()
	{
		return this.type;
	}
	
	public static final Speechs getByDimension(DimensionType type)
	{
		for(Speechs speech : Speechs.values())
		{
			if(speech.getType() == type)
			{
				return speech;
			}
		}
		
		return OVERWORLD;
	}
}
