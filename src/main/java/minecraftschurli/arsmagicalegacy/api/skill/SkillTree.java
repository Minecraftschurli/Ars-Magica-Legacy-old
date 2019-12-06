package minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillTree {
    private final ResourceLocation id;
    private ResourceLocation background;
    private ResourceLocation icon;
    private boolean canRender = true;
    private String unlock = null;

    public SkillTree(ResourceLocation id) {
        this.background = null;
        this.icon = null;
        this.id = id;
    }

    public SkillTree(ResourceLocation id, ResourceLocation background, ResourceLocation icon) {
        this.background = background;
        this.icon = icon;
        this.id = id;
    }

    public ResourceLocation getBackground() {
        if (background != null) return background;
        return new ResourceLocation(id.getNamespace(), "textures/gui/occulus/" + id.getPath() + ".png");
    }

    public ResourceLocation getIcon() {
        if (icon != null) return icon;
        return new ResourceLocation(id.getNamespace(), "textures/icon/" + id.getPath() + ".png");
    }

    public String getUnlocalizedName() {
        return "skilltree." + id.getPath();
    }

    public String getLocalizedName() {
        return new TranslationTextComponent(getUnlocalizedName()).toString();
    }

    public SkillTree disableRender(String compendiumUnlock) {
        canRender = false;
        this.unlock = compendiumUnlock;
        return this;
    }

    public String getUnlock() {
        return unlock;
    }

    public boolean canRender() {
        return canRender;
    }
}
