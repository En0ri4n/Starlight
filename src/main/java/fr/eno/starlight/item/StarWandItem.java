package fr.eno.starlight.item;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.eno.starlight.References;
import fr.eno.starlight.utils.Tabs;
import fr.eno.starlight.world.teleporter.DefaultTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class StarWandItem extends Item
{
	private final ResourceLocation dimension;
	private final int level;
	
	public StarWandItem(ResourceLocation type, int levelIn)
	{
		super(new Item.Properties().group(Tabs.ITEMS).maxStackSize(1).setNoRepair().rarity(Rarity.RARE));
		this.dimension = type;
		this.level = levelIn;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(stack.getTag() == null)
		{
			CompoundNBT nbt = new CompoundNBT();
			nbt.putBoolean("Activated", false);
			stack.setTag(nbt);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(dimension != null && Minecraft.getInstance().world != null && DimensionType.byName(dimension) != null)
			tooltip.add(new StringTextComponent(ChatFormatting.GOLD + "Dimension : " + DimensionType.byName(dimension).getRegistryName().getPath()));
		
		tooltip.add(new StringTextComponent(""));
		
		if(stack.getTag() != null)
		{
			if(stack.getTag().getBoolean("Activated"))
			{
				tooltip.add(References.getTranslate("item.StarWand.activated"));
			}
			else
			{
				tooltip.add(References.getTranslate("item.StarWand.desactivated"));
			}
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		
		if(stack.getTag().getBoolean("Activated"))
		{
			if(DimensionType.byName(this.dimension) == DimensionType.OVERWORLD && playerIn.dimension == DimensionType.OVERWORLD)
			{
				playerIn.sendMessage(References.getTranslate("item.StarWand.alreadyInDimension"));
			}
			
			playerIn.sendMessage(References.getTranslate("item.StarWand.teleportation"));
			
			if(playerIn.dimension == DimensionType.byName(this.dimension))
			{
				playerIn.changeDimension(DimensionType.OVERWORLD, DefaultTeleporter.getInstance());
			}
			else
			{
				playerIn.changeDimension(DimensionType.byName(this.dimension), DefaultTeleporter.getInstance());
				playerIn.setPosition(0, 75, 0);
			}
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public static boolean isActivated(ItemStack stack)
	{
		if(stack.getItem() instanceof StarWandItem)
		{
			if(stack.getTag().getBoolean("Activated"))
			{
				return false;
			}
		}
		
		return true;
	}
}
