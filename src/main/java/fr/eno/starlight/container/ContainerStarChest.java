package fr.eno.starlight.container;

import fr.eno.starlight.container.utils.CommonContainer;
import fr.eno.starlight.init.InitContainers;
import fr.eno.starlight.tileentity.StarChestTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;

public class ContainerStarChest extends CommonContainer
{
	public ContainerStarChest(int windowId, PlayerInventory playerInv, PacketBuffer data)
	{
		super(InitContainers.STAR_CHEST.get(), windowId, 200);
		
		StarChestTile tile = (StarChestTile) playerInv.player.world.getTileEntity(data.readBlockPos());
		
		this.bindPlayerInventory(playerInv, 107, 210);
		
		int slot = 0;
		for(int x = 0; x < 20; x++)
			for(int y = 0; y < 10; y++)
				this.addSlot(new Slot(tile, slot++, 8 + x * 18, 18 + y * 18));
	}
}
