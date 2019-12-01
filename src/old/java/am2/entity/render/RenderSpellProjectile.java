package am2.entity.render;

import org.lwjgl.opengl.GL11;

import am2.entity.EntitySpellProjectile;
import am2.particles.AMParticleIcons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderSpellProjectile extends Render<EntitySpellProjectile>{

	public RenderSpellProjectile(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntitySpellProjectile entity, double d0, double d1, double d2, float f, float f1){
		doRenderSpellProjectile((EntitySpellProjectile)entity, d0, d1, d2, f, f1);
	}

	private void doRenderSpellProjectile(EntitySpellProjectile entity, double d, double d1, double d2, float f, float f1){
		TextureAtlasSprite sprite = AMParticleIcons.instance.getIconByName(entity.getIcon());
		if (sprite == null){
			return;
		}

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(32826); /* RESCALE_NORMAL_EXT */
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
		GL11.glDepthMask(false);
		//RenderHelper.disableStandardItemLighting();

		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		int renderColor = entity.getColor();

		GL11.glTranslated(d, d1, d2);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		renderIcon(sprite, renderColor);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	private void renderIcon(TextureAtlasSprite sprite, int renderColor){
		Tessellator tessellator = Tessellator.getInstance();
		float f = 1.0F;
		float f1 = 0.5F;
		float f2 = 0.25F;

		tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		RenderHelper.disableStandardItemLighting();
		tessellator.getBuffer().pos(0.0F - f1, 0.0F - f2, 0.0D).tex( sprite.getMinU(), sprite.getMaxV()).color((renderColor & 0xFF0000) >> 16, (renderColor & 0x00FF00) >> 8, renderColor & 0x0000FF, 255).endVertex();
		tessellator.getBuffer().pos(f - f1, 0.0F - f2, 0.0D).tex( sprite.getMaxU(), sprite.getMaxV()).color((renderColor & 0xFF0000) >> 16, (renderColor & 0x00FF00) >> 8, renderColor & 0x0000FF, 255).endVertex();
		tessellator.getBuffer().pos(f - f1, f - f2, 0.0D).tex( sprite.getMaxU(), sprite.getMinV()).color((renderColor & 0xFF0000) >> 16, (renderColor & 0x00FF00) >> 8, renderColor & 0x0000FF, 255).endVertex();
		tessellator.getBuffer().pos(0.0F - f1, f - f2, 0.0D).tex( sprite.getMinU(), sprite.getMinV()).color((renderColor & 0xFF0000) >> 16, (renderColor & 0x00FF00) >> 8, renderColor & 0x0000FF, 255).endVertex();
		tessellator.draw();
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpellProjectile entity){
		return null;
	}

}
