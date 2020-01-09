package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.*;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.*;
import net.minecraft.inventory.container.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.network.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public final class ModContainers implements IInit {
    public static final RegistryObject<ContainerType<SpellBookContainer>> SPELLBOOK = CONTAINERS.register("spellbook", () -> new ContainerType<>(SpellBookContainer::new));
    public static final RegistryObject<ContainerType<InscriptionTableContainer>> INSCRIPTION_TABLE = CONTAINERS.register("inscription_table", () -> new ContainerType<>((IContainerFactory<InscriptionTableContainer>) InscriptionTableContainer::new));
    public static void register() {}
}
