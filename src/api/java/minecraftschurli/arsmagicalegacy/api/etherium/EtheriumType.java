package minecraftschurli.arsmagicalegacy.api.etherium;


import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.EtheriumRegistry;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EtheriumType extends ForgeRegistryEntry<EtheriumType> {
    public static final RegistryObject<EtheriumType> DARK = EtheriumRegistry.registerEtherium(ArsMagicaAPI.MODID, "dark", 0xffffff);
    public static final RegistryObject<EtheriumType> LIGHT = EtheriumRegistry.registerEtherium(ArsMagicaAPI.MODID, "light", 0xffffff);
    public static final RegistryObject<EtheriumType> NEUTRAL = EtheriumRegistry.registerEtherium(ArsMagicaAPI.MODID, "neutral", 0xffffff);

    private final int color;

    public EtheriumType(int color) {
        this.color = color;
    }

    public String getTranslationKey() {
        return Util.makeTranslationKey("etherium", getRegistryName());
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
