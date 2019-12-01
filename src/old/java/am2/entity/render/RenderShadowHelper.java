package am2.entity.render;

import org.lwjgl.opengl.GL11;

import am2.entity.EntityShadowHelper;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderShadowHelper extends RenderBiped<EntityShadowHelper>{

	public RenderShadowHelper(RenderManager manager){
		super(manager, new ModelBiped(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShadowHelper par1Entity){
		return par1Entity.getLocationSkin();
	}

	@Override
	protected void renderModel(EntityShadowHelper par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7){
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1, 1, 1, 0.35f);
		super.renderModel(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
		GL11.glPopAttrib();
	}
}
