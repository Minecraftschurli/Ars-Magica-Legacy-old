package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.advancements.ArsMagicaCriteriaTriggers;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.IngredientTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Minecraftschurli
 * @version 2019-12-03
 */
public class ArsMagicaAPI {
    public static final String MODID = "arsmagicalegacy";
    public static final String MODNAME = "Ars Magica: Legacy";

    public static final Logger LOGGER = LogManager.getLogger(MODID+"-api");

    public static void setup() {
        RegistryHandler.setup();
        CapabilityHelper.setup();
    }

    public static void init() {
        IngredientTypes.registerDefault();
        ArsMagicaCriteriaTriggers.registerDefaults();
    }

    public static PlayerEntity getLocalPlayer() {
        return DistExecutor.callWhenOn(Dist.CLIENT, () -> () -> net.minecraft.client.Minecraft.getInstance().player);
    }

}
