package am2.bosses.renderers;

import am2.bosses.EntityNatureGuardian;
import am2.bosses.models.ModelPlantGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPlantGuardian extends RenderBoss<EntityNatureGuardian>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/plant_guardian.png");

	public RenderPlantGuardian(RenderManager manager){
		super(manager, new ModelPlantGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNatureGuardian entity){
		return rLoc;
	}

}
