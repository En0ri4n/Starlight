package fr.eno.starlight.client.handlers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import fr.eno.starlight.References;
import fr.eno.starlight.client.entity.renderer.StarChestTileRenderer;
import fr.eno.starlight.client.utils.RenderUtils;
import fr.eno.starlight.entity.StarEntity;
import fr.eno.starlight.utils.ClientExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.AtlasTexture.SheetData;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite.Info;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = References.MOD_ID, value = Dist.CLIENT)
public class ClientHandler
{
	private static final Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent
	public static void onMountOnStar(EntityMountEvent event)
	{
		if (event.getEntityBeingMounted() != null)
		{
			if (event.getEntityBeingMounted() instanceof StarEntity)
			{
				DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientExecutor.changePlayerView());
			}
		}
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent event)
	{
		AtlasTexture atlas = event.getMap();		
		Stitcher stitcher = new Stitcher(1, 256, 256);
		stitcher.addSprite(new Info(RenderUtils.CHEST_ATLAS, 64, 64, AnimationMetadataSection.EMPTY));
		SheetData sheetData = new SheetData(Sets.newHashSet(StarChestTileRenderer.STAR_CHEST_TEXTURE), stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), 1, getStitchedSprites(atlas, mc.getResourceManager(), stitcher, 1));
		atlas.upload(sheetData);
	}

	private static List<TextureAtlasSprite> getStitchedSprites(AtlasTexture atlas, IResourceManager resourceManagerIn, Stitcher stitcherIn, int mipmapLevelIn)
	{
		ConcurrentLinkedQueue<TextureAtlasSprite> concurrentlinkedqueue = new ConcurrentLinkedQueue<>();
		List<CompletableFuture<?>> list = Lists.newArrayList();
		stitcherIn.getStichSlots((p_229215_5_, p_229215_6_, p_229215_7_, p_229215_8_, p_229215_9_) -> {
			if (p_229215_5_ == MissingTextureSprite.getSpriteInfo())
			{
				MissingTextureSprite missingtexturesprite = MissingTextureSprite.create(atlas, mipmapLevelIn, p_229215_6_, p_229215_7_, p_229215_8_, p_229215_9_);
				concurrentlinkedqueue.add(missingtexturesprite);
			} else
			{
				list.add(CompletableFuture.runAsync(() -> {
					TextureAtlasSprite textureatlassprite = atlas.getSprite(Atlases.CHEST_ATLAS);
					{
						concurrentlinkedqueue.add(textureatlassprite);
					}

				}, Util.getServerExecutor()));
			}

		});
		CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
		return Lists.newArrayList(concurrentlinkedqueue);
	}
}
