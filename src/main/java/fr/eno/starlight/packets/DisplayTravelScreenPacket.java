package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.utils.ClientExecutor;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class DisplayTravelScreenPacket
{
	private int id;
	private UUID starId;

	public DisplayTravelScreenPacket(int idIn, UUID starIdIn)
	{
		this.id = idIn;
		this.starId = starIdIn;
	}

	public static void encode(DisplayTravelScreenPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.id);
		buf.writeUniqueId(msg.starId);
	}

	public static DisplayTravelScreenPacket decode(PacketBuffer buf)
	{
		return new DisplayTravelScreenPacket(buf.readInt(), buf.readUniqueId());
	}
	
	public static void handle(DisplayTravelScreenPacket msg, Supplier<Context> ctx)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientExecutor.openTravelScreen(msg.id, msg.starId));
		ctx.get().setPacketHandled(true);
	}
}