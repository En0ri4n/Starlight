package fr.eno.starlight.world.dimension;

import java.util.function.BiFunction;

import fr.eno.starlight.init.InitDimensions;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

public class StarModDimension extends ModDimension
{
	@SuppressWarnings("unused")
	private final int id;
	
	public StarModDimension()
	{
		this.id = 3;
	}	
	
	public static DimensionType getDimensionType() 
	{		
		return DimensionType.byName(InitDimensions.STAR_DIMENSION.get().getRegistryName());
	}
	
	@Override
	public BiFunction<World, DimensionType, ? extends Dimension> getFactory() 
	{
		return StarDimension::new;
	}
}
