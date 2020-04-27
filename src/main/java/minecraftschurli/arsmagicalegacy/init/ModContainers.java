package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorContainer;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableContainer;
import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;

/**
 * @author Minecraftschurli
 * @version 2019-11-08
 */
public final class ModContainers implements IInit {
    public static final RegistryObject<ContainerType<SpellBookContainer>> SPELLBOOK = CONTAINERS.register("spellbook", () -> new ContainerType<>(SpellBookContainer::new));
    public static final RegistryObject<ContainerType<InscriptionTableContainer>> INSCRIPTION_TABLE = CONTAINERS.register("inscription_table", () -> new ContainerType<>((IContainerFactory<InscriptionTableContainer>) InscriptionTableContainer::new));
    public static final RegistryObject<ContainerType<EtheriumGeneratorContainer>> ETHERIUM_GENERATOR = CONTAINERS.register("ethzerium_generator", () -> new ContainerType<>((IContainerFactory<EtheriumGeneratorContainer>) EtheriumGeneratorContainer::new));

    public static void register() {
    }
}
