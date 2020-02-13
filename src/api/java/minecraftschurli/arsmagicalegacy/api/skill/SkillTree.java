package minecraftschurli.arsmagicalegacy.api.skill;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public class SkillTree extends ForgeRegistryEntry<SkillTree> {
    private boolean canRender = true;
    private String unlock = null;
    private final int occulusIndex;

    public SkillTree(int occulusIndex) {
        this.occulusIndex = occulusIndex;
    }

    public ResourceLocation getBackground() {
        return new ResourceLocation(getRegistryName().getNamespace(), "textures/gui/occulus/" + getRegistryName().getPath() + ".png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(getRegistryName().getNamespace(), "textures/icon/" + getRegistryName().getPath() + ".png");
    }

    public String getTranslationKey() {
        return Util.makeTranslationKey("skilltree", ArsMagicaAPI.getSkillTreeRegistry().getKey(this));
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(getTranslationKey());
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

    public int getOcculusIndex() {
        return occulusIndex;
    }
}
