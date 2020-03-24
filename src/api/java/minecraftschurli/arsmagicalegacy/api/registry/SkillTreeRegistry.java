package minecraftschurli.arsmagicalegacy.api.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillTreeRegistry {
    private static final Map<RegistryObject<SkillTree>, Supplier<? extends SkillTree>> entries = new LinkedHashMap<>();

    /**
     * Registers a new {@link SkillTree} with the specified occulusIndex under the given name and modid
     *
     * @param modid        : the modid of the owning mod
     * @param name         : the name of the registering skill tree
     * @param occulusIndex : the index of the {@link SkillTree} in the occulus gui
     * @return a {@link RegistryObject<SkillTree>} for the registered {@link SkillTree}
     */
    public static RegistryObject<SkillTree> registerSkillTree(String modid, String name, int occulusIndex) {
        return registerSkillTree(modid, name, () -> new SkillTree(occulusIndex));
    }

    /**
     * Registers a supplied {@link SkillTree} under the given name and modid
     *
     * @param modid : the modid of the owning mod
     * @param name  : the name of the registering {@link SkillTree}
     * @param tree  : a {@link Supplier} of a {@link SkillTree}
     * @return a {@link RegistryObject<SkillTree>} for the registered {@link SkillTree}
     */
    public static RegistryObject<SkillTree> registerSkillTree(String modid, String name, Supplier<? extends SkillTree> tree) {
        return registerSkillTree(new ResourceLocation(modid, name), tree);
    }

    /**
     * Registers a supplied {@link SkillTree} under the given {@link ResourceLocation}
     *
     * @param key  : the {@link ResourceLocation} to register with
     * @param tree  : a {@link Supplier} of a {@link SkillTree}
     * @return a {@link RegistryObject<SkillTree>} for the registered {@link SkillTree}
     */
    public static RegistryObject<SkillTree> registerSkillTree(ResourceLocation key, Supplier<? extends SkillTree> tree) {
        Objects.requireNonNull(key);
        RegistryObject<SkillTree> ret = RegistryObject.of(key, RegistryHandler.getSkillTreeRegistry());
        if (entries.putIfAbsent(ret, () -> tree.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + key.toString());
        }
        return ret;
    }

    static void onSkillTreeRegister(RegistryEvent.Register<?> event)
    {
        if (event.getGenericType() == RegistryHandler.getSkillTreeRegistry().getRegistrySuperType()) {
            @SuppressWarnings("unchecked")
            IForgeRegistry<SkillTree> reg = (IForgeRegistry<SkillTree>)event.getRegistry();
            for (Map.Entry<RegistryObject<SkillTree>, Supplier<? extends SkillTree>> e : entries.entrySet()) {
                reg.register(e.getValue().get());
                e.getKey().updateReference(reg);
            }
        }
    }
}
