package fr.eno.starlight.item;

import fr.eno.starlight.utils.Materials;
import fr.eno.starlight.utils.Tabs;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;

public class StarArmorItem extends ArmorItem
{
	public StarArmorItem(EquipmentSlotType slot)
	{
		super(Materials.STAR, slot, new Item.Properties().maxStackSize(1).group(Tabs.ARMORS).rarity(Rarity.RARE));
	}
}