package minecraftschurli.arsmagicalegacy.api.spell.skill;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillTree extends ForgeRegistryEntry<SkillTree> {
    private String name;
    private ResourceLocation background;
    private ResourceLocation icon;
    private boolean canRender = true;
    private String unlock = null;

    public SkillTree() {
        this.background = null;
        this.icon = null;
    }

    public SkillTree(ResourceLocation background, ResourceLocation icon) {
        this.background = background;
        this.icon = icon;
    }

    public ResourceLocation getBackground() {
        if (background != null) return background;
        return new ResourceLocation(getRegistryName().getNamespace(), "textures/gui/occulus/"+getRegistryName().getPath()+".png");
    }

    public ResourceLocation getIcon() {
        if (icon != null) return icon;
        return new ResourceLocation(getRegistryName().getNamespace(), "textures/icon/"+getRegistryName().getPath()+".png");
    }

    public String getUnlocalizedName() {
        return "skilltree." + getRegistryName().getPath();
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
