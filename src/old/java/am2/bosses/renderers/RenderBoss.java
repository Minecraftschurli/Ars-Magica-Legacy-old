package am2.bosses.renderers;

import am2.bosses.AM2Boss;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;

public abstract class RenderBoss<T extends AM2Boss> extends RenderLiving<T>{

	public RenderBoss(RenderManager manager, ModelBase model){
		super(manager, model, 0.5f);
	}

	@Override
	protected void renderModel(T par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7){
//		AM2Boss boss = (AM2Boss)par1EntityLivingBase;
//		if (boss.playerCanSee)
//			.setBossStatus((AM2Boss)par1EntityLivingBase, true);
		super.renderModel(par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
	}
}
