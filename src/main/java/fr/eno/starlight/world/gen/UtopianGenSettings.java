package fr.eno.starlight.world.gen;

import fr.eno.starlight.init.InitBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationSettings;

public class UtopianGenSettings extends GenerationSettings
{
	public UtopianGenSettings() {}
	
	public BlockState getDefaultBlock()
	{
		return InitBlocks.STAR_STONE.get().getDefaultState();
	}
	
	@Override
	public BlockState getDefaultFluid()
	{
		return Blocks.WATER.getDefaultState();
	}
	
	@Override
	public int getBedrockFloorHeight()
	{
		return 0;
	}
}
