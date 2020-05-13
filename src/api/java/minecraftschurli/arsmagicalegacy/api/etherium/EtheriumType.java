package minecraftschurli.arsmagicalegacy.api.etherium;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * @author Minecraftschurli
 * @version 2019-11-19
 */
public class EtheriumType extends ForgeRegistryEntry<EtheriumType> implements ITranslatable<EtheriumType> {
    private static final String TYPE = "etherium";
    public static final RegistryObject<EtheriumType> DARK = RegistryObject.of(new ResourceLocation(ArsMagicaAPI.MODID,"dark"), () -> EtheriumType.class);
    public static final RegistryObject<EtheriumType> LIGHT = RegistryObject.of(new ResourceLocation(ArsMagicaAPI.MODID, "light"), () -> EtheriumType.class);
    public static final RegistryObject<EtheriumType> NEUTRAL = RegistryObject.of(new ResourceLocation(ArsMagicaAPI.MODID, "neutral"), () -> EtheriumType.class);
    private final int color;

    public EtheriumType(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "EtheriumType("+ getRegistryName() +")";
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
