package fr.eno.starlight.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block
{
	public BasicBlock(float hardness)
	{
		super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(hardness));
	}
}
