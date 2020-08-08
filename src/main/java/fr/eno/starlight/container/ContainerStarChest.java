package fr.eno.starlight.container;

import fr.eno.starlight.init.InitContainers;
import fr.eno.starlight.container.utils.CommonContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;

public class ContainerStarChest extends CommonContainer
{
	public ContainerStarChest(int windowId, PlayerInventory playerInv, PacketBuffer data)
	{
		super(InitContainers.STAR_CHEST.get(), windowId, 200);
	}
}
