package fr.eno.starlight.item;

import java.util.List;

import fr.eno.starlight.References;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.utils.Tabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class StarControllerItem extends Item
{
	public StarControllerItem()
	{
		super(new Item.Properties().group(Tabs.ITEMS).maxStackSize(1).setNoRepair());
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(stack.getTag() != null)
		{
			CompoundNBT nbt = stack.getTag();
			
			if(nbt.contains("StarEntityTag"))
			{
				StarEntity star = getStarFromStack(worldIn, stack);
				tooltip.add(new StringTextComponent("§eLevel : §a" + star.getLevel()));
				tooltip.add(new StringTextComponent("§6Size : §2" + String.format("%.0f", star.getSize())));
				tooltip.add(new StringTextComponent("§4Health : §c" + String.format("%.0f/%.0f", star.getHealth(), star.getMaxHealth())));
				
				if(star.hasCustomName())
					tooltip.add(new StringTextComponent("§3Name : §b" + star.getCustomName().getFormattedText()));
			}
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
	{
		if (target instanceof StarEntity && !playerIn.world.isRemote)
		{
			StarEntity star = (StarEntity) target;
			storeStarInStack(star, stack);
			playerIn.sendStatusMessage(References.getTranslate("item.StarController.storeSuccess"), true);
			return true;
		}

		return false;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		ItemStack stack = ctx.getItem();
		PlayerEntity player = ctx.getPlayer();
		BlockPos pos = ctx.getPos().offset(ctx.getFace());

		if (world.isRemote)
		{
			return ActionResultType.PASS;
		}
		else if(!world.isRemote && player.isSneaking())
		{
			CompoundNBT nbt = stack.getTag();
			
			if (nbt != null)
			{
				if (hasStarStored(stack) && world.canBlockSeeSky(pos))
				{
					StarEntity star = getStarFromStack(world, stack);
					star.setPosition(pos.getX(), pos.getY(), pos.getZ());
					world.addEntity(star);
					stack.setTag(new CompoundNBT());
					return ActionResultType.SUCCESS;
				}
			}

			player.sendMessage(References.getTranslate("item.StarController.anyStarStored"));
			return ActionResultType.FAIL;
		}
		
		return ActionResultType.FAIL;
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		if (stack.getTag() != null)
		{
			CompoundNBT nbt = stack.getTag();

			if (nbt.contains("StarEntityTag"))
			{
				return true;
			}
		}

		return false;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}

	public static void storeStarInStack(StarEntity star, ItemStack stack)
	{
		if (!hasStarStored(stack))
		{
			CompoundNBT compound = stack.getTag() == null ? new CompoundNBT() : stack.getTag();
			compound.put("StarEntityTag", star.serializeNBT());
			stack.setTag(compound);
			star.remove();
		}
	}

	public static StarEntity getStarFromStack(World world, ItemStack stack)
	{
		if (hasStarStored(stack))
		{
			CompoundNBT nbt = stack.getTag();

			if (nbt.contains("StarEntityTag"))
			{
				StarEntity star = new StarEntity(world);
				star.deserializeNBT(nbt.getCompound("StarEntityTag"));
				return star;
			}
		}

		return new StarEntity(world);
	}
	
	public static boolean hasStarStored(ItemStack stack)
	{
		if(stack.getItem() instanceof StarControllerItem)
		{
			if(stack.getTag() != null)
			{
				if(stack.getTag().contains("StarEntityTag"))
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
