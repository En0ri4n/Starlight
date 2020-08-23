package fr.eno.starlight.handlers;

import fr.eno.starlight.References;
import fr.eno.starlight.init.InitDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = References.MOD_ID)
public class WorldHandler
{
	@SubscribeEvent
	public static void onMobSpawnInDarkDimension(LivingSpawnEvent event)
	{
		LivingEntity entity = event.getEntityLiving();
		
		if(entity instanceof MonsterEntity && entity.dimension.getRegistryName().equals(InitDimensions.DARK_DIM_LOC))
		{
			entity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 999999, 3, true, true));
			entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 999999, 1, true, true));
			entity.addPotionEffect(new EffectInstance(Effects.SPEED, 999999, 2, true, true));
			entity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 999999, 5, true, true));
		}
	}
	
	@SubscribeEvent
	public static void onDamageByMonsterInDarkDimension(LivingDamageEvent event)
	{
		if(event.getEntityLiving() instanceof PlayerEntity && event.getSource().getTrueSource() != null)
		{
			if(event.getSource().getTrueSource() instanceof MonsterEntity && event.getSource().getTrueSource().dimension.getRegistryName().equals(InitDimensions.DARK_DIM_LOC))
			{
				event.getEntityLiving().addPotionEffect(new EffectInstance(Effects.WITHER, 60, 2));
			}
		}
	}
}