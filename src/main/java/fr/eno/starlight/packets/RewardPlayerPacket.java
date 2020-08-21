package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.init.InitItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class RewardPlayerPacket
{
	private UUID starId;

	public RewardPlayerPacket(UUID starIdIn)
	{
		this.starId = starIdIn;
	}

	public static void encode(RewardPlayerPacket msg, PacketBuffer buf)
	{
		buf.writeUniqueId(msg.starId);
	}

	public static RewardPlayerPacket decode(PacketBuffer buf)
	{
		return new RewardPlayerPacket(buf.readUniqueId());
	}

	public static void handle(RewardPlayerPacket msg, Supplier<Context> ctx)
	{
		ServerPlayerEntity player = ctx.get().getSender();
		ServerWorld world = player.getServerWorld();
		Entity entity = world.getEntityByUuid(msg.starId);

		if (entity instanceof StarEntity)
		{
			StarEntity star = (StarEntity) entity;

			if (star.canGiveReward())
			{
				player.getServer().deferTask(() -> {
					player.addItemStackToInventory(new ItemStack(InitItems.STAR_HELMET.get(), 1));
					player.addItemStackToInventory(new ItemStack(InitItems.STAR_CHESTPLATE.get(), 1));
					player.addItemStackToInventory(new ItemStack(InitItems.STAR_LEGGINGS.get(), 1));
					player.addItemStackToInventory(new ItemStack(InitItems.STAR_BOOTS.get(), 1));
					star.setNoReward();
				});
			}
		}

		ctx.get().setPacketHandled(true);
	}
}