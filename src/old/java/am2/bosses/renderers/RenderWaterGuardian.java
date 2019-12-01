package am2.bosses.renderers;

import am2.bosses.EntityWaterGuardian;
import am2.bosses.models.ModelWaterGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWaterGuardian extends RenderBoss<EntityWaterGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/water_guardian.png");

	public RenderWaterGuardian(RenderManager manager){
		super(manager, new ModelWaterGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWaterGuardian entity){
		return rLoc;
	}
}
