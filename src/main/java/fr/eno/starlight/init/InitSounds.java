package fr.eno.starlight.init;

import fr.eno.starlight.References;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.MOD_ID);
	
	public static final RegistryObject<SoundEvent> STAR_GROW = SOUNDS.register("star_grow", () -> new SoundEvent(References.getLoc("star_grow")));
}
