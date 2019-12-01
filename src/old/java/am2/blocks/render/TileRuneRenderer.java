package am2.blocks.render;

import am2.api.affinity.Affinity;
import am2.blocks.tileentity.TileEntityGroundRuneSpell;
import am2.utils.AffinityShiftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class TileRuneRenderer extends TileEntitySpecialRenderer<TileEntityGroundRuneSpell> {

	@Override
	public void renderTileEntityAt(TileEntityGroundRuneSpell te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te.getSpellStack() != null) {
			Affinity mainAff = AffinityShiftUtils.getMainShiftForStack(te.getSpellStack());
			TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(mainAff.getRegistryName().getResourceDomain(), "blocks/runes/rune_" + mainAff.getRegistryName().getResourcePath()).toString());
			Tessellator tesselator = Tessellator.getInstance();
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.enableBlend();
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			RenderHelper.disableStandardItemLighting();
			int i = 0xF00F0;
	        int j = i >> 16 & 65535;
	        int k = i & 65535;
	        float height = 0.03125F;
			VertexBuffer wr = tesselator.getBuffer();
			wr.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
			
			float minU = sprite.getMinU();
			float minV = sprite.getMinV();
			float maxU = sprite.getMaxU();
			float maxV = sprite.getMaxV();

			wr.pos(1, 0, 0).tex(maxU, maxV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			wr.pos(1, 0, 1).tex(maxU, minV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			wr.pos(0, 0, 1).tex(minU, minV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			wr.pos(0, 0, 0).tex(minU, maxV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			
			wr.pos(0, height, 1).tex(minU, maxV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			wr.pos(1, height, 1).tex(maxU, maxV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			wr.pos(1, height, 0).tex(maxU, minV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			wr.pos(0, height, 0).tex(minU, minV).lightmap(j, k).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			tesselator.draw();
			GlStateManager.disableBlend();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.popMatrix();
		}
	}

}
