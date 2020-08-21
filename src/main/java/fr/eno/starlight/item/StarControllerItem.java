package fr.eno.starlight.item;

import fr.eno.starlight.utils.Tabs;
import net.minecraft.item.Item;

public class StarControllerItem extends Item
{
	public StarControllerItem()
	{
		super(new Item.Properties().group(Tabs.ITEMS).maxStackSize(1).setNoRepair());
	}

}
