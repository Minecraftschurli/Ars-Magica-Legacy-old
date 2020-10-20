package minecraftschurli.arsmagicalegacy.objects.entity.renderer;

import javax.annotation.Nonnull;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

public class ManaCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("arsmagicalegacy:textures/entity/mana_creeper.png");
    public ManaCreeperRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull CreeperEntity entity) {
        return CREEPER_TEXTURES;
    }
}
