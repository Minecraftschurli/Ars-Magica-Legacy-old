package am2.entity.render;

import am2.entity.EntityAirSled;
import am2.models.ModelAirGuardianHoverball;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAirSled extends RenderLiving<EntityAirSled>{

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/bosses/air_guardian.png");

	public RenderAirSled(RenderManager manager){
		super(manager, new ModelAirGuardianHoverball(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAirSled entity){
		return rLoc;
	}

}
