package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.item.ItemBlockBasic;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MOD_ID);
	
	public static final RegistryObject<Item> STAR_ORE = ITEMS.register("star_ore", () -> new ItemBlockBasic(InitBlocks.STAR_ORE.get()));
}
