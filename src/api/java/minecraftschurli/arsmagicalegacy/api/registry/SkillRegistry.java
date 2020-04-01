package minecraftschurli.arsmagicalegacy.api.registry;

import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
     * Registers a new {@link Skill} with the given attributes
     *
     * @param id      : the id of the new {@link Skill}
     * @param icon    : the {@link ResourceLocation} used for the icon in the gui of the occulus
     * @param tier    : the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    : the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    : the x position of this {@link Skill} in the gui of the occulus
     * @param posY    : the y position of this {@link Skill} in the gui of the occulus
     * @param parents : the ids of the parents of this {@link Skill}
     */
    public static RegistryObject<Skill> registerSkill(ResourceLocation id, ResourceLocation icon, Supplier<SkillPoint> tier, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        final Supplier<SkillPoint> _tier = tier == null ? () -> null : tier;
        final Supplier<SkillTree> _tree = tree == null ? () -> null : tree;
        RegistryObject<Skill> ret = RegistryObject.of(id, RegistryHandler.getSkillRegistry());
        if (skills.putIfAbsent(ret, () -> new Skill(icon, _tier.get(), posX, posY, _tree.get(), parents).setRegistryName(id)) != null) {
            throw new IllegalArgumentException("Duplicate registration " + id);
        }
        return RegistryObject.of(id, RegistryHandler.getSkillRegistry());
    }

    /**
     * Registers a new {@link Skill} with the given attributes
     *
     * @param modid   : the id of the mod for the new {@link Skill}
     * @param name    : the name for the new {@link Skill}
     * @param tier    : the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    : the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    : the x position of this {@link Skill} in the gui of the occulus
     * @param posY    : the y position of this {@link Skill} in the gui of the occulus
     * @param parents : the ids of the parents of this {@link Skill}
     */
    public static RegistryObject<Skill> registerSkill(String modid, String name, Supplier<SkillPoint> tier, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        ResourceLocation id = new ResourceLocation(modid, name);
        return registerSkill(id, getSkillIcon(id), tier, tree, posX, posY, parents);
    }

    /**
     * Registers a new {@link Skill} with the given attributes
     *
     * @param name    : the name for the new {@link Skill}
     * @param tier    : the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    : the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    : the x position of this {@link Skill} in the gui of the occulus
     * @param posY    : the y position of this {@link Skill} in the gui of the occulus
     * @param parents : the ids of the parents of this {@link Skill}
     */
    public static RegistryObject<Skill> registerSkill(String name, Supplier<SkillPoint> tier, Supplier<SkillTree> tree, int posX, int posY, String... parents) {
        return registerSkill(ModLoadingContext.get().getActiveNamespace(), name, tier, tree, posX, posY, parents);
    }

    private static ResourceLocation getSkillIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/skill/" + id.getPath() + ".png");
    }

    /**
     * Gets all {@link Skill's} for the given {@link SkillTree}
     *
     * @param tree : the skill tree to query
     * @return all {@link Skill's} in the given {@link SkillTree}
     */
    public static List<Skill> getSkillsForTree(SkillTree tree) {
        return RegistryHandler.getSkillRegistry().getValues().stream().filter(skill -> skill.getTree() == tree).collect(Collectors.toList());
    }
}
