package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillRegistry {
    private static final List<Supplier<Skill>> SKILLS = new ArrayList<>();

    @SubscribeEvent
    public static void onSkillRegister(RegistryEvent.Register<Skill> event) {
        event.getRegistry().registerAll(SKILLS.stream().map(Supplier::get).toArray(Skill[]::new));
    }

    /**
     *
     * @param id      the id of the new {@link Skill}
     * @param icon    the {@link ResourceLocation} used for the icon in the gui of the occulus
     * @param tier    the {@link SkillPoint} used to learn this {@link Skill}
     * @param tree    the {@link SkillTree} to display this skill on in the gui of the occulus
     * @param posX    the x position of this {@link Skill} in the gui of the occulus
     * @param posY    the y position of this {@link Skill} in the gui of the occulus
     * @param parents the ids of the parents of this {@link Skill}
     */
    public static void registerSkill(ResourceLocation id, ResourceLocation icon, SkillPoint tier, SkillTree tree, int posX, int posY, String... parents) {
        SKILLS.add(() -> new Skill(icon, tier, posX, posY, tree, parents).setRegistryName(id));
    }

    public static List<Skill> getSkillsForTree(SkillTree tree) {
        return ArsMagicaLegacyAPI.SKILL_REGISTRY.getValues().stream().filter(skill -> skill.getTree() == tree).collect(Collectors.toList());
    }
}
