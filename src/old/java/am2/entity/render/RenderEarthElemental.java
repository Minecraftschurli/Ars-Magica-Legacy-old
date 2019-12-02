package am2.entity.render;

import am2.entity.EntityEarthElemental;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEarthElemental extends RenderBiped<EntityEarthElemental>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/earth_golem.png");

	public RenderEarthElemental(RenderManager renderManager){
		super(renderManager, new ModelZombie(), 0.5f);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityEarthElemental par1Entity){
		return rLoc;
	}

}
