package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.client.screen.TravelRequestSpaceScreen;
import fr.eno.starlight.utils.ScreenTravelManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class DisplayScreenPacket
{
	private int id;
	private UUID starId;
	
	public DisplayScreenPacket(int idIn, UUID starIdIn)
	{
		this.id = idIn;
		this.starId = starIdIn;
	}
	
	public static void encode(DisplayScreenPacket msg, PacketBuffer buf)
	{
		buf.writeInt(msg.id);
		buf.writeUniqueId(msg.starId);
	}
	
	public static DisplayScreenPacket decode(PacketBuffer buf)
	{
		return new DisplayScreenPacket(buf.readInt(), buf.readUniqueId());
	}
	
	public static class ClientHandler
	{
		public static void handle(DisplayScreenPacket msg, Supplier<Context> ctx)
		{
			Minecraft.getInstance().displayGuiScreen(new TravelRequestSpaceScreen(ScreenTravelManager.getById(msg.id), msg.starId));
			ctx.get().setPacketHandled(true);
		}
	}
}
