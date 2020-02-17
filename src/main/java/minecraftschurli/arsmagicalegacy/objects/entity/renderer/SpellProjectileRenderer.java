package minecraftschurli.arsmagicalegacy.objects.entity.renderer;

import minecraftschurli.arsmagicalegacy.objects.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
public class SpellProjectileRenderer extends EntityRenderer<SpellProjectileEntity> {
    protected SpellProjectileRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getEntityTexture(SpellProjectileEntity entity) {
        return null;
    }
}
