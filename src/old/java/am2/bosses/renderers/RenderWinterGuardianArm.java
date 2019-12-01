package am2.bosses.renderers;

import am2.bosses.models.ModelWinterGuardianArm;
import am2.entity.EntityWinterGuardianArm;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWinterGuardianArm extends RenderLiving<EntityWinterGuardianArm>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/ice_guardian.png");

	public RenderWinterGuardianArm(RenderManager manager){
		super(manager, new ModelWinterGuardianArm(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWinterGuardianArm entity){
		return rLoc;
	}

}
