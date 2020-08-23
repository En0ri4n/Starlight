package fr.eno.starlight.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BasicBlock extends Block
{
	public BasicBlock(float hardness, SoundType sound, ToolType tool)
	{
		super(Block.Properties.create(Material.ROCK).sound(sound).hardnessAndResistance(hardness).harvestTool(tool));
	}
}