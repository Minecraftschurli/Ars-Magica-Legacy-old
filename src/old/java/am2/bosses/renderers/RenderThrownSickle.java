package am2.bosses.renderers;

import am2.bosses.models.ModelPlantGuardianSickle;
import am2.entity.EntityThrownSickle;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderThrownSickle extends RenderLiving<EntityThrownSickle>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/plant_guardian.png");

	public RenderThrownSickle(RenderManager manager){
		super(manager, new ModelPlantGuardianSickle(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityThrownSickle entity){
		return rLoc;
	}

}
