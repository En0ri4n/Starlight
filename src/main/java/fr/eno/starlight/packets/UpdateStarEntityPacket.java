package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.entity.StarEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class UpdateStarEntityPacket
{
	private UUID starId;

	public UpdateStarEntityPacket(UUID starIdIn)
	{
		this.starId = starIdIn;
	}

	public static void encode(UpdateStarEntityPacket msg, PacketBuffer buf)
	{
		buf.writeUniqueId(msg.starId);
	}

	public static UpdateStarEntityPacket decode(PacketBuffer buf)
	{
		return new UpdateStarEntityPacket(buf.readUniqueId());
	}

	public static void handle(UpdateStarEntityPacket msg, Supplier<Context> ctx)
	{
		ServerPlayerEntity player = ctx.get().getSender();
		Entity entity = player.getServerWorld().getEntityByUuid(msg.starId);

		if (entity instanceof StarEntity)
		{
			((StarEntity) entity).setTalking(false);
		}

		ctx.get().setPacketHandled(true);
	}
}
