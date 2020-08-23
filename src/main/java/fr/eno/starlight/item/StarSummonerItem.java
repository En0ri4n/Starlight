package fr.eno.starlight.item;

import fr.eno.starlight.References;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.utils.Tabs;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class StarSummonerItem extends Item
{
	public StarSummonerItem()
	{
		super(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON).setNoRepair().group(Tabs.ITEMS));
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext ctx)
	{
		World world = ctx.getWorld();
		PlayerEntity player = ctx.getPlayer();
		BlockPos pos = ctx.getPos();
		
		if(world.isRemote)
		{
			return ActionResultType.PASS;
		}
		else
		{
			StarEntity star = new StarEntity(world);
			star.setPosition(pos.getX(), pos.getY() + 2, pos.getZ());
			star.setCustomName(References.getTranslate("item.StarSummoner.starName", player.getName()));
			star.setCustomNameVisible(true);
			world.addEntity(new LightningBoltEntity(world, pos.getX(), pos.getY(), pos.getZ(), false));
			((ServerWorld) world).addEntityIfNotDuplicate(star);
			player.getHeldItem(ctx.getHand()).shrink(1);
			return ActionResultType.SUCCESS;
		}
	}
}