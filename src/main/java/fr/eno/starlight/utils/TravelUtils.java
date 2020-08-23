package fr.eno.starlight.utils;

import net.minecraft.world.dimension.DimensionType;

public class TravelUtils
{
	public static Travels getDimensionByLevel(int level)
	{
		for(Travels manager : Travels.values())
		{
			if(manager.getLevel() == level)
			{
				return manager;
			}
		}
		
		return Travels.OVERWORLD;
	}
	
	public static int getLevelByDimension(DimensionType type)
	{
		for(Travels manager : Travels.values())
		{
			if(manager.getDimensionLocation().equals(type.getRegistryName()))
			{
				return manager.getLevel();
			}
		}
		
		return 0;
	}
}
