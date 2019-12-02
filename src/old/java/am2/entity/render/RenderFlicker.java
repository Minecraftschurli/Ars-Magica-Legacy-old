package am2.entity.render;

import am2.entity.EntityFlicker;
import am2.entity.models.ModelFlicker;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderFlicker extends RenderLiving<EntityFlicker>{
	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/flicker.png");

	public RenderFlicker(RenderManager manager){
		super(manager, new ModelFlicker(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlicker par1Entity){
		return rLoc;
	}

	@Override
	public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9){
	}
}
