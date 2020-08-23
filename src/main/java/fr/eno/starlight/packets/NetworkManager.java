package fr.eno.starlight.packets;

import java.util.Optional;

import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkManager
{
	private static boolean initialized;
	
	private static SimpleChannel network;
	
	public static final void createNetwork(SimpleChannel networkIn)
	{
		if(!initialized)
		{
			NetworkManager.network = networkIn;
			initialized = true;
		}
	}
	
	public static void registerMessages()
	{
		int disc = 0;

		network.registerMessage(disc++, TravelToDimensionPacket.class, TravelToDimensionPacket::encode, TravelToDimensionPacket::decode, TravelToDimensionPacket::handle, toServer());
		network.registerMessage(disc++, UpdateStarEntityPacket.class, UpdateStarEntityPacket::encode, UpdateStarEntityPacket::decode, UpdateStarEntityPacket::handle, toServer());
		network.registerMessage(disc++, RewardPlayerPacket.class, RewardPlayerPacket::encode, RewardPlayerPacket::decode, RewardPlayerPacket::handle, toServer());
		network.registerMessage(disc++, DisplaySpeechScreenPacket.class, DisplaySpeechScreenPacket::encode, DisplaySpeechScreenPacket::decode, DisplaySpeechScreenPacket::handle, toClient());
		network.registerMessage(disc++, DisplaySimpleSpeechScreenPacket.class, DisplaySimpleSpeechScreenPacket::encode, DisplaySimpleSpeechScreenPacket::decode, DisplaySimpleSpeechScreenPacket::handle, toClient());
		network.registerMessage(disc++, DisplayTravelScreenPacket.class, DisplayTravelScreenPacket::encode, DisplayTravelScreenPacket::decode, DisplayTravelScreenPacket::handle, toClient());
	}

	private static Optional<NetworkDirection> toServer() { return Optional.of(NetworkDirection.PLAY_TO_SERVER); }
	private static Optional<NetworkDirection> toClient() { return Optional.of(NetworkDirection.PLAY_TO_CLIENT); }
	
	public static SimpleChannel getNetwork()
	{
		return network;
	}
}
