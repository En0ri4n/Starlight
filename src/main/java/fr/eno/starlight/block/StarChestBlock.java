package fr.eno.starlight.block;

import java.util.function.BiPredicate;

import fr.eno.starlight.init.InitTileEntities;
import fr.eno.starlight.tileentity.StarChestTile;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class StarChestBlock extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE_NORTH = Block.makeCuboidShape(1.0D, 0.0D, 0.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_SOUTH = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 16.0D);
	protected static final VoxelShape SHAPE_WEST = Block.makeCuboidShape(0.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);
	protected static final VoxelShape SHAPE_EAST = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 16.0D, 14.0D, 15.0D);

	public StarChestBlock()
	{
		super(Block.Properties.create(Material.IRON).hardnessAndResistance(3F).harvestTool(ToolType.PICKAXE).sound(SoundType.WOOD));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof StarChestTile)
			{
				NetworkHooks.openGui((ServerPlayerEntity) player, (StarChestTile) tileentity, pos);
			}
		}

		return ActionResultType.FAIL;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, this.getDefaultState().with(FACING, placer.getAdjustedHorizontalFacing().getOpposite()));
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if (state.getBlock() != newState.getBlock())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof StarChestTile)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, (StarChestTile) tileentity);
			}

			if (state.hasTileEntity() && (state.getBlock() != newState.getBlock() || !newState.hasTileEntity()))
			{
				worldIn.removeTileEntity(pos);
			}
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		switch(state.get(FACING))
		{
			case WEST:
				return SHAPE_WEST;
			case EAST:
				return SHAPE_EAST;
			case SOUTH:
				return SHAPE_SOUTH;
			case NORTH:
				default:
					return SHAPE_NORTH;
		}
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new StarChestTile();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	public TileEntityMerger.ICallbackWrapper<? extends StarChestTile> getCallbackWrapper(BlockState state, World world, BlockPos pos, boolean p_225536_4_)
	{
		BiPredicate<IWorld, BlockPos> bipredicate;
		if (p_225536_4_)
		{
			bipredicate = (p_226918_0_, p_226918_1_) -> {
				return false;
			};
		} else
		{
			bipredicate = ChestBlock::isBlocked;
		}

		return TileEntityMerger.func_226924_a_(InitTileEntities.STAR_CHEST.get(), this::getType, this::getDirectionToAttached, FACING, state, world, pos, bipredicate);
	}

	@OnlyIn(Dist.CLIENT)
	public static TileEntityMerger.ICallback<StarChestTile, Float2FloatFunction> getFloatFunction(final IChestLid chestLid)
	{
		return new TileEntityMerger.ICallback<StarChestTile, Float2FloatFunction>()
		{
			public Float2FloatFunction func_225539_a_(StarChestTile p_225539_1_, StarChestTile p_225539_2_)
			{
				return (p_226921_2_) -> {
					return Math.max(p_225539_1_.getLidAngle(p_226921_2_), p_225539_2_.getLidAngle(p_226921_2_));
				};
			}

			public Float2FloatFunction func_225538_a_(StarChestTile p_225538_1_)
			{
				return p_225538_1_::getLidAngle;
			}

			public Float2FloatFunction func_225537_b_()
			{
				return chestLid::getLidAngle;
			}
		};
	}

	public static boolean isBlocked(IWorld world, BlockPos pos)
	{
		return ChestBlock.isBlocked(world, pos);
	}

	public TileEntityMerger.Type getType(BlockState state)
	{
		return TileEntityMerger.Type.SINGLE;
	}

	public Direction getDirectionToAttached(BlockState state)
	{
		return Direction.NORTH;
	}
}