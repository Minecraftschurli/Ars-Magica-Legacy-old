package am2.bosses.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;

public class RenderItemNoBob extends RenderEntityItem{

	public RenderItemNoBob(RenderManager renderManagerIn) {
		super(renderManagerIn, Minecraft.getMinecraft().getRenderItem());
	}

	@Override
	public boolean shouldBob(){
		return false;
	}
}
