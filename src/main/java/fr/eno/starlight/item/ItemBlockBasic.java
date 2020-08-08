package fr.eno.starlight.item;

import fr.eno.starlight.utils.Tabs;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ItemBlockBasic extends BlockItem
{

	public ItemBlockBasic(Block blockIn)
	{
		super(blockIn, new Item.Properties().maxStackSize(64).group(Tabs.BLOCKS));
	}

}
