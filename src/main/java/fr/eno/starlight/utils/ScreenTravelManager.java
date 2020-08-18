package fr.eno.starlight.utils;

import fr.eno.starlight.References;
import fr.eno.starlight.init.InitDimensions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;

public enum ScreenTravelManager
{
	ASTRAL(100, 0, InitDimensions.ASTRAL_LOC, References.getTranslate("screen.TravelRequestSpace.astral.speech"), References.getTranslate("screen.TravelRequestSpace.astral.request")),
	UTOPIAN(101, 2, InitDimensions.UTOPIAN_LOC, References.getTranslate("screen.TravelRequestSpace.utopian.speech"), References.getTranslate("screen.TravelRequestSpace.utopian.request"));
	
	private int id;
	private int level;
	private ResourceLocation dimensionLocation;
	private TextComponent speech;
	private TextComponent request;
	
	private ScreenTravelManager(int idIn, int levelIn, ResourceLocation dimensionLocationIn, TextComponent speechIn, TextComponent requestIn)
	{
		this.id = idIn;
		this.level = levelIn;
		this.dimensionLocation = dimensionLocationIn;
		this.speech = speechIn;
		this.request = requestIn;
	}

	public int getId()
	{
		return id;
	}
	
	public int getLevel()
	{
		return this.level;
	}

	public ResourceLocation getDimensionLocation()
	{
		return dimensionLocation;
	}

	public TextComponent getSpeech()
	{
		return speech;
	}

	public TextComponent getRequest()
	{
		return request;
	}
	
	public static ScreenTravelManager getById(int id)
	{
		for(ScreenTravelManager manager : ScreenTravelManager.values())
		{
			if(manager.getId() == id)
			{
				return manager;
			}
		}
		
		return ASTRAL;
	}
	
	public static ScreenTravelManager getDimensionByLevel(int level)
	{
		for(ScreenTravelManager manager : ScreenTravelManager.values())
		{
			if(manager.getLevel() == level)
			{
				return manager;
			}
		}
		
		return ASTRAL;
	}
}
