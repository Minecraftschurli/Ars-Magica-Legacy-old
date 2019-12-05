package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.util.*;
import net.minecraftforge.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.registries.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-03
 */
public class ArsMagicaLegacyAPI {
    public static IForgeRegistry<AbstractSpellPart> SPELL_PART_REGISTRY = null;
    public static IForgeRegistry<Skill> SKILL_REGISTRY = null;

    @SubscribeEvent
    public static void registerRegistries(final RegistryEvent.NewRegistry event) {
        SPELL_PART_REGISTRY = new RegistryBuilder<AbstractSpellPart>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "spell_parts")).setType(AbstractSpellPart.class).create();
        SKILL_REGISTRY = new RegistryBuilder<Skill>().setName(new ResourceLocation(ArsMagicaLegacy.MODID, "skills")).setType(Skill.class).create();

        SpellParts.register();
    }
}
