package fr.eno.starlight.client.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import fr.eno.starlight.entity.StarEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StarRenderer extends EntityRenderer<StarEntity>
{
	private final ItemRenderer itemRenderer;
	
	public StarRenderer(EntityRendererManager renderManager, ItemRenderer itemRendererIn)
	{
		super(renderManager);
		this.itemRenderer = itemRendererIn;
	}

	@Override
	public void render(StarEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		matrixStackIn.push();
		matrixStackIn.rotate(this.renderManager.getCameraOrientation());
		matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));

		float scale = entity.getSize();
		matrixStackIn.scale(scale, scale, scale);
		this.itemRenderer.renderItem(entity.getItem(), ItemCameraTransforms.TransformType.GROUND, 200, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
		matrixStackIn.pop();
		super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(StarEntity entity)
	{
		return PlayerContainer.LOCATION_BLOCKS_TEXTURE;
	}
}
