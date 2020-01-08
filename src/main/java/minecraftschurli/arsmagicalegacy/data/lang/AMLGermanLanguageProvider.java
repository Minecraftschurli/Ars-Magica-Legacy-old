package minecraftschurli.arsmagicalegacy.data.lang;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.data.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.data.*;

/**
 * @author Minecraftschurli
 * @version 2020-01-06
 */
public class AMLGermanLanguageProvider extends ArsMagicaLanguageProvider {
    public AMLGermanLanguageProvider(DataGenerator gen) {
        super(gen, ArsMagicaLegacy.MODID, "de_de");
    }

    @Override
    protected void addTranslations() {
        addBlock(ModBlocks.SUNSTONE_BLOCK, "Sonnensteinblock");
        addBlock(ModBlocks.SUNSTONE_ORE, "Sonnensteinerz");
        addItem(ModItems.SUNSTONE, "Sonnenstein");

        addBlock(ModBlocks.MOONSTONE_BLOCK, "Mondsteinblock");
        addBlock(ModBlocks.MOONSTONE_ORE, "Mondsteinerz");
        addItem(ModItems.MOONSTONE, "Mondstein");

        addBlock(ModBlocks.CHIMERITE_BLOCK, "Chimeritblock");
        addBlock(ModBlocks.CHIMERITE_ORE, "Chimeriterz");
        addItem(ModItems.CHIMERITE, "Chimerit");

        addBlock(ModBlocks.TOPAZ_BLOCK, "Topasblock");
        addBlock(ModBlocks.TOPAZ_ORE, "Topaserz");
        addItem(ModItems.TOPAZ, "Topas");

        addBlock(ModBlocks.VINTEUM_BLOCK, "Vinteumblock");
        addBlock(ModBlocks.VINTEUM_ORE, "Vinteumerz");
        addItem(ModItems.VINTEUM, "Vinteum");
        addItem(ModItems.PURIFIED_VINTEUM, "Gereinigtes Vinteum");

        addBlock(ModBlocks.WITCHWOOD_PLANKS, "Hexenholzbretter");
        addBlock(ModBlocks.WITCHWOOD_LOG, "Hexenholzstamm");
        addBlock(ModBlocks.WITCHWOOD_STAIRS, "Hexenholztreppe");
        addBlock(ModBlocks.WITCHWOOD_SLAB, "Hexenholzstufe");
        addBlock(ModBlocks.WITCHWOOD_BUTTON, "Hexenholzknopf");
        addBlock(ModBlocks.WITCHWOOD_DOOR, "Henxenholztür");
        addBlock(ModBlocks.WITCHWOOD_FENCE, "Hexenholzzaun");
        addBlock(ModBlocks.WITCHWOOD_FENCE_GATE, "Hexenholzzauntor");
        addBlock(ModBlocks.WITCHWOOD_LEAVES, "Hexenholzlaub");
        addBlock(ModBlocks.WITCHWOOD_PRESSURE_PLATE, "Hexenholzdruckplatte");
        addBlock(ModBlocks.WITCHWOOD_TRAPDOOR, "Hexenholzfalltür");
        addBlock(ModBlocks.WITCHWOOD_WOOD, "Hexenholz");
        addBlock(ModBlocks.WITCHWOOD_SAPLING, "Hexenholzsapling");
        addBlock(ModBlocks.STRIPPED_WITCHWOOD_LOG, "Entrindeter Hexenholzstamm");
        addBlock(ModBlocks.STRIPPED_WITCHWOOD_WOOD, "Entrindetes Hexenholz");

        addBlock(ModBlocks.ALTAR_CORE, "Altarkern");
        addBlock(ModBlocks.INSCRIPTION_TABLE, "Inschriftentisch");
        addBlock(ModBlocks.OCCULUS, "Okkulus");

        addItem(ModItems.RUNE, "Rune");
        addItem(ModItems.BLUE_RUNE, "Blaue Rune");
        addItem(ModItems.WHITE_RUNE, "Weiße Rune");
        addItem(ModItems.PURPLE_RUNE, "Violette Rune");
        addItem(ModItems.BLACK_RUNE, "Schwarze Rune");
        addItem(ModItems.GREEN_RUNE, "Grüne Rune");
        addItem(ModItems.RED_RUNE, "Rote Rune");
        addItem(ModItems.YELLOW_RUNE, "Gelbe Rune");
        addItem(ModItems.ORANGE_RUNE, "Orange Rune");
        addItem(ModItems.BROWN_RUNE, "Braune Rune");
        addItem(ModItems.CYAN_RUNE, "Türkise Rune");
        addItem(ModItems.GRAY_RUNE, "Graue Rune");
        addItem(ModItems.LIGHT_BLUE_RUNE, "Hellblaue Rune");
        addItem(ModItems.LIGHT_GRAY_RUNE, "Hellgraue Rune");
        addItem(ModItems.LIME_RUNE, "Hellgrüne Rune");
        addItem(ModItems.MAGENTA_RUNE, "Magenta Rune");
        addItem(ModItems.PINK_RUNE, "Pinke Rune");

        addItem(ModItems.ARCANE_ESSENCE, "Magieessenz");
        addItem(ModItems.ENDER_ESSENCE, "Enderessenz");
        addItem(ModItems.LIFE_ESSENCE, "Lebensessenz");
        addItem(ModItems.ICE_ESSENCE, "Eisessenz");
        addItem(ModItems.NATURE_ESSENCE, "Naturessenz");
        addItem(ModItems.LIGHTNING_ESSENCE, "Blitzessenz");
        addItem(ModItems.AIR_ESSENCE, "Luftessenz");
        addItem(ModItems.EARTH_ESSENCE, "Erdessenz");
        addItem(ModItems.FIRE_ESSENCE, "Feueressenz");
        addItem(ModItems.WATER_ESSENCE, "Wasseressenz");

        addItem(ModItems.ARCANE_ASH, "Magische Asche");
        addItem(ModItems.SPELL, "Zauber");
        addItem(ModItems.INFINITY_ORB, "%s Infinity Orb");
        addItem(ModItems.SPELL_BOOK, "Buch der Zauber");
        addItem(ModItems.ARCANE_COMPENDIUM, "Magisches Lexikon");

        addItem(ModItems.LESSER_FOCUS, "Schwacher Fokus");
        addItem(ModItems.STANDARD_FOCUS, "Normaler Fokus");
        addItem(ModItems.GREATER_FOCUS, "Starker Fokus");
        addItem(ModItems.MANA_FOCUS, "Manafokus");
        addItem(ModItems.MONSTER_FOCUS, "Monsterfokus");
        addItem(ModItems.PLAYER_FOCUS, "Spielerfokus");
        addItem(ModItems.CREATURE_FOCUS, "Kreaturfokus");
        addItem(ModItems.ITEM_FOCUS, "Itemfokus");
        addItem(ModItems.CHARGE_FOCUS, "Aufgeladener Focus");

        addBiome(ModBiomes.WITCHWOOD_FOREST, "Hexenwald");

        addEntityType(ModEntities.SPELL_PROJECTILE, "Zaubergeschoss");
        addEntityType(ModEntities.THROWN_ROCK, "Magischer Meteor");

        addEffect(ModEffects.ASTRAL_DISTORTION, "Astrale Verwirrung");
        addEffect(ModEffects.SHRINK, "Schrumpfung");
        addEffect(ModEffects.CLARITY, "Klarheit");
        addEffect(ModEffects.MAGIC_SHIELD, "Magieschild");
        addEffect(ModEffects.TEMPORAL_ANCHOR, "Zeitanker");
        addEffect(ModEffects.AGILITY, "Agilität");
        addEffect(ModEffects.ENTANGLE, "Verwirrung");
        addEffect(ModEffects.FROST, "Frost");
        addEffect(ModEffects.SILENCE, "Stille");
        addEffect(ModEffects.FLIGHT, "Flug");
        addEffect(ModEffects.FURY, "Rage");
        addEffect(ModEffects.GRAVITY_WELL, "Schwerkraft");
        addEffect(ModEffects.SCRAMBLE_SYNAPSES, "Zerstreuung");
        addEffect(ModEffects.SPELL_REFLECT, "Magische Reflexion");
        addEffect(ModEffects.SWIFT_SWIM, "Schwimmgeschwindigkeit");
        addEffect(ModEffects.TRUE_SIGHT, "Klarsicht");
        addEffect(ModEffects.WATERY_GRAVE, "Wassergrab");
        addEffect(ModEffects.BURNOUT_REDUCTION, "Chillout");
        addEffect(ModEffects.ILLUMINATION, "Erleuchtung");
        addEffect(ModEffects.INSTANT_MANA, "Sofortmana");
        addEffect(ModEffects.MANA_BOOST, "Manaboost");
        addEffect(ModEffects.MANA_REGEN, "Manaregeneration");
        addEffect(ModEffects.SHIELD, "Schild");

        addSpellPart(ModSpellParts.BLIZZARD, "Blizzard", "Schnee. Sehr viel Schnee");
        addSpellPart(ModSpellParts.FALLING_STAR, "Sternschlag", "Leuchtend! Warte, fällt es auf mich zu?");
        addSpellPart(ModSpellParts.FIRE_RAIN, "Feuerregen", "Zumindest werde ich mich nicht erkälten...");
        addSpellPart(ModSpellParts.DISMEMBERING, "Zerlegung", "Ich wars nicht. Ich schwöre, er hatte keinen Kopf mehr, als ich kam.");
        addSpellPart(ModSpellParts.MANA_BLAST, "Manabombe", "Ich LIEBE Mana, speziell wenn es das Gesicht von anderen sprengt.");

        //addSpellPart(ModSpellParts.MANA_LINK, "Manalink", "Müde? Ein bisschen was geht noch.");
        addSpellPart(ModSpellParts.MANA_SHIELD, "Manaschild", "Jetzt weiß ich, wie Unnahbarkeit aussieht.");
        addSpellPart(ModSpellParts.BUFF_POWER, "Zauberkraft", "Stärker, schneller, besser und... meine Manaleiste ist leer.");

        addSpellPart(ModSpellParts.DAYLIGHT, "Tageslicht", "Heißt das, ich kann die Zeit kontrollieren?");
        addSpellPart(ModSpellParts.MOONRISE, "Mondlicht", "Vollmond.");
        //addSpellPart(ModSpellParts.PROSPERITY, "Wohlstand", "Gold. Unmengen an Gold.");

        addSkill(ModSpellParts.MANA_REGEN_1, "Manaheilung I", "Du fühlst die Mana, wie sie dich umfließt...");
        addSkill(ModSpellParts.MANA_REGEN_2, "Manaheilung II", "All diese Energie... wieso verschwenden?");
        addSkill(ModSpellParts.MANA_REGEN_3, "Manaheilung III", "Deine Kontrolle über Mana hat ihren Höhepunkt erreicht.");
        addSkill(ModSpellParts.MAGE_POSSE_1, "Magierbindung I", "Cool, ein magischer Freund!");
        addSkill(ModSpellParts.MAGE_POSSE_2, "Magierbindung II", "Lass uns teilen!");
        addSkill(ModSpellParts.EXTRA_SUMMONS, "Beschwörung", "Von den Toten auferstanden! Und... noch einer...");
        addSpellPart(ModSpellParts.COLOR, "Farbe", "Regenbogen!");
        addSkill(ModSpellParts.SPELL_MOTION, "Zauberbewegung", "Mobile Magie.");
        addSkill(ModSpellParts.SHIELD_OVERLOAD, "Schildüberladung", "Zu viel Mana? Dann machen wir ein Schild draus...");
        addSkill(ModSpellParts.AUGMENTED_CASTING, "Erweitertes Zaubern", "Bessere Magie.");
        addSkill(ModSpellParts.AFFINITY_GAINS, "Affinitätsboost", "Wie wärs mit dem Teil, in dem ich Superkräfte habe?");

        addSpellPart(ModSpellParts.PROJECTILE, "Projektil", "Schneeball!");
        addSpellPart(ModSpellParts.GRAVITY, "Schwerkraft", "Fallen... okay...");
        addSpellPart(ModSpellParts.BOUNCE, "Abprallen", "Hey! Komm zurück!");
        addSpellPart(ModSpellParts.PHYSICAL_DAMAGE, "Physischer Schaden", "Schwert mit Bogenreichweite... wieso nicht?");
        addSpellPart(ModSpellParts.FIRE_DAMAGE, "Feuerschaden", "Feuer!");
        addSpellPart(ModSpellParts.IGNITION, "Verbrennung", "Mehr Feuer!");
        addSpellPart(ModSpellParts.FORGE, "Schmiedefeuer", "Mobiler Ofen!");
        //addSpellPart(ModSpellParts.CONTINGENCY_FIRE, "Eventualität: Feuer", "Brenn (oder auch nicht)!");
        addSpellPart(ModSpellParts.LIGHTNING_DAMAGE, "Blitzschaden", "Zap!");
        addSpellPart(ModSpellParts.BLIND, "Blindheit", "Kann jemand das Licht anmachen? Nein?");
        addSpellPart(ModSpellParts.SOLAR, "Solarkraft", "Sonnenenergie!");
        addSpellPart(ModSpellParts.STORM, "Gewitter", "Es regnet...");
        addSpellPart(ModSpellParts.FURY, "Wut", "Ich bin WÜTEND!");
        addSpellPart(ModSpellParts.AOE, "Flächeneffekt", "Kontrolle. Schichtenweise.");
        addSpellPart(ModSpellParts.BEAM, "Strahl", "Beam mich weg, Scotty!");
        addSpellPart(ModSpellParts.DAMAGE, "Schaden", "Das tut weh...");
        addSpellPart(ModSpellParts.PIERCING, "Durchlöcherung", "Rüstung, mach dich bereit!");
        addSpellPart(ModSpellParts.FROST_DAMAGE, "Frostschaden", "Kalt...");
        addSpellPart(ModSpellParts.FREEZE, "Starre", "Erstarre!");
        addSpellPart(ModSpellParts.SILENCE, "Stille", "Nicht reden! (oder zaubern, in dem Fall...)");
        addSpellPart(ModSpellParts.ASTRAL_DISTORTION, "Astrale Verkrümmung", "Auf dem Weg ins Nirgendwo...");
        addSpellPart(ModSpellParts.WAVE, "Welle", "Eher nicht zum Surfen...");
        addSpellPart(ModSpellParts.MAGIC_DAMAGE, "Magieschaden", "Schlag aus dem Nichts!");
        addSpellPart(ModSpellParts.KNOCKBACK, "Rückstoß", "Nach hinten gestoßen!");
        addSpellPart(ModSpellParts.FLING, "Schleuderung", "Bereit für einen Luftkampf?");
        addSpellPart(ModSpellParts.VELOCITY_ADDED, "Extrageschwindigkeit", "Schneller! SCHNELLER!");
        addSpellPart(ModSpellParts.DROWN, "Ertrinken", "Wie kannst du ertrinken? Hier ist kein Wasser.");
        addSpellPart(ModSpellParts.WATERY_GRAVE, "Wassergrab", "Grund des Ozeans.");

        addSpellPart(ModSpellParts.WALL, "Mauer", "Du darfst nicht vorbei.");
        addSpellPart(ModSpellParts.APPROPRIATON, "Beschlagnahmung", "Meins! Meins!");
        addSpellPart(ModSpellParts.SLOW, "Verlangsamung", "Kein Laufen mehr!");
        addSpellPart(ModSpellParts.REPEL, "Abstoßung", "Bleib mir vom Leib!");
        addSpellPart(ModSpellParts.ENTANGLE, "Verwirrung", "Halt. Stop.");
        addSpellPart(ModSpellParts.RUNE, "Rune", "Magie zum Platzieren.");
        addSpellPart(ModSpellParts.RUNE_PROCS, "Extrarunen", "Ich will mehr!");
        addSpellPart(ModSpellParts.HASTE, "Eile", "Ich muss weg...");
        addSpellPart(ModSpellParts.SWIFT_SWIM, "Schwimmgeschwindigkeit", "Nie wieder stundenlang schwimmen.");
        addSpellPart(ModSpellParts.ACCELERATE, "Beschleunigung", "Du fängst mich demnächst nicht mehr so schnell.");
        addSpellPart(ModSpellParts.SPEED, "Geschwindigkeit", "Projektile sind lagsam, hier ist eine Lösung dafür.");
        addSpellPart(ModSpellParts.LEAP, "Sprung", "Kein Frosch? Und?");
        addSpellPart(ModSpellParts.SLOWFALL, "Federfall", "Wie ein Huhn...");
        addSpellPart(ModSpellParts.GRAVITY_WELL, "Gravitationskammer", "NICHT wie ein Huhn. Eher das Gegenteil.");
        addSpellPart(ModSpellParts.LEVITATE, "Levitation", "Benutze die Macht.");
        addSpellPart(ModSpellParts.FLIGHT, "Flug", "Eher ein Flugzeug benutzen.");
        addSpellPart(ModSpellParts.SELF, "Ich", "Alles dreht sich um mich.");
        addSpellPart(ModSpellParts.SUMMON, "Beschwörung", "Erwache! Ach, du siehst aus wie die anderen...");
        //addSpellPart(ModSpellParts.CONTINGENCY_FALL, "Enventualität: Fall", "Fallen... Jetzt hat es wenigstens einen Sinn.");
        addSpellPart(ModSpellParts.LIFE_TAP, "Leben anzapfen", "Ich borg mir das aus.");
        addSpellPart(ModSpellParts.LIFE_DRAIN, "Lebensentzug", "Ich nehm dir alles. Inklusive dich selbst");
        addSpellPart(ModSpellParts.MANA_DRAIN, "Manaentzug", "So viele Manaleisten!");
        addSpellPart(ModSpellParts.REGENERATION, "Regeneration", "Ein bisschen Gesundheit.");
        addSpellPart(ModSpellParts.HEAL, "Heilung", "Sofortige Heilung.");
        addSpellPart(ModSpellParts.DISPEL, "Verbannung", "Beseitung des Chaos.");
        addSpellPart(ModSpellParts.ZONE, "Zone", "Niemand besiegt mich in meinem Revier!");
        addSpellPart(ModSpellParts.SHIELD, "Schild", "Das Gewicht ist egal, es ist Magie.");
        addSpellPart(ModSpellParts.REFLECT, "Reflexion", "Zurück zum Absender.");
        addSpellPart(ModSpellParts.CHRONO_ANCHOR, "Zeitanker", "Wie spät ist es? Oh! Die Zeit geht rückwärts!");
        addSpellPart(ModSpellParts.DURATION, "Dauer", "Zeitmanipulation und so.");
        addSpellPart(ModSpellParts.SHRINK, "Schrumpfung", "Hm, ich bin kleiner...");
        addSpellPart(ModSpellParts.HEALING, "Sofortheilung", "Effizienz statt Zeit.");
        addSpellPart(ModSpellParts.ABSORPTION, "Absorption", "Wie ein Schild, nur nicht so dick.");
        //addSpellPart(ModSpellParts.CONTINGENCY_HEALTH, "Eventualität: Heilung", "I'm not going down... not right now.");
        //addSpellPart(ModSpellParts.CONTINGENCY_DAMAGE, "Eventualität: Schaden", "Hurting me? That would be bad...");

        addSpellPart(ModSpellParts.TOUCH, "Berührung", "Klopf klopf? Jemand da?");
        addSpellPart(ModSpellParts.DIG, "Graben", "Baggerfahrer.");
        addSpellPart(ModSpellParts.WIZARDS_AUTUMN, "Herbst", "Blätter fallen nur langsam...");
        addSpellPart(ModSpellParts.TARGET_NON_SOLID_BLOCKS, "Durchlässige Ziele", "Und ich wollte gegen Wasser kämpfen.");
        addSpellPart(ModSpellParts.PLACE_BLOCK, "Block setzen", "Beachte mich nicht, ich schick nur nen Amboss.");
        addSpellPart(ModSpellParts.SILK_TOUCH, "Behutsamkeit", "Sanft wie Seide...");
        addSpellPart(ModSpellParts.MINING_POWER, "Abbaukraft", "Wer braucht Diamanten?");
        addSpellPart(ModSpellParts.LIGHT, "Licht", "Ende eines Tunnels.");
        addSpellPart(ModSpellParts.NIGHT_VISION, "Nachtsicht", "Oh! Das war ein Tunnel?");
        //addSpellPart(ModSpellParts.BINDING, "Bindung", "Schwert, Axt, Schaufel... wer braucht Werkzeuge?");
        addSpellPart(ModSpellParts.DISARM, "Entwaffnung", "Ups, hast du was verloren?");
        addSpellPart(ModSpellParts.CHARM, "Gedankenkontrolle", "Freunde! Viele Freunde! Alle in die Luft gegangen...");
        addSpellPart(ModSpellParts.TRUE_SIGHT, "Klarsicht", "Sieh das Versteckte.");
        addSpellPart(ModSpellParts.LUNAR, "Sternenkraft", "Ich bin ein Werwolf!");
        addSpellPart(ModSpellParts.HARVEST_PLANTS, "Ernte", "Uuuund... ihr seid gewachsen.");
        addSpellPart(ModSpellParts.PLOW, "Pflug", "Hacken sind nutzlos, wie jeder weiß.");
        addSpellPart(ModSpellParts.PLANT, "Pflanzen", "Warum die Hände benutzen, wenn Magie das auch kann?");
        addSpellPart(ModSpellParts.CREATE_WATER, "Wassermagie", "Ja, wollte aus Schnee Wasser machen...");
        addSpellPart(ModSpellParts.DROUGHT, "Dürre", "Sand. Wer kennts nicht?");
        addSpellPart(ModSpellParts.BANISH_RAIN, "Regentanz", "Komm später wieder. Oder gar nicht.");
        addSpellPart(ModSpellParts.WATER_BREATHING, "Wasseratmung", "Luft direkt in den Lungen? Cool!");
        addSpellPart(ModSpellParts.GROW, "Wachstum", "Ich werde nicht den ganzen Tag warten.");
        addSpellPart(ModSpellParts.CHAIN, "Kette", "Du hast Freunde mitgebracht? Egal, dann sterbt ihr alle.");
        addSpellPart(ModSpellParts.RIFT, "Dimensionsspeicher", "Eines Tages werde ich durchgehen, aber aktuell speichern sie nur Gegenstände.");
        addSpellPart(ModSpellParts.INVISIBILITY, "Unsichtbarkeit", "Magst du Verstecken spielen?");
        addSpellPart(ModSpellParts.RANDOM_TELEPORT, "Zufallsteleportation", "Ich will... dorthin! Ups, falscher Ort.");
        addSpellPart(ModSpellParts.ATTRACT, "Attraktion", "Komm näher... oder egal, bleib wo du bist.");
        addSpellPart(ModSpellParts.TELEKINESIS, "Telekinese", "Rauf, runter, rauf, runter...");
        addSpellPart(ModSpellParts.BLINK, "Flackern", "Ich bin dann mal weg.");
        addSpellPart(ModSpellParts.RANGE, "Reichweite", "Denkst du, ich erwische dich nicht? Falsch gedacht.");
        addSpellPart(ModSpellParts.CHANNEL, "Kanal", "Konzentration ist hilfreich.");
        addSpellPart(ModSpellParts.RADIUS, "Radius", "Verfehlt? Nun, das Ding wird größer und größer...");
        addSpellPart(ModSpellParts.TRANSPLACE, "Teleportation", "Von A nach B.");
        addSpellPart(ModSpellParts.MARK, "Heimat", "Home sweet home.");
        addSpellPart(ModSpellParts.RECALL, "Rückruf", "Kann jemand den Müll rausbringen?");
        addSpellPart(ModSpellParts.DIVINE_INTERVENTION, "Außerirdischer Eingriff", "Hilfe aus dem Weltall.");
        addSpellPart(ModSpellParts.ENDER_INTERVENTION, "Endereingriff", "Ich wusste nicht, wie die Hölle aussieht...");
        //addSpellPart(ModSpellParts.CONTINGENCY_DEATH, "Eventualität: Tod", "Du kommst mit mir.");

        add(ModSpellParts.SKILL_POINT_1, "Blau");
        add(ModSpellParts.SKILL_POINT_2, "Rot");
        add(ModSpellParts.SKILL_POINT_3, "Grün");

        add("itemGroup.arsmagicalegacy", "Ars Magica: Legacy");

        add("arsmagicalegacy.gui.inscriptiontable.search", "Suchen");
        add("arsmagicalegacy.occulus.prevent", "Mythische Kräfte hindern dich am Benutzen dieses Konstrukts!");
    }
}