package fr.eno.starlight.utils;

import fr.eno.starlight.init.InitItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.NonNullLazy;

public class Tabs
{
	public static final ItemGroup ITEMS = createTab("starlight_items", () -> InitItems.STAR.get());

	public static final ItemGroup ARMORS = createTab("starlight_armors", () -> InitItems.STAR_CHESTPLATE.get());

	public static final ItemGroup BLOCKS = createTab("starlight_blocks", () -> InitItems.STAR_ORE.get());

	public static final ItemGroup createTab(String name, NonNullLazy<Item> icon)
	{
		return new ItemGroup(name)
		{

			@Override
			public ItemStack createIcon()
			{
				return new ItemStack(icon.get());
			}
		};
	}
}
