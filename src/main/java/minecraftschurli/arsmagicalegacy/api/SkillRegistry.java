package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillRegistry {
    private static final List<Supplier<Skill>> SKILLS = new ArrayList<>();

    static void onSkillRegister(RegistryEvent.Register<Skill> event) {
        if (event.getGenericType() != Skill.class)
            return;
        event.getRegistry().registerAll(SKILLS.stream().map(Supplier::get).toArray(Skill[]::new));
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
    public static RegistryObject<Skill> registerSkill(ResourceLocation id, ResourceLocation icon, SkillPoint tier, SkillTree tree, int posX, int posY, String... parents) {
        SKILLS.add(() -> new Skill(icon, tier, posX, posY, tree, parents).setRegistryName(id));
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
    public static RegistryObject<Skill> registerSkill(String modid, String name, SkillPoint tier, SkillTree tree, int posX, int posY, String... parents) {
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
    public static RegistryObject<Skill> registerSkill(String name, SkillPoint tier, SkillTree tree, int posX, int posY, String... parents) {
        return registerSkill(ModLoadingContext.get().getActiveNamespace(), name, tier, tree, posX, posY, parents);
    }

    private static ResourceLocation getSkillIcon(ResourceLocation id) {
        return new ResourceLocation(id.getNamespace(), "textures/icon/skill/" + id.getPath() + ".png");
    }

    public static List<Skill> getSkillsForTree(SkillTree tree) {
        return ArsMagicaAPI.getSkillRegistry().getValues().stream().filter(skill -> skill.getTree() == tree).collect(Collectors.toList());
    }
}
