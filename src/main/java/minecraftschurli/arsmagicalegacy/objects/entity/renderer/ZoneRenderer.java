package minecraftschurli.arsmagicalegacy.objects.entity.renderer;

import minecraftschurli.arsmagicalegacy.objects.entity.ZoneEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
public class ZoneRenderer extends EntityRenderer<ZoneEntity> {
    protected ZoneRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(ZoneEntity entity) {
        return null;
    }
}
