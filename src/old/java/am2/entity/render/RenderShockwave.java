package am2.entity.render;

import am2.entity.EntityShockwave;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShockwave extends RenderEntity {


    public RenderShockwave(RenderManager p_i46185_1_) {
        super(p_i46185_1_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }

    @Override
    public void doRender(Entity p_doRender_1_, double p_doRender_2_, double p_doRender_4_, double p_doRender_4_2, float p_doRender_6_, float p_doRender_6_2) {
        //super.doRender(p_doRender_1_, p_doRender_2_, p_doRender_4_, p_doRender_4_2, p_doRender_6_, p_doRender_6_2);
    }
}
