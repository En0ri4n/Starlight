package fr.eno.starlight.item;

import fr.eno.starlight.entity.StarEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StarSummonerItem extends Item
{
	public StarSummonerItem()
	{
		super(new Item.Properties().maxStackSize(1).rarity(Rarity.UNCOMMON).setNoRepair());
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
			star.setPosition(pos.getX(), pos.getY(), pos.getZ());
			star.setMotion(0, -30, 0);
			world.addEntity(star);
			player.getHeldItem(ctx.getHand()).shrink(1);
			return ActionResultType.SUCCESS;
		}
	}
}