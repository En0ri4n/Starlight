package fr.eno.starlight.block;

import fr.eno.starlight.client.screen.TravelRequestSpaceScreen;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.utils.ScreenTravelManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class BasicBlock extends Block
{
	public BasicBlock(float hardness)
	{
		super(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(hardness));
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(worldIn.isRemote)
		{
			Minecraft.getInstance().displayGuiScreen(new TravelRequestSpaceScreen(ScreenTravelManager.ASTRAL, new StarEntity(worldIn).getUniqueID()));
			return ActionResultType.PASS;
		}
		else if(!worldIn.isRemote && handIn.equals(Hand.MAIN_HAND))
		{
			// ((ServerPlayerEntity) player).changeDimension(UtopianModDimension.getDimensionType(), new DefaultTeleporter());
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.FAIL;
	}
}
