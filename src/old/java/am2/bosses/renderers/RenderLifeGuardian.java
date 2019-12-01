package am2.bosses.renderers;

import am2.bosses.EntityLifeGuardian;
import am2.bosses.models.ModelLifeGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLifeGuardian extends RenderBoss<EntityLifeGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/life_guardian.png");

	public RenderLifeGuardian(RenderManager manager){
		super(manager, new ModelLifeGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLifeGuardian entity){
		return rLoc;
	}

}
