package fr.eno.starlight.tileentity;

import fr.eno.starlight.init.InitTileEntities;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class StarChestTile extends LockableLootTileEntity
{
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(200, ItemStack.EMPTY);
	
	public StarChestTile()
	{
		super(InitTileEntities.STAR_CHEST.get());
	}

	@Override
	public int getSizeInventory()
	{
		return this.inventory.size();
	}

	@Override
	protected NonNullList<ItemStack> getItems()
	{
		return this.inventory;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> itemsIn)
	{
		this.inventory = itemsIn;		
	}

	@Override
	protected ITextComponent getDefaultName()
	{
		return new StringTextComponent("tile.star_chest.name");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player)
	{
		return null;
	}
}
