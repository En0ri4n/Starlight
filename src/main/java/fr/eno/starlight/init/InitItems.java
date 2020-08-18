package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.item.ItemBlockBasic;
import fr.eno.starlight.item.StarWandItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MOD_ID);

	public static final RegistryObject<Item> STAR_ORE = ITEMS.register("star_ore", () -> new ItemBlockBasic(InitBlocks.STAR_ORE.get()));
	public static final RegistryObject<Item> STAR_STONE = ITEMS.register("star_stone", () -> new ItemBlockBasic(InitBlocks.STAR_STONE.get()));
	public static final RegistryObject<Item> STAR_GRASS = ITEMS.register("star_grass", () -> new ItemBlockBasic(InitBlocks.STAR_GRASS.get()));
	public static final RegistryObject<Item> FRAGMENTED_STAR_ORE = ITEMS.register("fragmented_star_ore", () -> new ItemBlockBasic(InitBlocks.FRAGMENTED_STAR_ORE.get()));

	public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<Item> ASTRAL_STAR_WAND = ITEMS.register("astral_star_wand", () -> new StarWandItem(InitDimensions.ASTRAL_DIM_TYPE, 0));
	public static final RegistryObject<Item> UTOPIAN_STAR_WAND = ITEMS.register("utopian_star_wand", () -> new StarWandItem(InitDimensions.UTOPIAN_DIM_TYPE, 2));
}
