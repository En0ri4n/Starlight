package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.entity.ForceWaveEntity;
import fr.eno.starlight.entity.StarEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities
{
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.MOD_ID);
	
	public static final RegistryObject<EntityType<StarEntity>> STAR = ENTITIES.register("star", () -> EntityType.Builder.<StarEntity>create(StarEntity::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new StarEntity(world))
			.size(1F, 1F).build("star"));
	
	public static final RegistryObject<EntityType<ForceWaveEntity>> FORCEWAVE = ENTITIES.register("forcewave", () -> EntityType.Builder.<ForceWaveEntity>create(ForceWaveEntity::new, EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new ForceWaveEntity(world))
			.size(1F, 1F).build("forcewave"));
}
