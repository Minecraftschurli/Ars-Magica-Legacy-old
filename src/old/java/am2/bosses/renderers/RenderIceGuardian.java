package am2.bosses.renderers;

import am2.bosses.EntityWinterGuardian;
import am2.bosses.models.ModelWinterGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderIceGuardian extends RenderBoss<EntityWinterGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/ice_guardian.png");

	public RenderIceGuardian(RenderManager manager){
		super(manager, new ModelWinterGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWinterGuardian entity){
		return rLoc;
	}
}
