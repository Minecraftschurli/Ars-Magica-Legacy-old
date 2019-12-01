package am2.entity.render;

import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class RenderManaCreeper extends RenderCreeper{

	public RenderManaCreeper(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/mana_creeper.png");

	@Override
	protected ResourceLocation getEntityTexture(EntityCreeper entity){
		return rLoc;
	}

}
