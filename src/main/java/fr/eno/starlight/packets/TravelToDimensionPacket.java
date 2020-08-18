package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.world.teleporter.DefaultTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class TravelToDimensionPacket
{
	private ResourceLocation dimension;
	private UUID starId;

	public TravelToDimensionPacket(ResourceLocation dimension, UUID starIdIn)
	{
		this.dimension = dimension;
		this.starId = starIdIn;
	}
	
	public static void encode(TravelToDimensionPacket msg, PacketBuffer buf)
	{
		buf.writeResourceLocation(msg.dimension);
		buf.writeUniqueId(msg.starId);
	}
	
	public static TravelToDimensionPacket decode(PacketBuffer buf)
	{
		return new TravelToDimensionPacket(buf.readResourceLocation(), buf.readUniqueId());
		}
	
	public static class ServerHandler
	{
		public static void handle(TravelToDimensionPacket msg, Supplier<Context> ctx)
		{			
			ServerPlayerEntity player = ctx.get().getSender();
			Entity entity = player.getServerWorld().getEntityByUuid(msg.starId);
			
			if(entity instanceof StarEntity)
			{
				entity.changeDimension(DimensionType.byName(msg.dimension), new DefaultTeleporter());
				player.changeDimension(DimensionType.byName(msg.dimension), new DefaultTeleporter());
				entity.setPositionAndUpdate(player.getPosX(), player.getPosY(), player.getPosZ());
			}
			
			ctx.get().setPacketHandled(true);
		}
	}
}
