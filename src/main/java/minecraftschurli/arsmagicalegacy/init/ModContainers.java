package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.occulus.OcculusContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

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
