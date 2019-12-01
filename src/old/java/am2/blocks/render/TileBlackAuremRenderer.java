package am2.blocks.render;

import org.lwjgl.opengl.GL11;

import am2.ArsMagica2;
import am2.blocks.tileentity.TileEntityBlackAurem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class TileBlackAuremRenderer extends TileEntitySpecialRenderer<TileEntityBlackAurem> {
	
	@Override
	public void renderTileEntityAt(TileEntityBlackAurem te, double x, double y, double z, float partialTicks, int destroyStage) {
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1, z + 0.5);
		GlStateManager.enableBlend();
		renderArsMagicaEffect(Tessellator.getInstance(), 1, 1);
		
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
	}
	
	private void renderArsMagicaEffect(Tessellator tessellator, float offset, float scale){
		if (offset != 0){
			GL11.glRotatef(180 - Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		}else{
			GL11.glRotatef(35, 0, 1, 0);
			GL11.glTranslatef(0, -0.75f, 0);
		}

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("arsmagica2:textures/blocks/custom/black_aurem.png"));
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef(0.0f, 0.25f, 0.0f);
		GL11.glRotatef(ArsMagica2.proxy.getLocalPlayer().ticksExisted, 0, 0, 1);
		GL11.glScalef(scale * 2, scale * 2, scale * 2);
		GL11.glTranslatef(0.0f, -0.25f, 0.0f);
		renderSprite(tessellator);
	}
	
	private void renderSprite(Tessellator tessellator){

		float TLX = 0;
		float BRX = 1;
		float TLY = 0;
		float BRY = 1;

		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;

		try{
			tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
			tessellator.getBuffer().pos(0.0F - f5, 0.0F - f6, 0.0D).tex(TLX, BRY).endVertex();
			tessellator.getBuffer().pos(f4 - f5, 0.0F - f6, 0.0D).tex(BRX, BRY).endVertex();
			tessellator.getBuffer().pos(f4 - f5, f4 - f6, 0.0D).tex(BRX, TLY).endVertex();
			tessellator.getBuffer().pos(0.0F - f5, f4 - f6, 0.0D).tex(TLX, TLY).endVertex();;
			tessellator.draw();
		}catch (Throwable t){
		}
	}
}
