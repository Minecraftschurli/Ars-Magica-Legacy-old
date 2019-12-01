package am2.bosses.renderers;

import am2.bosses.EntityEarthGuardian;
import am2.bosses.models.ModelEarthGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEarthGuardian extends RenderBoss<EntityEarthGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/earth_guardian.png");

	public RenderEarthGuardian(RenderManager manager){
		super(manager, new ModelEarthGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEarthGuardian entity){
		return rLoc;
	}
}
