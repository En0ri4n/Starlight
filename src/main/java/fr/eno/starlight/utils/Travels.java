package fr.eno.starlight.utils;

import fr.eno.starlight.References;
import fr.eno.starlight.init.InitDimensions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.dimension.DimensionType;

public enum Travels
{
	OVERWORLD(100, 0, DimensionType.OVERWORLD.getRegistryName(), References.getTranslate("screen.RequestScreen.overworld.request")),
	ASTRAL_DIMENSION(101, 1, InitDimensions.ASTRAL_DIM_LOC, References.getTranslate("screen.RequestScreen.astral.request")),
	DARK_DIMENSION(102, 2, InitDimensions.DARK_DIM_LOC, References.getTranslate("screen.RequestScreen.dark.request")),
	UTOPIAN_DIMENSION(103, 3, InitDimensions.UTOPIAN_DIM_LOC, References.getTranslate("screen.RequestScreen.utopian.request"));
	
	private int id;
	private int level;
	private ResourceLocation dimensionLocation;
	private TextComponent request;
	
	private Travels(int idIn, int levelIn, ResourceLocation dimensionLocationIn, TextComponent requestIn)
	{
		this.id = idIn;
		this.level = levelIn;
		this.dimensionLocation = dimensionLocationIn;
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

	public TextComponent getRequest()
	{
		return request;
	}
	
	public static Travels getById(int id)
	{
		for(Travels manager : Travels.values())
		{
			if(manager.getId() == id)
			{
				return manager;
			}
		}
		
		return OVERWORLD;
	}
}
