package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;

import java.util.*;
import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillTreeRegistry {
    private static final Map<RegistryObject<SkillTree>, Supplier<? extends SkillTree>> entries = new LinkedHashMap<>();

    public static RegistryObject<SkillTree> registerSkillTree(String modid, String name, int occulusIndex) {
        return registerSkillTree(modid, name, () -> new SkillTree(occulusIndex));
    }

    public static RegistryObject<SkillTree> registerSkillTree(String modid, String name, Supplier<? extends SkillTree> tree) {
        return registerSkillTree(new ResourceLocation(modid, name), tree);
    }

    public static RegistryObject<SkillTree> registerSkillTree(ResourceLocation key, Supplier<? extends SkillTree> tree) {
        Objects.requireNonNull(key);
        RegistryObject<SkillTree> ret = RegistryObject.of(key, ArsMagicaAPI.getSkillTreeRegistry());
        if (entries.putIfAbsent(ret, () -> tree.get().setRegistryName(key)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + key.toString());
        }
        return ret;
    }

    static void onSkillTreeRegister(RegistryEvent.Register<?> event)
    {
        if (event.getGenericType() == ArsMagicaAPI.getSkillTreeRegistry().getRegistrySuperType()) {
            @SuppressWarnings("unchecked")
            IForgeRegistry<SkillTree> reg = (IForgeRegistry<SkillTree>)event.getRegistry();
            for (Map.Entry<RegistryObject<SkillTree>, Supplier<? extends SkillTree>> e : entries.entrySet()) {
                reg.register(e.getValue().get());
                e.getKey().updateReference(reg);
            }
        }
    }
}
