package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.registry.SkillTreeRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.SkillTree;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2020-04-22
 */
public final class ModSkillTrees {
    public static final RegistryObject<SkillTree> OFFENSE = SkillTreeRegistry.registerSkillTree(ArsMagicaAPI.MODID, "offense", 0);
    public static final RegistryObject<SkillTree> DEFENSE = SkillTreeRegistry.registerSkillTree(ArsMagicaAPI.MODID, "defense", 1);
    public static final RegistryObject<SkillTree> UTILITY = SkillTreeRegistry.registerSkillTree(ArsMagicaAPI.MODID, "utility", 2);
    public static final RegistryObject<SkillTree> AFFINITY = SkillTreeRegistry.registerSkillTree(ArsMagicaAPI.MODID, "affinity", 3);
    public static final RegistryObject<SkillTree> TALENT = SkillTreeRegistry.registerSkillTree(ArsMagicaAPI.MODID, "talent", 4);

    public static void register() {
    }
}
