package am2.entity.render;

import am2.entity.EntityFireElemental;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFireElemental extends RenderBiped<EntityFireElemental>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/fire_elemental.png");

	public RenderFireElemental(RenderManager renderManager){
		super(renderManager, new ModelBiped(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFireElemental par1Entity){
		return rLoc;
	}

}
