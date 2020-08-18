package fr.eno.starlight.world.teleporter;

import java.util.function.Function;

import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

public class DefaultTeleporter implements ITeleporter
{
	private static DefaultTeleporter INSTANCE;
	
	@Override
	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity)
	{
		return repositionEntity.apply(false);
	}
	
	public static final DefaultTeleporter getInstance()
	{
		return INSTANCE == null ? INSTANCE = new DefaultTeleporter() : INSTANCE;
	}
}
