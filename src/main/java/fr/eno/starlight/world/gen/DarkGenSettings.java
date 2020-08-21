package fr.eno.starlight.world.gen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationSettings;

public class DarkGenSettings extends GenerationSettings
{
	public DarkGenSettings() {}
	
	@Override
	public BlockState getDefaultBlock()
	{
		return Blocks.BLACK_TERRACOTTA.getDefaultState();
	}
	
	@Override
	public BlockState getDefaultFluid()
	{
		return Blocks.LAVA.getDefaultState();
	}
	
	@Override
	public int getBedrockFloorHeight()
	{
		return 0;
	}
}