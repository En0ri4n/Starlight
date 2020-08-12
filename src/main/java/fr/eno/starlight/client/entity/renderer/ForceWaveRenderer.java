package fr.eno.starlight.client.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import fr.eno.starlight.References;
import fr.eno.starlight.client.entity.renderer.model.ForceWaveModel;
import fr.eno.starlight.entity.ForceWaveEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class ForceWaveRenderer extends EntityRenderer<ForceWaveEntity> implements IEntityRenderer<ForceWaveEntity, ForceWaveModel>
{
	private ForceWaveModel model;

	public ForceWaveRenderer(EntityRendererManager renderManager)
	{
		super(renderManager);
		this.model = new ForceWaveModel();
	}

	@Override
	public void render(ForceWaveEntity forceWave, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
	{
		boolean flag = !forceWave.isInvisible();
		boolean flag1 = !flag && !forceWave.isInvisibleToPlayer(Minecraft.getInstance().player);
		RenderType rendertype = RenderType.getEntityTranslucent(this.getEntityTexture(forceWave));
		if (rendertype != null)
		{
			IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
			this.model.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
		}
		super.render(forceWave, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getEntityTexture(ForceWaveEntity entity)
	{
		return new ResourceLocation(References.MOD_ID, "textures/misc/forcewave_256.png");
	}

	@Override
	public ForceWaveModel getEntityModel()
	{
		return model;
	}
}
