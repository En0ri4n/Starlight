package fr.eno.starlight.client.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import fr.eno.starlight.References;
import fr.eno.starlight.block.StarChestBlock;
import fr.eno.starlight.tileentity.StarChestTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityMerger.ICallbackWrapper;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class StarChestTileRenderer extends TileEntityRenderer<StarChestTile>
{
	public static final ResourceLocation STAR_CHEST_TEXTURE = References.getLoc("entity/chest/star_chest");
	private ModelRenderer singleBottom;
	private ModelRenderer singleLid;
	private ModelRenderer singleLatch;

	public StarChestTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);

		this.singleBottom = new ModelRenderer(64, 64, 0, 19);
		this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
		this.singleLid = new ModelRenderer(64, 64, 0, 0);
		this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.singleLid.rotationPointY = 9.0F;
		this.singleLid.rotationPointZ = 1.0F;
		this.singleLatch = new ModelRenderer(64, 64, 0, 0);
		this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.singleLatch.rotationPointY = 8.0F;
	}

	@Override
	public void render(StarChestTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		World world = tileEntityIn.getWorld();
		boolean flag = world != null;
		BlockState blockstate = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(StarChestBlock.FACING, Direction.SOUTH);
		Block block = blockstate.getBlock();
		if (block instanceof StarChestBlock)
		{
			StarChestBlock starChestBlock = (StarChestBlock) block;
			matrixStackIn.push();
			float f = blockstate.has(StarChestBlock.FACING) ? blockstate.get(StarChestBlock.FACING).getHorizontalAngle() : Direction.NORTH.getHorizontalAngle();
			Material material = new Material(Atlases.CHEST_ATLAS, STAR_CHEST_TEXTURE);
			matrixStackIn.translate(0.5D, 0.5D, 0.5D);
			matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
			matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
			ICallbackWrapper<? extends StarChestTile> icallbackwrapper;
			if (flag)
			{
				icallbackwrapper = starChestBlock.getCallbackWrapper(blockstate, world, tileEntityIn.getPos(), true);
			} else
			{
				icallbackwrapper = TileEntityMerger.ICallback::func_225537_b_;
			}

			float lidAndgle = icallbackwrapper.apply(StarChestBlock.getFloatFunction(((IChestLid) tileEntityIn))).get(partialTicks);
			lidAndgle = 1.0F - lidAndgle;
			lidAndgle = 1.0F - lidAndgle * lidAndgle * lidAndgle;
			int light = icallbackwrapper.apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
			IVertexBuilder ivertexbuilder = material.getBuffer(bufferIn, RenderType::getEntityCutout);
			this.renderModels(matrixStackIn, ivertexbuilder, this.singleLid, this.singleLatch, this.singleBottom, lidAndgle, light, combinedOverlayIn);

			matrixStackIn.pop();
		}
	}

	private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn)
	{
		chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));
		chestLatch.rotateAngleX = chestLid.rotateAngleX;
		chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
		chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
	}
}