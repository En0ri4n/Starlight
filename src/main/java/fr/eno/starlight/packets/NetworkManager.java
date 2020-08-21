package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.packets.DisplayScreenPacket;
import fr.eno.starlight.packets.TravelToDimensionPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class InitPackets
{
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	
	public static final SimpleChannel NETWORK = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(References.MOD_ID, "main_channel"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	
	public static void registerServerMessages()
	{
		int disc = 0;

		NETWORK.registerMessage(disc++, TravelToDimensionPacket.class, TravelToDimensionPacket::encode, TravelToDimensionPacket::decode, TravelToDimensionPacket.ServerHandler::handle);
	}
	
	public static void registerClientMessages()
	{
		int disc = 0;
		
		NETWORK.registerMessage(disc++, DisplayScreenPacket.class, DisplayScreenPacket::encode, DisplayScreenPacket::decode, DisplayScreenPacket.ClientHandler::handle);
	}
}
