package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.block.BasicBlock;
import fr.eno.starlight.block.StarChestBlock;
import net.minecraft.block.Block;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MOD_ID);

	public static final RegistryObject<Block> FRAGMENTED_STAR_ORE = BLOCKS.register("fragmented_star_ore", () -> new BasicBlock(3.0F, ToolType.PICKAXE));
	public static final RegistryObject<Block> STAR_GRASS = BLOCKS.register("star_grass", () -> new BasicBlock(1.5F, ToolType.SHOVEL));
	public static final RegistryObject<Block> STAR_STONE = BLOCKS.register("star_stone", () -> new BasicBlock(3.0F, ToolType.PICKAXE));
	public static final RegistryObject<Block> STAR_ORE = BLOCKS.register("star_ore", () -> new BasicBlock(3.0F, ToolType.PICKAXE));
	
	public static final RegistryObject<Block> STAR_CHEST = BLOCKS.register("star_chest", () -> new StarChestBlock());
}
