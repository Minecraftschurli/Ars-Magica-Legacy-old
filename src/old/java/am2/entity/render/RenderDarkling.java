package am2.entity.render;

import am2.entity.EntityDarkling;
import am2.models.ModelDarkling;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDarkling extends RenderLiving<EntityDarkling>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/darkling.png");

	public RenderDarkling(RenderManager manager){
		super(manager, new ModelDarkling(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDarkling par1Entity){
		return rLoc;
	}
}
