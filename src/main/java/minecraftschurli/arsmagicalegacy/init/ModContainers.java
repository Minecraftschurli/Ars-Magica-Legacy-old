package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.occulus.*;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.*;
import net.minecraft.inventory.container.*;
import net.minecraftforge.fml.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public final class ModContainers implements IInit {

    public static final RegistryObject<ContainerType<SpellBookContainer>> SPELLBOOK = CONTAINERS.register("spellbook", () -> new ContainerType<>(SpellBookContainer::new));
    public static final RegistryObject<ContainerType<OcculusContainer>> OCCULUS = CONTAINERS.register("occulus", () -> new ContainerType<>(OcculusContainer::new));

    public static void register() {
    }
}
