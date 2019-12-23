package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.api.skill.*;
import net.minecraft.util.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class SkillTreeRegistry {
    public static final List<SkillTree> SKILL_TREE_REGISTRY = new ArrayList<>();

    public static SkillTree registerSkillTree(String modid, String name) {
        SkillTree tree = new SkillTree(new ResourceLocation(modid, name));
        SKILL_TREE_REGISTRY.add(tree);
        return tree;
    }
}
