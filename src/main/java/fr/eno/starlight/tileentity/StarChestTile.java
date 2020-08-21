package fr.eno.starlight.tileentity;

import fr.eno.starlight.container.ContainerStarChest;
import fr.eno.starlight.init.InitTileEntities;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = IChestLid.class)
public class StarChestTile extends LockableTileEntity implements ITickableTileEntity, IChestLid
{
	private NonNullList<ItemStack> chestContents = NonNullList.<ItemStack>withSize(200, ItemStack.EMPTY);
	private float prevLidAngle;
	private float lidAngle;
	private int ticksSinceSync;
	private int numPlayersUsing;

	public StarChestTile()
	{
		super(InitTileEntities.STAR_CHEST.get());
	}

	@Override
	public int getSizeInventory()
	{
		return this.chestContents.size();
	}

	@Override
	protected ITextComponent getDefaultName()
	{
		return new StringTextComponent("tile.star_chest.name");
	}

	@Override
	public void read(CompoundNBT compound)
	{
		super.read(compound);
		this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.chestContents);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound)
	{
		super.write(compound);
		ItemStackHelper.saveAllItems(compound, this.chestContents);

		return compound;
	}

	@Override
	public void tick()
	{
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
		++this.ticksSinceSync;
		this.numPlayersUsing = ChestTileEntity.calculatePlayersUsingSync(this.world, this, this.ticksSinceSync, x, y, z, this.numPlayersUsing);
		this.prevLidAngle = this.lidAngle;
		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
		{
			this.playSound();
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
		{
			float f1 = this.lidAngle;
			if (this.numPlayersUsing > 0)
			{
				this.lidAngle += 0.1F;
			} else
			{
				this.lidAngle -= 0.1F;
			}

			if (this.lidAngle > 1.0F)
			{
				this.lidAngle = 1.0F;
			}

			if (this.lidAngle < 0.5F && f1 >= 0.5F)
			{
				this.playSound();
			}

			if (this.lidAngle < 0.0F)
			{
				this.lidAngle = 0.0F;
			}
		}
	}

	private void playSound()
	{
		world.playSound((double) this.getPos().getX(), (double) this.getPos().getY(), (double) this.getPos().getZ(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 100F, 1F, true);
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player)
	{
		return new ContainerStarChest(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(getPos()));
	}

	/*
	 * Chest Lid Only on Client
	 */
	@OnlyIn(Dist.CLIENT)
	public float getLidAngle(float partialTicks)
	{
		return MathHelper.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.chestContents)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.chestContents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.chestContents, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.chestContents, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.chestContents.set(index, stack);
		
		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		if (this.world.getTileEntity(this.pos) != this)
		{
			return false;
		} else
		{
			return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void clear()
	{
		this.chestContents.clear();
	}
}