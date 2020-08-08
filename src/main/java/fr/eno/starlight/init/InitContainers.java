package fr.eno.starlight.init;

import fr.eno.starlight.References;
import fr.eno.starlight.container.ContainerStarChest;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitContainers
{
	public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, References.MOD_ID);
	
	public static final RegistryObject<ContainerType<ContainerStarChest>> STAR_CHEST = CONTAINERS.register("star_chest", () -> IForgeContainerType.create(ContainerStarChest::new));
}
