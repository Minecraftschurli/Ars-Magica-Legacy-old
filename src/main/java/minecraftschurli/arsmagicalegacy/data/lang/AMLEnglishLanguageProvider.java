package minecraftschurli.arsmagicalegacy.data.lang;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.data.ArsMagicaLanguagePlugin;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * @author Minecraftschurli
 * @version 2020-01-01
 */
public class AMLEnglishLanguageProvider extends LanguageProvider implements ArsMagicaLanguagePlugin {
    public AMLEnglishLanguageProvider(DataGenerator gen) {
        super(gen, ArsMagicaLegacy.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addBlock(ModBlocks.SUNSTONE_BLOCK, "Sunstone Block");
        addBlock(ModBlocks.SUNSTONE_ORE, "Sunstone Ore");
        addItem(ModItems.SUNSTONE, "Sunstone");

        addBlock(ModBlocks.MOONSTONE_BLOCK, "Moonstone Block");
        addBlock(ModBlocks.MOONSTONE_ORE, "Moonstone Ore");
        addItem(ModItems.MOONSTONE, "Moonstone");

        addBlock(ModBlocks.CHIMERITE_BLOCK, "Chimerite Block");
        addBlock(ModBlocks.CHIMERITE_ORE, "Chimerite Ore");
        addItem(ModItems.CHIMERITE, "Chimerite");

        addBlock(ModBlocks.TOPAZ_BLOCK, "Topaz Block");
        addBlock(ModBlocks.TOPAZ_ORE, "Topaz Ore");
        addItem(ModItems.TOPAZ, "Topaz");

        addBlock(ModBlocks.VINTEUM_BLOCK, "Vinteum Block");
        addBlock(ModBlocks.VINTEUM_ORE, "Vinteum Ore");
        addItem(ModItems.VINTEUM, "Vinteum");
        addItem(ModItems.PURIFIED_VINTEUM, "Purified Vinteum");

        addBlock(ModBlocks.WITCHWOOD_PLANKS, "Witchwood Planks");
        addBlock(ModBlocks.WITCHWOOD_LOG, "Witchwood Log");
        addBlock(ModBlocks.WITCHWOOD_STAIRS, "Witchwood Stairs");
        addBlock(ModBlocks.WITCHWOOD_SLAB, "Witchwood Slab");
        addBlock(ModBlocks.WITCHWOOD_BUTTON, "Witchwood Button");
        addBlock(ModBlocks.WITCHWOOD_DOOR, "Witchwood Door");
        addBlock(ModBlocks.WITCHWOOD_FENCE, "Witchwood Fence");
        addBlock(ModBlocks.WITCHWOOD_FENCE_GATE, "Witchwood Fence Gate");
        addBlock(ModBlocks.WITCHWOOD_LEAVES, "Witchwood Leaves");
        addBlock(ModBlocks.WITCHWOOD_PRESSURE_PLATE, "Witchwood Pressure Plate");
        addBlock(ModBlocks.WITCHWOOD_TRAPDOOR, "Witchwood Trapdoor");
        addBlock(ModBlocks.WITCHWOOD_WOOD, "Witchwood Wood");
        addBlock(ModBlocks.WITCHWOOD_SAPLING, "Witchwood Sapling");
        addBlock(ModBlocks.STRIPPED_WITCHWOOD_LOG, "Stripped Witchwood Log");
        addBlock(ModBlocks.STRIPPED_WITCHWOOD_WOOD, "Stripped Witchwood Wood");

        addBlock(ModBlocks.ALTAR_CORE, "Altar Core");
        addBlock(ModBlocks.INSCRIPTION_TABLE, "Inscription Table");
        addBlock(ModBlocks.OCCULUS, "Occulus");
        addBlock(ModBlocks.MANA_BATTERY, "Mana Battery");

        addBlock(ModBlocks.VINTEUM_TORCH, "Vinteum Torch");

        addBlock(ModBlocks.MAGIC_WALL, "Magic Wall");
        addBlock(ModBlocks.GOLD_INLAY, "Gold Inlay");
        addBlock(ModBlocks.REDSTONE_INLAY, "Redstone Inlay");
        addBlock(ModBlocks.IRON_INLAY, "Iron Inlay");

        addBlock(ModBlocks.WAKEBLOOM, "Wakebloom");
        addBlock(ModBlocks.TARMA_ROOT, "Tarma Root");
        addBlock(ModBlocks.AUM, "Aum");
        addBlock(ModBlocks.DESERT_NOVA, "Desert Nova");
        addBlock(ModBlocks.CERUBLOSSOM, "Cerublossom");

        addItem(ModItems.RUNE, "Rune");
        addItem(ModItems.BLUE_RUNE, "Blue Rune");
        addItem(ModItems.WHITE_RUNE, "White Rune");
        addItem(ModItems.PURPLE_RUNE, "Purple Rune");
        addItem(ModItems.BLACK_RUNE, "Black Rune");
        addItem(ModItems.GREEN_RUNE, "Green Rune");
        addItem(ModItems.RED_RUNE, "Red Rune");
        addItem(ModItems.YELLOW_RUNE, "Yellow Rune");
        addItem(ModItems.ORANGE_RUNE, "Orange Rune");
        addItem(ModItems.BROWN_RUNE, "Brown Rune");
        addItem(ModItems.CYAN_RUNE, "Cyan Rune");
        addItem(ModItems.GRAY_RUNE, "Gray Rune");
        addItem(ModItems.LIGHT_BLUE_RUNE, "Light Blue Rune");
        addItem(ModItems.LIGHT_GRAY_RUNE, "Light Gray Rune");
        addItem(ModItems.LIME_RUNE, "Lime Rune");
        addItem(ModItems.MAGENTA_RUNE, "Magenta Rune");
        addItem(ModItems.PINK_RUNE, "Pink Rune");

        addItem(ModItems.ARCANE_ESSENCE, "Arcane Essence");
        addItem(ModItems.ENDER_ESSENCE, "Ender Essence");
        addItem(ModItems.LIFE_ESSENCE, "Life Essence");
        addItem(ModItems.ICE_ESSENCE, "Ice Essence");
        addItem(ModItems.NATURE_ESSENCE, "Nature Essence");
        addItem(ModItems.LIGHTNING_ESSENCE, "Lightning Essence");
        addItem(ModItems.AIR_ESSENCE, "Air Essence");
        addItem(ModItems.EARTH_ESSENCE, "Earth Essence");
        addItem(ModItems.FIRE_ESSENCE, "Fire Essence");
        addItem(ModItems.WATER_ESSENCE, "Water Essence");

        addItem(ModItems.MANA_CAKE, "Manacake");
        addItem(ModItems.MANA_MARTINI, "Mana Martini");
        addItem(ModItems.PIG_FAT, "Pig Fat");
        addItem(ModItems.ARCANE_ASH, "Arcane Ash");
        addItem(ModItems.ARCANE_COMPOUND, "Arcane Compound");
        addItem(ModItems.WOODEN_LEG, "Wooden Leg");
        addItem(ModItems.SPELL, "Spell");
        addItem(ModItems.INFINITY_ORB, "%s Infinity Orb");
        addItem(ModItems.SPELL_BOOK, "Spell Book");
        addItem(ModItems.ARCANE_COMPENDIUM, "Arcane Compendium");
        addItem(ModItems.SPELL_PARCHMENT, "Spell Parchment");
        addItem(ModFluids.LIQUID_ESSENCE_BUCKET, "Liquid Essence Bucket");
        addItemStack(ModItems.INSCRIPTION_UPGRADE.lazyMap(item -> item.getTieredStack(0)), "Tier I Upgrade");
        addItemStack(ModItems.INSCRIPTION_UPGRADE.lazyMap(item -> item.getTieredStack(1)), "Tier II Upgrade");
        addItemStack(ModItems.INSCRIPTION_UPGRADE.lazyMap(item -> item.getTieredStack(2)), "Tier III Upgrade");

        addItem(ModItems.LESSER_FOCUS, "Lesser Focus");
        addItem(ModItems.STANDARD_FOCUS, "Standard Focus");
        addItem(ModItems.GREATER_FOCUS, "Greater Focus");
        addItem(ModItems.MANA_FOCUS, "Mana Focus");
        addItem(ModItems.MONSTER_FOCUS, "Monster Focus");
        addItem(ModItems.PLAYER_FOCUS, "Player Focus");
        addItem(ModItems.CREATURE_FOCUS, "Creature Focus");
        addItem(ModItems.ITEM_FOCUS, "Item Focus");
        addItem(ModItems.CHARGE_FOCUS, "Charge Focus");

        addBiome(ModBiomes.WITCHWOOD_FOREST, "Witchwood Forest");

        addEntityType(ModEntities.SPELL_PROJECTILE, "Spell Projectile");
        addEntityType(ModEntities.THROWN_ROCK, "Thrown Rock");

        addEffect(ModEffects.ASTRAL_DISTORTION, "Astral Distortion");
        addEffect(ModEffects.SHRINK, "Shrink");
        addEffect(ModEffects.CLARITY, "Clarity");
        addEffect(ModEffects.MAGIC_SHIELD, "Magic Shield");
        addEffect(ModEffects.TEMPORAL_ANCHOR, "Temporal Anchor");
        addEffect(ModEffects.AGILITY, "Agility");
        addEffect(ModEffects.ENTANGLE, "Entangle");
        addEffect(ModEffects.FROST, "Frost");
        addEffect(ModEffects.SILENCE, "Silence");
        addEffect(ModEffects.FLIGHT, "Flight");
        addEffect(ModEffects.FURY, "Fury");
        addEffect(ModEffects.GRAVITY_WELL, "Gravity Well");
        addEffect(ModEffects.SCRAMBLE_SYNAPSES, "Scramble Synapses");
        addEffect(ModEffects.SPELL_REFLECT, "Spell Reflect");
        addEffect(ModEffects.SWIFT_SWIM, "Swift Swim");
        addEffect(ModEffects.TRUE_SIGHT, "True Sight");
        addEffect(ModEffects.WATERY_GRAVE, "Watery Grave");
        addEffect(ModEffects.BURNOUT_REDUCTION, "Chillout");
        addEffect(ModEffects.ILLUMINATION, "Illumination");
        addEffect(ModEffects.INSTANT_MANA, "Instant Mana");
        addEffect(ModEffects.MANA_BOOST, "Mana Boost");
        addEffect(ModEffects.MANA_REGEN, "Mana Regen");
        addEffect(ModEffects.SHIELD, "Shield");

        addSpellPart(ModSpellParts.BLIZZARD, "Blizzard", "Snow. Lots of snow");
        addSpellPart(ModSpellParts.FALLING_STAR, "Starstrike", "Shiny! Wait, is it falling towards me?");
        addSpellPart(ModSpellParts.FIRE_RAIN, "Fire Rain", "Well, at least I'm not going to catch a cold...");
        addSpellPart(ModSpellParts.DISMEMBERING, "Dismembering", "Wasn't me. I swear he had no head when I came in.");
        addSpellPart(ModSpellParts.MANA_BLAST, "Mana Blast", "I LOVE mana, especially when it blows up in someone's face.");

        //addSpellPart(ModSpellParts.MANA_LINK, "Mana Link", "Tired? Keep going.");
        addSpellPart(ModSpellParts.MANA_SHIELD, "Mana Shield", "Well, now I know what boredom looks like.");
        addSpellPart(ModSpellParts.BUFF_POWER, "Buff Power", "Harder, better, faster and... my mana pool is empty.");

        addSpellPart(ModSpellParts.DAYLIGHT, "Daylight", "Does that means I can control time?");
        addSpellPart(ModSpellParts.MOONRISE, "Moonrise", "Full Moon.");
        //addSpellPart(ModSpellParts.PROSPERITY, "Prosperity", "Gold. Loads of gold.");

        addSkill(ModSpellParts.MANA_REGEN_1, "Mana Regen I", "You can feel the mana flowing around you...");
        addSkill(ModSpellParts.MANA_REGEN_2, "Mana Regen II", "All this energy... why waste it?");
        addSkill(ModSpellParts.MANA_REGEN_3, "Mana Regen III", "Your control over mana has come to it's best.");
        addSkill(ModSpellParts.MAGE_POSSE_1, "Mage Band I", "Cool, a mage friend!");
        addSkill(ModSpellParts.MAGE_POSSE_2, "Mage Band II", "Let's share!");
        addSkill(ModSpellParts.EXTRA_SUMMONS, "Extra Summons", "Rise from the dead! And... one more...");
        addSpellPart(ModSpellParts.COLOR, "Color", "Rainbow!");
        addSkill(ModSpellParts.SPELL_MOTION, "Spell Motion", "Mobile magic.");
        addSkill(ModSpellParts.SHIELD_OVERLOAD, "Shield Overload", "Too much mana? Well it's always a good shield...");
        addSkill(ModSpellParts.AUGMENTED_CASTING, "Augmented Casting", "Better Magic.");
        addSkill(ModSpellParts.AFFINITY_GAINS, "Affinity Gain Boost", "Let's skip to the part where I have superpowers.");

        addSpellPart(ModSpellParts.PROJECTILE, "Projectile", "Snowball!");
        addSpellPart(ModSpellParts.GRAVITY, "Gravity", "Falling... okay...");
        addSpellPart(ModSpellParts.BOUNCE, "Bounce", "Hey! Come back!");
        addSpellPart(ModSpellParts.PHYSICAL_DAMAGE, "Physical Damage", "Ranged sword... why not?");
        addSpellPart(ModSpellParts.FIRE_DAMAGE, "Fire Damage", "You shall burn!");
        addSpellPart(ModSpellParts.IGNITION, "Ignition", "Burn harder!");
        addSpellPart(ModSpellParts.FORGE, "Forge", "Portable furnace!");
        //addSpellPart(ModSpellParts.CONTINGENCY_FIRE, "Contingency: Fire", "You shall (not) burn!");
        addSpellPart(ModSpellParts.LIGHTNING_DAMAGE, "Lightning Damage", "Zap!");
        addSpellPart(ModSpellParts.BLIND, "Blind", "Can someone switch on the lights? No?");
        addSpellPart(ModSpellParts.SOLAR, "Solar", "Sun power!");
        addSpellPart(ModSpellParts.STORM, "Thunderstorm", "It's raining...");
        addSpellPart(ModSpellParts.FURY, "Fury", "Berserker rage!");
        addSpellPart(ModSpellParts.AOE, "AoE", "Zone control.");
        addSpellPart(ModSpellParts.BEAM, "Beam", "Beam me up, Scotty!");
        addSpellPart(ModSpellParts.DAMAGE, "Damage", "Now it hurts...");
        addSpellPart(ModSpellParts.PIERCING, "Piercing", "Armor here I come!");
        addSpellPart(ModSpellParts.FROST_DAMAGE, "Frost Damage", "Cold...");
        addSpellPart(ModSpellParts.FREEZE, "Freeze", "Freeze!");
        addSpellPart(ModSpellParts.SILENCE, "Silence", "No talking! (or casting in this case...)");
        addSpellPart(ModSpellParts.ASTRAL_DISTORTION, "Astral Distortion", "Going nowhere...");
        addSpellPart(ModSpellParts.WAVE, "Wave", "You might not want to surf on this one...");
        addSpellPart(ModSpellParts.MAGIC_DAMAGE, "Magic Damage", "Hit from the void!");
        addSpellPart(ModSpellParts.KNOCKBACK, "Knockback", "Punch from a distance!");
        addSpellPart(ModSpellParts.FLING, "Fling", "Ready for an air fight?");
        addSpellPart(ModSpellParts.VELOCITY_ADDED, "Velocity Added", "Faster! FASTER!");
        addSpellPart(ModSpellParts.DROWN, "Drown", "How can you drown? There isn't any water.");
        addSpellPart(ModSpellParts.WATERY_GRAVE, "Watery Grave", "Bottom of the ocean.");

        addSpellPart(ModSpellParts.WALL, "Wall", "You shall not pass.");
        addSpellPart(ModSpellParts.APPROPRIATON, "Appropriation", "That's mine.");
        addSpellPart(ModSpellParts.SLOW, "Slow", "No more running!");
        addSpellPart(ModSpellParts.REPEL, "Repel", "Go away from me!");
        addSpellPart(ModSpellParts.ENTANGLE, "Entangle", "Stop right there.");
        addSpellPart(ModSpellParts.RUNE, "Rune", "Placeable magic.");
        addSpellPart(ModSpellParts.RUNE_PROCS, "Rune Procs", "I want more!");
        addSpellPart(ModSpellParts.HASTE, "Haste", "I'm out of here...");
        addSpellPart(ModSpellParts.SWIFT_SWIM, "Swift Swim", "No more swimming for hours.");
        addSpellPart(ModSpellParts.ACCELERATE, "Accelerate", "Seems like you won't be catching me anytime soon.");
        addSpellPart(ModSpellParts.SPEED, "Speed", "Projectiles are slow, here's something that could solve that problem.");
        addSpellPart(ModSpellParts.LEAP, "Leap", "Not a frog? Who cares?");
        addSpellPart(ModSpellParts.SLOWFALL, "Slowfall", "Like a chicken...");
        addSpellPart(ModSpellParts.GRAVITY_WELL, "Gravity Well", "NOT like a chicken. The opposite.");
        addSpellPart(ModSpellParts.LEVITATE, "Levitate", "Use the force.");
        addSpellPart(ModSpellParts.FLIGHT, "Flight", "I'd rather use a plane.");
        addSpellPart(ModSpellParts.SELF, "Self", "It's all about me.");
        addSpellPart(ModSpellParts.SUMMON, "Summon", "Rise creation! Oh you look like the others...");
        //addSpellPart(ModSpellParts.CONTINGENCY_FALL, "Contingency: Fall", "Falling... Well, at least now it has a purpose.");
        addSpellPart(ModSpellParts.LIFE_TAP, "Life Tap", "I'm burrowing this.");
        addSpellPart(ModSpellParts.LIFE_DRAIN, "Life Drain", "Ahem... I'm taking all of it. Including you.");
        addSpellPart(ModSpellParts.MANA_DRAIN, "Mana Drain", "So much pools at my disposal!");
        addSpellPart(ModSpellParts.REGENERATION, "Regeneration", "A little bit of health");
        addSpellPart(ModSpellParts.HEAL, "Heal", "Instant healing.");
        addSpellPart(ModSpellParts.DISPEL, "Dispel", "Cleaning up the mess.");
        addSpellPart(ModSpellParts.ZONE, "Zone", "No one can beat me in my sanctuary!");
        addSpellPart(ModSpellParts.SHIELD, "Shield", "Don't worry about the weight, it's magic.");
        addSpellPart(ModSpellParts.REFLECT, "Reflect", "Bounces back to you");
        addSpellPart(ModSpellParts.CHRONO_ANCHOR, "Chrono Anchor", "Let's look at the time. Oh dear! It went backward!");
        addSpellPart(ModSpellParts.DURATION, "Duration", "Time manipulation tricks.");
        addSpellPart(ModSpellParts.SHRINK, "Shrink", "Looks like I'm smaller now...");
        addSpellPart(ModSpellParts.HEALING, "Healing", "Efficiency over number.");
        addSpellPart(ModSpellParts.ABSORPTION, "Absorption", "Like a slightly flimsier shield.");
        //addSpellPart(ModSpellParts.CONTINGENCY_HEALTH, "Contingency: Health", "I'm not going down... not right now.");
        //addSpellPart(ModSpellParts.CONTINGENCY_DAMAGE, "Contingency: Damage", "Hurting me? That would be bad...");

        addSpellPart(ModSpellParts.TOUCH, "Touch", "Someone in there?");
        addSpellPart(ModSpellParts.DIG, "Dig", "Diggy Diggy Hole");
        addSpellPart(ModSpellParts.WIZARDS_AUTUMN, "Wizard's Autumn", "Leaves fall in autumn. But it's kind of a slow process...");
        addSpellPart(ModSpellParts.TARGET_NON_SOLID_BLOCKS, "Target Non-Solid Blocks", "And I decided that I would fight the water.");
        addSpellPart(ModSpellParts.PLACE_BLOCK, "Place Block", "Don't mind me, I'm just sending an Anvil.");
        addSpellPart(ModSpellParts.SILK_TOUCH, "Silk Touch", "Feels soft...");
        addSpellPart(ModSpellParts.MINING_POWER, "Mining Power", "Who needs diamond?");
        addSpellPart(ModSpellParts.LIGHT, "Light", "The end of a tunnel.");
        addSpellPart(ModSpellParts.NIGHT_VISION, "Night Vision", "Oh? There was a tunnel?");
        //addSpellPart(ModSpellParts.BINDING, "Binding", "Sword, axe, shovel... who needs tools?");
        addSpellPart(ModSpellParts.DISARM, "Disarm", "Woops, you dropped something?");
        addSpellPart(ModSpellParts.CHARM, "Charm", "Friends! Lots of friends! And they blew up...");
        addSpellPart(ModSpellParts.TRUE_SIGHT, "True Sight", "Reveal what is hidden.");
        addSpellPart(ModSpellParts.LUNAR, "Lunar", "I'm gonna be a werewolf!");
        addSpellPart(ModSpellParts.HARVEST_PLANTS, "Harvest", "Let's just say you're all grown.");
        addSpellPart(ModSpellParts.PLOW, "Plow", "Hoes are useless. Everyone knows that.");
        addSpellPart(ModSpellParts.PLANT, "Plant", "Why bother using hand when magic can do the same?");
        addSpellPart(ModSpellParts.CREATE_WATER, "Create Water", "Yop, totally intended turning my snow into water...");
        addSpellPart(ModSpellParts.DROUGHT, "Drought", "Sand. Who doesn't know that?");
        addSpellPart(ModSpellParts.BANISH_RAIN, "Banish Rain", "Come back latter. Or don't. It would be kind.");
        addSpellPart(ModSpellParts.WATER_BREATHING, "Water Breathing", "Creating air directly inside my lungs? Cool!");
        addSpellPart(ModSpellParts.GROW, "Grow", "I won't sit all day.");
        addSpellPart(ModSpellParts.CHAIN, "Chain", "Looks like you brought friends. Well I don't mind, you're all gonna die.");
        addSpellPart(ModSpellParts.RIFT, "Rift Storage", "One day I'll walk through it, for now, it'll just store items.");
        addSpellPart(ModSpellParts.INVISIBILITY, "Invisibility", "Wanna play Hide & Seek?");
        addSpellPart(ModSpellParts.RANDOM_TELEPORT, "Random Teleport", "I wanna go... there! Woops, wrong spot.");
        addSpellPart(ModSpellParts.ATTRACT, "Attract", "Come closer... or not, just stay where you are.");
        addSpellPart(ModSpellParts.TELEKINESIS, "Telekinesis", "Up, down, up, down, I think you get and idea.");
        addSpellPart(ModSpellParts.BLINK, "Blink", "Well, I'm out.");
        addSpellPart(ModSpellParts.RANGE, "Range", "Think you're far enough? No, you're not.");
        addSpellPart(ModSpellParts.CHANNEL, "Channel", "You might want to concentrate.");
        addSpellPart(ModSpellParts.RADIUS, "Radius", "I missed? Oh wait, this thing expands across a mile...");
        addSpellPart(ModSpellParts.TRANSPLACE, "Transplace", "From point A to point B.");
        addSpellPart(ModSpellParts.MARK, "Mark", "Home sweet home.");
        addSpellPart(ModSpellParts.RECALL, "Recall", "Can someone remind me who marked the trash?");
        addSpellPart(ModSpellParts.DIVINE_INTERVENTION, "Divine Intervention", "Help from across the cosmos.");
        addSpellPart(ModSpellParts.ENDER_INTERVENTION, "Ender Intervention", "Well, I didn't know what hell looked like...");
        //addSpellPart(ModSpellParts.CONTINGENCY_DEATH, "Contingency: Death", "You're coming with me.");

        addSkillPoint(ModSpellParts.SKILL_POINT_1, "Blue");
        addSkillPoint(ModSpellParts.SKILL_POINT_2, "Red");
        addSkillPoint(ModSpellParts.SKILL_POINT_3, "Green");

        add("itemGroup.arsmagicalegacy", "Ars Magica: Legacy");

        add("arsmagicalegacy.spell.manacost", "Mana Cost: %d");
        add("arsmagicalegacy.altar.lowpower", "The Altar Powerlevel is too low!");

        add("arsmagicalegacy.gui.inscriptiontable.search", "Search");
        add("arsmagicalegacy.gui.inscriptiontable.name", "Name");

        add("arsmagicalegacy.occulus.prevent", "Mythical forces prevent you from using this device!");
    }

    @Override
    public void add(String key, String value) {
        super.add(key, value);
    }
}
