package am2.bosses.renderers;

import am2.bosses.EntityFireGuardian;
import am2.bosses.models.ModelFireGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFireGuardian extends RenderBoss<EntityFireGuardian>{
	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/fire_guardian.png");

	public RenderFireGuardian(RenderManager manager){
		super(manager, new ModelFireGuardian());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFireGuardian entity){
		return rLoc;
	}
}
