package fr.eno.starlight.item;

import java.util.Arrays;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import fr.eno.starlight.References;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.init.InitSounds;
import fr.eno.starlight.utils.Tabs;
import fr.eno.starlight.world.teleporter.DefaultTeleporter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
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
		if (stack.getTag() == null)
		{
			CompoundNBT nbt = new CompoundNBT();
			nbt.putInt("Level", this.getLevel());
			nbt.putBoolean("Activated", false);
			nbt.putIntArray("OverworldSpawnPosition", Arrays.asList(0, 75, 0));
			nbt.putIntArray("DimensionSpawnPosition", Arrays.asList(0, 75, 0));
			stack.setTag(nbt);
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (dimension != null && Minecraft.getInstance().world != null && this.getDimension() != null)
			tooltip.add(new StringTextComponent(ChatFormatting.GOLD + "Dimension : " + this.getDimension().getRegistryName().getPath()));

		if (stack.getTag() != null)
		{
			tooltip.add(new StringTextComponent("§6Level : §e" + this.getLevel()));

			if (stack.getTag().getBoolean("Activated"))
			{
				int[] overworldPos = stack.getTag().getIntArray("OverworldSpawnPosition");
				tooltip.add(new StringTextComponent(String.format("§3Overworld Spawn x:§b%s §3y:§b%s §3z:§b%s", overworldPos[0], overworldPos[1], overworldPos[2])));
				int[] dimPos = stack.getTag().getIntArray("DimensionSpawnPosition");
				tooltip.add(new StringTextComponent(String.format("§1Dimension Spawn x:§b%s §1y:§b%s §1z:§b%s", dimPos[0], dimPos[1], dimPos[2])));
				tooltip.add(new StringTextComponent(""));

				tooltip.add(References.getTranslate("item.StarWand.activated"));
			} else
			{
				tooltip.add(References.getTranslate("item.StarWand.desactivated"));
			}
		}
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		PlayerEntity player = ctx.getPlayer();
		ItemStack stack = ctx.getItem();
		BlockPos pos = ctx.getPos();
		List<Integer> array = Arrays.asList(pos.getX(), pos.getY(), pos.getZ());

		if (!world.isRemote)
		{
			if(!isActivated(stack))
			{
				player.sendMessage(References.getTranslate("item.StarWand.notActivated"));
				return ActionResultType.FAIL;
			}
			
			if (player.isSneaking())
			{
				CompoundNBT nbt = stack.getTag();

				if (player.dimension == this.getDimension())
				{
					nbt.putIntArray("DimensionSpawnPosition", array);

					player.sendMessage(References.getTranslate("item.StarWand.SetDimensionSpawn", array.get(0), array.get(1), array.get(2)));
				} else if (player.dimension == DimensionType.OVERWORLD)
				{
					nbt.putIntArray("OverworldSpawnPosition", array);

					player.sendMessage(References.getTranslate("item.StarWand.SetOverworldSpawn", array.get(0), array.get(1), array.get(2)));
				} else
				{
					player.sendMessage(References.getTranslate("item.StarWand.notInDimension"));
					return ActionResultType.FAIL;
				}

				stack.setTag(nbt);

				return ActionResultType.SUCCESS;
			} else
			{
				if (this.getDimension() == DimensionType.OVERWORLD && player.dimension == DimensionType.OVERWORLD)
				{
					player.sendMessage(References.getTranslate("item.StarWand.alreadyInDimension"));
					return ActionResultType.FAIL;
				}

				player.sendMessage(References.getTranslate("item.StarWand.teleportation"));

				if (player.dimension == this.getDimension())
				{
					int[] overworldPos = stack.getTag().getIntArray("OverworldSpawnPosition");
					player.changeDimension(DimensionType.OVERWORLD, DefaultTeleporter.getInstance());
					player.setPositionAndUpdate(overworldPos[0], overworldPos[1], overworldPos[2]);
					return ActionResultType.SUCCESS;
				} else
				{
					int[] dimPos = stack.getTag().getIntArray("DimensionSpawnPosition");
					player.changeDimension(this.getDimension(), DefaultTeleporter.getInstance());
					player.setPositionAndUpdate(dimPos[0], dimPos[1], dimPos[2]);
					return ActionResultType.SUCCESS;
				}
			}
		}

		return ActionResultType.FAIL;

	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity player, LivingEntity target, Hand hand)
	{
		if (!(target instanceof StarEntity))
		{
			return false;
		}

		if (stack.getItem() instanceof StarWandItem && !player.world.isRemote)
		{
			StarEntity star = (StarEntity) target;
			int itemLevel = ((StarWandItem) stack.getItem()).getLevel();

			if (itemLevel == star.getLevel() && !StarWandItem.isActivated(stack))
			{
				CompoundNBT nbt = stack.getTag();
				nbt.putBoolean("Activated", true);
				stack.setTag(nbt);
				star.growUp();

				player.sendMessage(References.getTranslate("item.StarWand.starGrowUp"));
				player.world.playSound(player, star.getPosX(), star.getPosY(), star.getPosZ(), InitSounds.STAR_GROW.get(), SoundCategory.NEUTRAL, 100, 1F);
				return true;
			}
			else if(StarWandItem.isActivated(stack))
			{
				player.sendMessage(References.getTranslate("item.StarWand.alreadyActivated"));
				return true;
			}
		}

		return false;
	}

	private DimensionType getDimension()
	{
		return DimensionType.byName(dimension);
	}

	public int getLevel()
	{
		return this.level;
	}

	public static boolean isActivated(ItemStack stack)
	{
		if (stack.getItem() instanceof StarWandItem)
		{
			if (stack.getTag().getBoolean("Activated"))
			{
				return true;
			}
		}

		return false;
	}
}