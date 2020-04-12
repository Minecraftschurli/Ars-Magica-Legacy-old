package minecraftschurli.arsmagicalegacy.objects.entity.renderer;

import minecraftschurli.arsmagicalegacy.objects.entity.FireRainEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
public class FireRainRenderer extends EntityRenderer<FireRainEntity> {
    protected FireRainRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(FireRainEntity entity) {
        return null;
    }
}
