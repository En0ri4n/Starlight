package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.utils.ClientExecutor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class DisplaySpeechScreenPacket
{
	private ResourceLocation loc;
	private UUID starId;

	public DisplaySpeechScreenPacket(ResourceLocation locIn, UUID starIdIn)
	{
		this.loc = locIn;
		this.starId = starIdIn;
	}

	public static void encode(DisplaySpeechScreenPacket msg, PacketBuffer buf)
	{
		buf.writeResourceLocation(msg.loc);
		buf.writeUniqueId(msg.starId);
	}

	public static DisplaySpeechScreenPacket decode(PacketBuffer buf)
	{
		return new DisplaySpeechScreenPacket(buf.readResourceLocation(), buf.readUniqueId());
	}

	public static void handle(DisplaySpeechScreenPacket msg, Supplier<Context> ctx)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientExecutor.openSpeechScreen(msg.loc, msg.starId));
		ctx.get().setPacketHandled(true);
	}
}
