package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillRegistry {
    private static final Map<RegistryObject<Skill>, Supplier<? extends Skill>> skills = new LinkedHashMap<>();

    static void onSkillRegister(RegistryEvent.Register<Skill> event) {
        if (event.getGenericType() != Skill.class)
            return;
        IForgeRegistry<Skill> reg = event.getRegistry();
        for (Map.Entry<RegistryObject<Skill>, Supplier<? extends Skill>> e : skills.entrySet()) {
            reg.register(e.getValue().get());
            e.getKey().updateReference(reg);
        }
    }

    /**
     * @param id      the id of the new {@link Skill}
     * @param icon    the {@link ResourceLocation} used for the icon in the gui of the occulus
     * @param tier    the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    the x position of this {@link Skill} in the gui of the occulus
     * @param posY    the y position of this {@link Skill} in the gui of the occulus
     * @param parents the ids of the parents of this {@link Skill}
     */
    public static RegistryObject<Skill> registerSkill(ResourceLocation id, ResourceLocation icon, Supplier<SkillPoint> tier, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        final Supplier<SkillPoint> _tier = tier == null ? () -> null : tier;
        final Supplier<SkillTree> _tree = tree == null ? () -> null : tree;
        RegistryObject<Skill> ret = RegistryObject.of(id, ArsMagicaAPI.getSkillRegistry());
        if (skills.putIfAbsent(ret, () -> new Skill(icon, _tier.get(), posX, posY, _tree.get(), parents).setRegistryName(id)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + id);
        }
        return RegistryObject.of(id, ArsMagicaAPI.getSkillRegistry());
    }

    /**
     * @param modid   the id of the mod for the new {@link Skill}
     * @param name    the name for the new {@link Skill}
     * @param tier    the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    the x position of this {@link Skill} in the gui of the occulus
     * @param posY    the y position of this {@link Skill} in the gui of the occulus
     * @param parents the ids of the parents of this {@link Skill}
     */
    public static RegistryObject<Skill> registerSkill(String modid, String name, Supplier<SkillPoint> tier, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        ResourceLocation id = new ResourceLocation(modid, name);
        return registerSkill(id, getSkillIcon(id), tier, tree, posX, posY, parents);
    }

    /**
     * @param name    the name for the new {@link Skill}
     * @param tier    the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    the x position of this {@link Skill} in the gui of the occulus
     * @param posY    the y position of this {@link Skill} in the gui of the occulus
     * @param parents the ids of the parents of this {@link Skill}
     */
    public static RegistryObject<Skill> registerSkill(String name, Supplier<SkillPoint> tier, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSkill(ModLoadingContext.get().getActiveNamespace(), name, tier, tree, posX, posY, parents);
    }

    private static ResourceLocation getSkillIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/skill/" + id.getPath() + ".png");
    }

    public static List<Skill> getSkillsForTree(SkillTree tree) {
        return ArsMagicaAPI.getSkillRegistry().getValues().stream().filter(skill -> skill.getTree() == tree).collect(Collectors.toList());
    }
}
