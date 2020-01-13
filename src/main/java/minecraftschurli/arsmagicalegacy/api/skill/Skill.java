package minecraftschurli.arsmagicalegacy.api.skill;

import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraftforge.registries.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public final class Skill extends ForgeRegistryEntry<Skill> {
    private int posX, posY;
    private SkillTree tree;
    private String[] parents;
    private ResourceLocation icon;
    private SkillPoint point;

    public Skill(ResourceLocation icon, SkillPoint point, int posX, int posY, SkillTree tree, String... parents) {
        this.posX = posX;
        this.posY = posY;
        this.tree = tree;
        this.parents = parents;
        this.icon = icon;
        this.point = point;
    }

    public String getID() {
        return getRegistryName().toString();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public SkillTree getTree() {
        return tree;
    }

    public String[] getParents() {
        return parents;
    }

    public void writeToNBT(CompoundNBT tag) {
        tag.putString("ID", getID());
    }

    public SkillPoint getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return getID();
    }

    public String getTranslationKey() {
        return "skill." + getRegistryName().getNamespace() + "." + getRegistryName().getPath();
    }

    public ITextComponent getName() {
        return new TranslationTextComponent(getTranslationKey() + ".name");
    }

    public ITextComponent getOcculusDesc() {
        return new TranslationTextComponent(getTranslationKey() + ".occulusdesc");
    }
}
