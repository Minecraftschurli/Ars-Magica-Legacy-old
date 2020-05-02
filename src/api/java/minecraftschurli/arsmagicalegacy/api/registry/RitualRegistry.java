package minecraftschurli.arsmagicalegacy.api.registry;

import minecraftschurli.arsmagicalegacy.api.rituals.AbstractRitual;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import vazkii.patchouli.api.IMultiblock;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/** todo javadoc
 * @author Minecraftschurli
 * @version 2020-05-02
 */
@SuppressWarnings("unchecked")
public class RitualRegistry {
    private static final Map<RegistryObject<AbstractRitual>, Supplier<? extends AbstractRitual>> rituals = new LinkedHashMap<>();

    static void onRitualRegister(RegistryEvent.Register<AbstractRitual> event) {
		if (event.getGenericType() != AbstractRitual.class) {
			return;
		}
        IForgeRegistry<AbstractRitual> reg = event.getRegistry();
        for (Map.Entry<RegistryObject<AbstractRitual>, Supplier<? extends AbstractRitual>> e : rituals.entrySet()) {
            reg.register(e.getValue().get());
            e.getKey().updateReference(reg);
        }
    }

    public static <T extends AbstractRitual> RegistryObject<T> registerRitual(ResourceLocation id, Supplier<T> ritual) {
        RegistryObject<T> ret = RegistryObject.of(id, RegistryHandler.getRitualRegistry());
        if (rituals.putIfAbsent((RegistryObject<AbstractRitual>) ret, () -> ritual.get().setRegistryName(id)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + id);
        }
        return ret;
    }

    public static <T extends AbstractRitual> RegistryObject<T> registerRitual(String name, Supplier<T> ritual) {
        return registerRitual(new ResourceLocation(ModLoadingContext.get().getActiveNamespace(), name), ritual);
    }

    public static RegistryObject<DefaultRitual> registerRitual(String name, List<ItemStack> reagents, int radius, IMultiblock multiblock, ItemStack result) {
        return registerRitual(new ResourceLocation(ModLoadingContext.get().getActiveNamespace(), name), () -> new DefaultRitual(reagents, radius, multiblock, result));
    }

    /*public static RegistryObject<DefaultRitual> registerRitual(String name, List<ItemStack> reagents, IMultiblock multiblock, ItemStack result) {
        return registerRitual(new ResourceLocation(ModLoadingContext.get().getActiveNamespace(), name), () -> new DefaultRitual(reagents, Math.max(multiblock.getSize().getX(), multiblock.getSize().getZ()), multiblock, result));
    }*/

    public static class DefaultRitual extends AbstractRitual {
        private final List<ItemStack> reagents;
        private final int radius;
        private final IMultiblock multiblock;
        private final ItemStack reasult;

        private DefaultRitual(List<ItemStack> reagents, int radius, IMultiblock multiblock, ItemStack reasult) {
            this.reagents = reagents;
            this.radius = radius;
            this.multiblock = multiblock;
            this.reasult = reasult;
        }

        @Override
        public List<ItemStack> getReagents() {
            return this.reagents;
        }

        @Override
        public int getReagentSearchRadius() {
            return this.radius;
        }

        @Override
        public IMultiblock getRitualShape() {
            return this.multiblock;
        }

        @Override
        public ItemStack getResult() {
            return this.reasult;
        }
    }
}
