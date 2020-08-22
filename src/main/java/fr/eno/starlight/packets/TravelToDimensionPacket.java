package fr.eno.starlight.packets;

import java.util.UUID;
import java.util.function.Supplier;

import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.world.teleporter.DefaultTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;

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

	public static void handle(TravelToDimensionPacket msg, Supplier<Context> ctx)
	{
		ServerPlayerEntity player = ctx.get().getSender();
		Entity entity = player.getServerWorld().getEntityByUuid(msg.starId);

		if (entity instanceof StarEntity)
		{
			StarEntity starEntity = (StarEntity) entity;
			StarEntity star = starEntity;
			double x = star.getPosX();
			double y = star.getPosY();
			double z = star.getPosZ();
			float yaw = star.rotationYaw;
			float pitch = star.rotationPitch;
			starEntity = (StarEntity) starEntity.getType().create(player.getServerWorld());
			starEntity.copyDataFromOld(star);
			starEntity.setLocationAndAngles(x, y, z, yaw, pitch);
            starEntity.setRotationYawHead(yaw);
            player.getServerWorld().addFromAnotherDimension(starEntity);
            
			player.changeDimension(DimensionType.byName(msg.dimension), DefaultTeleporter.getInstance());
			player.setPositionAndUpdate(0, 75, 0);
			player.startRiding(starEntity);
			
			player.getServer().enqueue(new TickDelayedTask(100, () ->
			{
				NetworkManager.getNetwork().send(PacketDistributor.PLAYER.with(() -> player), new DisplaySpeechScreenPacket(DimensionType.byName(msg.dimension).getRegistryName(), msg.starId));
			}));
		}

		ctx.get().setPacketHandled(true);
	}
}