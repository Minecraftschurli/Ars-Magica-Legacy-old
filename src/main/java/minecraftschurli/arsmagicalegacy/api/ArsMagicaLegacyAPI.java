package minecraftschurli.arsmagicalegacy.api;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.init.SpellParts;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

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
