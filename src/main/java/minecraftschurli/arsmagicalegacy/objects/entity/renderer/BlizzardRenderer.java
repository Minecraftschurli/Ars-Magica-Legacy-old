package minecraftschurli.arsmagicalegacy.objects.entity.renderer;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.objects.entity.BlizzardEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
public class BlizzardRenderer extends EntityRenderer<BlizzardEntity> {
    protected BlizzardRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(BlizzardEntity entity) {
        return null;
    }
}
