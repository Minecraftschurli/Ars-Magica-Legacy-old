package minecraftschurli.arsmagicalegacy.init;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public final class ModVillagers implements Registries {
    public static final RegistryObject<PointOfInterestType> ALTAR_CORE = POI_TYPES.register("altar_core", () -> new PointOfInterestType("altar_core", ImmutableSet.copyOf(ModBlocks.ALTAR_CORE.get().getStateContainer().getValidStates()), 1, null, 1));
    public static final RegistryObject<VillagerProfession> MAGE = PROFESSIONS.register("mage", () -> new VillagerProfession("mage", ModVillagers.ALTAR_CORE.get(), ImmutableSet.of(), ImmutableSet.of()));
}
