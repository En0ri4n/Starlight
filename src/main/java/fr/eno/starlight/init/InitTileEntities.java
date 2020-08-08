package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.tileentity.StarChestTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitTileEntities
{
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.MOD_ID);
	
	public static final RegistryObject<TileEntityType<StarChestTile>> STAR_CHEST = TILE_ENTITIES.register("star_chest", () -> TileEntityType.Builder.create(StarChestTile::new).build(null));
}
