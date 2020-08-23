package fr.eno.starlight.client.screen.containers;

import com.mojang.blaze3d.systems.RenderSystem;

import fr.eno.starlight.References;
import fr.eno.starlight.container.ContainerStarChest;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class StarChestScreen extends ContainerScreen<ContainerStarChest>
{
	private static final ResourceLocation STAR_CHEST_GUI_TEXTURE = References.getLoc("textures/gui/container/star_chest.png");

	public StarChestScreen(ContainerStarChest screenContainer, PlayerInventory inv, ITextComponent titleIn)
	{
		super(screenContainer, inv, titleIn);
		this.xSize = 200;
		this.ySize = 200;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(STAR_CHEST_GUI_TEXTURE);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.blit(x, y, 0, 0, this.xSize, 200 * 18 + 17);
	}
}