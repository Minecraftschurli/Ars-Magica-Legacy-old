package am2.bosses.renderers;

import am2.bosses.EntityAirGuardian;
import am2.bosses.models.ModelAirGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAirGuardian extends RenderBoss<EntityAirGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/air_guardian.png");

	public RenderAirGuardian(RenderManager manager){
		super(manager, new ModelAirGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAirGuardian entity){
		return rLoc;
	}
}
