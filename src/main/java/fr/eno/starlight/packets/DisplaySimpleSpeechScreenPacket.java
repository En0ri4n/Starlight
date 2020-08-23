package fr.eno.starlight.packets;

import java.util.function.Supplier;

import fr.eno.starlight.utils.ClientExecutor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class DisplaySimpleSpeechScreenPacket
{
	private ITextComponent text;

	public DisplaySimpleSpeechScreenPacket(ITextComponent iTextComponent)
	{
		this.text = iTextComponent;
	}

	public static void encode(DisplaySimpleSpeechScreenPacket msg, PacketBuffer buf)
	{
		buf.writeTextComponent(msg.text);
	}

	public static DisplaySimpleSpeechScreenPacket decode(PacketBuffer buf)
	{
		return new DisplaySimpleSpeechScreenPacket(buf.readTextComponent());
	}

	public static void handle(DisplaySimpleSpeechScreenPacket msg, Supplier<Context> ctx)
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientExecutor.openSimpleSpeechScreen(msg.text));
		ctx.get().setPacketHandled(true);
	}
}
