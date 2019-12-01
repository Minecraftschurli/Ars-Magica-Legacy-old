package am2.blocks.render;

import org.lwjgl.opengl.GL11;

import am2.blocks.tileentity.TileEntityEverstone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class TileEverstoneRenderer extends TileEntitySpecialRenderer<TileEntityEverstone> {

	@Override
	public void renderTileEntityAt(TileEntityEverstone te, double x, double y, double z, float partialTicks, int destroyStage) {
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if (te.isSolid()) {
			if (te.getFacade() != null) {
				GlStateManager.pushMatrix();
				RenderHelper.disableStandardItemLighting();
				GlStateManager.translate(x, y, z);
				GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
				Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.BLOCK);
				Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(te.getFacade(), te.getPos(), te.getWorld(), Tessellator.getInstance().getBuffer());
				Tessellator.getInstance().draw();
				if (destroyStage >= 0) {
					Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.BLOCK);
					TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("minecraft:blocks/destroy_stage_" + destroyStage);
		            IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(te.getFacade());
		            IBakedModel ibakedmodel1 = net.minecraftforge.client.ForgeHooksClient.getDamageModel(ibakedmodel, sprite, te.getFacade(), te.getWorld(), te.getPos());
					Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(te.getWorld(), ibakedmodel1, te.getFacade(), te.getPos(), Tessellator.getInstance().getBuffer(), true);
					Tessellator.getInstance().draw();
				}
				GlStateManager.popMatrix();
				RenderHelper.enableStandardItemLighting();
			}
		}
	}
	
	public void render(TextureAtlasSprite sprite) {
		if (sprite != null)
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		float minU = (sprite != null ? sprite.getMinU() : 0F);
		float maxU = (sprite != null ? sprite.getMaxU() : 1F);
		float minV = (sprite != null ? sprite.getMinV() : 0F);
		float maxV = (sprite != null ? sprite.getMaxV() : 1F);
		//LogHelper.info(sprite);
		GL11.glPushMatrix();
		RenderHelper.disableStandardItemLighting();
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(0, 1, 0).tex(maxU, minV).endVertex();
		buffer.pos(1, 1, 0).tex(minU, minV).endVertex();
		buffer.pos(1, 0, 0).tex(minU, maxV).endVertex();
		buffer.pos(0, 0, 0).tex(maxU, maxV).endVertex();
		
		buffer.pos(1, 0, 1).tex(minU, maxV).endVertex();
		buffer.pos(1, 1, 1).tex(minU, minV).endVertex();
		buffer.pos(0, 1, 1).tex(maxU, minV).endVertex();
		buffer.pos(0, 0, 1).tex(maxU, maxV).endVertex();
		tessellator.draw();
		
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(1, 0, 0).tex(maxU, maxV).endVertex();
		buffer.pos(1, 0, 1).tex(maxU, minV).endVertex();
		buffer.pos(0, 0, 1).tex(minU, minV).endVertex();
		buffer.pos(0, 0, 0).tex(minU, maxV).endVertex();
		
		buffer.pos(0, 1, 1).tex(minU, maxV).endVertex();
		buffer.pos(1, 1, 1).tex(maxU, maxV).endVertex();
		buffer.pos(1, 1, 0).tex(maxU, minV).endVertex();
		buffer.pos(0, 1, 0).tex(minU, minV).endVertex();
		tessellator.draw();
				
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(1, 1, 0).tex(maxU, minV).endVertex();
		buffer.pos(1, 1, 1).tex(minU, minV).endVertex();
		buffer.pos(1, 0, 1).tex(minU, maxV).endVertex();
		buffer.pos(1, 0, 0).tex(maxU, maxV).endVertex();
		
		buffer.pos(0, 0, 1).tex(minU, maxV).endVertex();
		buffer.pos(0, 1, 1).tex(minU, minV).endVertex();
		buffer.pos(0, 1, 0).tex(maxU, minV).endVertex();
		buffer.pos(0, 0, 0).tex(maxU, maxV).endVertex();
		tessellator.draw();
		
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
	}

}
