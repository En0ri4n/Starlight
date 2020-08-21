package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.item.ItemBlockBasic;
import fr.eno.starlight.item.StarArmorItem;
import fr.eno.starlight.item.StarControllerItem;
import fr.eno.starlight.item.StarSummonerItem;
import fr.eno.starlight.item.StarWandItem;
import fr.eno.starlight.utils.Tabs;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MOD_ID);

	public static final RegistryObject<Item> STAR_ORE = ITEMS.register("star_ore", () -> new ItemBlockBasic(InitBlocks.STAR_ORE.get()));
	public static final RegistryObject<Item> STAR_STONE = ITEMS.register("star_stone", () -> new ItemBlockBasic(InitBlocks.STAR_STONE.get()));
	public static final RegistryObject<Item> STAR_GRASS = ITEMS.register("star_grass", () -> new ItemBlockBasic(InitBlocks.STAR_GRASS.get()));
	public static final RegistryObject<Item> STAR_CHEST = ITEMS.register("star_chest", () -> new ItemBlockBasic(InitBlocks.STAR_CHEST.get()));
	public static final RegistryObject<Item> FRAGMENTED_STAR_ORE = ITEMS.register("fragmented_star_ore", () -> new ItemBlockBasic(InitBlocks.FRAGMENTED_STAR_ORE.get()));

	public static final RegistryObject<Item> STAR_FRAGMENT = ITEMS.register("star_fragment", () -> new Item(new Item.Properties().maxStackSize(64).group(Tabs.ITEMS)));
	public static final RegistryObject<Item> STAR = ITEMS.register("star", () -> new Item(new Item.Properties().maxStackSize(1)));
	public static final RegistryObject<Item> OVERWORLD_STAR_WAND = ITEMS.register("overworld_star_wand", () -> new StarWandItem(DimensionType.OVERWORLD.getRegistryName(), 0));
	public static final RegistryObject<Item> ASTRAL_STAR_WAND = ITEMS.register("astral_star_wand", () -> new StarWandItem(InitDimensions.ASTRAL_DIM_LOC, 1));
	public static final RegistryObject<Item> UTOPIAN_STAR_WAND = ITEMS.register("utopian_star_wand", () -> new StarWandItem(InitDimensions.UTOPIAN_DIM_LOC, 3));
	public static final RegistryObject<Item> DARK_STAR_WAND = ITEMS.register("dark_star_wand", () -> new StarWandItem(InitDimensions.DARK_DIM_LOC, 2));
	public static final RegistryObject<Item> STAR_CONTROLLER = ITEMS.register("star_controller", () -> new StarControllerItem());
	public static final RegistryObject<Item> STAR_SUMMONER = ITEMS.register("star_summoner", () -> new StarSummonerItem());

	public static final RegistryObject<Item> STAR_HELMET = ITEMS.register("star_helmet", () -> new StarArmorItem(EquipmentSlotType.HEAD));
	public static final RegistryObject<Item> STAR_CHESTPLATE = ITEMS.register("star_chestplate", () -> new StarArmorItem(EquipmentSlotType.CHEST));
	public static final RegistryObject<Item> STAR_LEGGINGS = ITEMS.register("star_leggings", () -> new StarArmorItem(EquipmentSlotType.LEGS));
	public static final RegistryObject<Item> STAR_BOOTS = ITEMS.register("star_boots", () -> new StarArmorItem(EquipmentSlotType.FEET));
}