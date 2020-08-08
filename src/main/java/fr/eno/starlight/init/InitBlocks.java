package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.block.BasicBlock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MOD_ID);
	
	public static final RegistryObject<Block> STAR_ORE = BLOCKS.register("star_ore", () -> new BasicBlock(3.0F));
}
