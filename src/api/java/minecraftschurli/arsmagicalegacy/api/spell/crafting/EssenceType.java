package minecraftschurli.arsmagicalegacy.api.spell.crafting;


import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EssenceType extends ForgeRegistryEntry<EssenceType> {
    public static final EssenceType DARK = new EssenceType(0xffffff);
    public static final EssenceType LIGHT = new EssenceType(0xffffff);
    public static final EssenceType NEUTRAL = new EssenceType(0xffffff);

    private final int color;

    public EssenceType(int color) {
        this.color = color;
    }

    public String getTranslationKey() {
        return Util.makeTranslationKey("essence_type", getRegistryName());
    }

    public int getColor() {
        return this.color;
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(getTranslationKey());
    }

    @Override
    public String toString() {
        return "EssenceType("+getRegistryName().toString()+")";
    }
}
