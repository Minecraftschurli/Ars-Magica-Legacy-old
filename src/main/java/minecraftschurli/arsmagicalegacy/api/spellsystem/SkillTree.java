package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillTree {
    private String name;
    private ResourceLocation background;
    private ResourceLocation icon;
    private boolean canRender = true;
    private String unlock = null;

    public SkillTree(String name, ResourceLocation background, ResourceLocation icon) {
        this.name = name.toLowerCase();
        this.background = background;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getBackground() {
        return background;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public String getUnlocalizedName() {
        return "skilltree." + name;
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
