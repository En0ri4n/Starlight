package fr.eno.starlight.utils;

import fr.eno.starlight.init.InitItems;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.NonNullLazy;

public class Materials
{
	public static final IArmorMaterial STAR = create("star", new int[] {1000, 1100, 1200, 1300}, new int[] {6, 8, 10, 8}, 9F, 30, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, () -> Ingredient.fromItems(InitItems.STAR.get()));
	
	private static final IArmorMaterial create(String name, int[] durability, int[] reductionAmount, float toughness, int enchantability, SoundEvent sound, NonNullLazy<Ingredient> repair)
	{
		return new IArmorMaterial()
		{
			@Override
			public float getToughness()
			{
				return toughness;
			}
			
			@Override
			public SoundEvent getSoundEvent()
			{
				return sound;
			}
			
			@Override
			public Ingredient getRepairMaterial()
			{
				return repair.get();
			}
			
			@Override
			public String getName()
			{
				return name;
			}
			
			@Override
			public int getEnchantability()
			{
				return enchantability;
			}
			
			@Override
			public int getDurability(EquipmentSlotType slotIn)
			{
				return durability[slotIn.getIndex()];
			}
			
			@Override
			public int getDamageReductionAmount(EquipmentSlotType slotIn)
			{
				return reductionAmount[slotIn.getIndex()];
			}
		};
	}
}
