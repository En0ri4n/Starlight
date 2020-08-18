package fr.eno.starlight.item;

import java.util.List;

import fr.eno.starlight.References;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.utils.Tabs;
import fr.eno.starlight.world.teleporter.DefaultTeleporter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class StarWandItem extends Item
{
	private final DimensionType dimension;
	private final int level;
	
	public StarWandItem(DimensionType type, int levelIn)
	{
		super(new Item.Properties().group(Tabs.ITEMS).maxStackSize(1).setNoRepair().rarity(Rarity.RARE));
		this.dimension = type;
		this.level = levelIn;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(dimension != null)
			tooltip.add(new StringTextComponent("Dimension : " + dimension.getRegistryName().getPath()));
		
		tooltip.add(new StringTextComponent("\n"));
		
		if(stack.getTag().contains("Activated"))
		{
			tooltip.add(References.getTranslate("item.StarWand.activated"));
		}
		else
		{
			tooltip.add(References.getTranslate("item.StarWand.desactivated"));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(stack.getTag().contains("Activated"))
		{
			playerIn.sendMessage(References.getTranslate("item.StarWand.teleportation"));
			
			if(playerIn.dimension == this.dimension)
			{
				playerIn.changeDimension(DimensionType.OVERWORLD, DefaultTeleporter.getInstance());
			}
			else
			{
				playerIn.changeDimension(this.dimension, DefaultTeleporter.getInstance());
			}
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
	{
		if(target instanceof StarEntity)
		{
			CompoundNBT nbt = stack.getTag();
			nbt.putBoolean("Activated", true);
			stack.setTag(nbt);
		}
		
		return false;
	}
	
	public int getLevel()
	{
		return this.level;
	}
}
