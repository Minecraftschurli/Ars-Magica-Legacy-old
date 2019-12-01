package am2.entity.render;

import am2.entity.EntityBroom;
import am2.entity.models.ModelBroom;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBroom extends RenderLiving<EntityBroom> {

    private static final ResourceLocation rLoc = new ResourceLocation("arsmagica2", "textures/mobs/broom.png");
    private ModelBroom model;

    public RenderBroom(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBroom(), 0.5f);
        model = (ModelBroom)mainModel;
    }

    @Override
    public void doRender(EntityBroom entity, double x, double y, double z, float entityYaw, float partialTicks) {

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBroom entity) {
        return rLoc;
    }
}
