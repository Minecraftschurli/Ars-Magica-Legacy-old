package am2.bosses.renderers;

import am2.bosses.EntityLightningGuardian;
import am2.bosses.models.ModelLightningGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderLightningGuardian extends RenderBoss<EntityLightningGuardian>{

	private static final ResourceLocation lightning = new ResourceLocation("arsmagica2", "textures/mobs/bosses/lightning_guardian_lt.png");
	private static final ResourceLocation armor = new ResourceLocation("arsmagica2", "textures/mobs/bosses/lightning_guardian.png");

	public RenderLightningGuardian(RenderManager manager){
		super(manager, new ModelLightningGuardian(armor, lightning));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLightningGuardian entity){
		return lightning;
	}

}
