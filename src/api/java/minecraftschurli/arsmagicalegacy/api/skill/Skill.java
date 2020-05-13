package minecraftschurli.arsmagicalegacy.api.skill;

import java.util.ArrayList;
import java.util.List;
import minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-27
 */
public final class Skill extends ForgeRegistryEntry<Skill> implements ITranslatable.WithDescription<Skill> {
    private final int posX, posY;
    private final SkillTree tree;
    private final String[] parents;
    private final ResourceLocation icon;
    private final SkillPoint point;

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

    @Override
    public String getType() {
        return "skill";
    }

    public List<ITextComponent> getTooltip() {
        List<ITextComponent> list = new ArrayList<>();
        list.add(getDisplayName());
        return list;
    }
}
