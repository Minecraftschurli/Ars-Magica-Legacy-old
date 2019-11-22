package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.spell.ISpell;
import minecraftschurli.arsmagicalegacy.capabilities.spell.TestSpell;
import minecraftschurli.arsmagicalegacy.event.ModifierCalculatedEvent;
import minecraftschurli.arsmagicalegacy.init.ModRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public final class SpellUtils {
    private static final String SHAPE_PREFIX = "shape_ordinal_";
    private static final String COMPONENT_PREFIX = "spell_component_ids_";
    private static final String MODIFIER_PREFIX = "spell_modifier_ids_";

    private static final String SHAPE_META_PREFIX = "spell_shape_meta_";
    private static final String COMPONENT_META_PREFIX = "spell_component_meta_";
    private static final String MODIFIER_META_PREFIX = "spell_modifier_meta_";
    private static final String STAGES_IDENTIFIER = "num_stages";

    private static final String GLOBAL_SPELL_META = "spellMetadata";

    private static final String BASE_MANA_COST_IDENTIFIER = "BMC_";
    private static final String BASE_BURNOUT_IDENTIFIER = "BB_";

    private static final String BASE_REAGENTS_IDENTIFIER = "BRR";
    //private static final String ForcedAffinity = "ForcedAffinity";

    public static BlockPos rayTrace(World world, PlayerEntity player, double rayTraceRange) {
        Vec3d look = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d end = new Vec3d(player.posX + look.x * rayTraceRange, player.posY + player.getEyeHeight() + look.y * rayTraceRange, player.posZ + look.z * rayTraceRange);
        RayTraceContext ctx = new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player);
        BlockRayTraceResult result = world.rayTraceBlocks(ctx);
        return result.getPos().offset(result.getFace());
    }

    public static LazyOptional<ISpell> getSpell(ItemStack stack) {
        //TODO Make Spell system
        return LazyOptional.of(TestSpell::new).cast();
    }

    //==============================================================================
    // API Satisfaction
    //==============================================================================

    public static double getModifiedDoubleMul(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifier.Type check){
        return getModifiedDoubleMul(defaultValue, stack, caster, target, world, 0, check);
    }

    public static int getModifiedIntMul(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifier.Type check){
        return getModifiedIntMul(defaultValue, stack, caster, target, world, 0, check);
    }

    public static double getModifiedDoubleMul(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world){
        return getModifiedDoubleMul(check, stack, caster, target, world, 0);
    }

    public static int getModifiedIntMul(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world){
        return getModifiedIntMul(check, stack, caster, target, world, 0);
    }

    public static double getModifiedDoubleAdd(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifier.Type check){
        return getModifiedDoubleAdd(defaultValue, stack, caster, target, world, 0, check);
    }

    public static int getModifiedIntAdd(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifier.Type check){
        return getModifiedIntAdd(defaultValue, stack, caster, target, world, 0, check);
    }

    public static double getModifiedDoubleAdd(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world){
        return getModifiedDoubleAdd(check, stack, caster, target, world, 0);
    }

    public static int getModifiedIntAdd(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world){
        return getModifiedIntAdd(check, stack, caster, target, world, 0);
    }

    public static boolean modifierIsPresent(SpellModifier.Type check, ItemStack stack){
        return modifierIsPresent(check, stack, 0);
    }

    public static int countModifiers(SpellModifier.Type check, ItemStack stack){
        return countModifiers(check, stack, 0);
    }

    //==============================================================================
    // Modifiers
    //==============================================================================
    public static double getModifiedDoubleMul(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifier.Type check){
        int ordinalCount = 0;
        double modifiedValue = defaultValue;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue *= modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.MULTIPLY);
        MinecraftForge.EVENT_BUS.post(event);

        return event.modifiedValue;
    }

    public static int getModifiedIntMul(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifier.Type check){
        int ordinalCount = 0;
        int modifiedValue = defaultValue;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue *= modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.MULTIPLY);
        MinecraftForge.EVENT_BUS.post(event);

        return (int)event.modifiedValue;
    }

    public static double getModifiedDoubleMul(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world, int stage){
        int ordinalCount = 0;
        double modifiedValue = check.defaultValue;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue *= modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, check.defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.MULTIPLY);
        MinecraftForge.EVENT_BUS.post(event);

        return event.modifiedValue;
    }

    public static int getModifiedIntMul(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world, int stage){
        int ordinalCount = 0;
        int modifiedValue = check.defaultValueInt;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue *= modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, check.defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.MULTIPLY);
        MinecraftForge.EVENT_BUS.post(event);

        return (int)event.modifiedValue;
    }

    public static double getModifiedDoubleAdd(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifier.Type check){
        int ordinalCount = 0;
        double modifiedValue = defaultValue;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue += modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.ADD);
        MinecraftForge.EVENT_BUS.post(event);

        return event.modifiedValue;
    }

    public static int getModifiedIntAdd(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifier.Type check){
        int ordinalCount = 0;

        double modifiedValue = defaultValue;

        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue += modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.ADD);
        MinecraftForge.EVENT_BUS.post(event);

        return (int)Math.ceil(event.modifiedValue);
    }

    public static double getModifiedDoubleAdd(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world, int stage){
        int ordinalCount = 0;
        double modifiedValue = check.defaultValue;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue += modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, check.defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.ADD);
        MinecraftForge.EVENT_BUS.post(event);

        return event.modifiedValue;
    }

    public static int getModifiedIntAdd(SpellModifier.Type check, ItemStack stack, LivingEntity caster, Entity target, World world, int stage){
        int ordinalCount = 0;
        int modifiedValue = check.defaultValueInt;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                byte[] meta = getModifierMetadataFromStack(stack, modifier, stage, ordinalCount++);
                modifiedValue += modifier.getModifier(check, caster, target, world, meta);
            }
        }

        if (caster instanceof PlayerEntity){
            if (caster.getCapability(CapabilityResearch.SKILL_TREE).map(iSkillTreeStorage -> iSkillTreeStorage.hasLearned(new ResourceLocation(ArsMagicaLegacy.MODID,"augmented_casting"))).orElse(false)){
                modifiedValue *= 1.1f;
            }
        }

        ModifierCalculatedEvent event = new ModifierCalculatedEvent(stack, caster, check, check.defaultValue, modifiedValue, ModifierCalculatedEvent.OperationType.ADD);
        MinecraftForge.EVENT_BUS.post(event);

        return (int)event.modifiedValue;
    }

    public static boolean modifierIsPresent(SpellModifier.Type check, ItemStack stack, int stage){
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                return true;
            }
        }
        return false;
    }

    public static int countModifiers(SpellModifier.Type check, ItemStack stack, int stage){
        int count = 0;
        for (SpellModifier modifier : getModifiersForStage(stack, stage)){
            if (modifier.getAspectsModified().contains(check)){
                count++;
            }
        }
        return count;
    }
    //==============================================================================
    // End Modifiers
    //==============================================================================

    public static SpellModifier[] getModifiersForStage(ItemStack stack, int stage){
        if (stack == null || !stack.hasTag())
            return new SpellModifier[0];

        return stack.getTag()
                .getList(MODIFIER_PREFIX + stage, Constants.NBT.TAG_STRING)
                .stream()
                .map(inbt -> (StringNBT) inbt)
                .map(StringNBT::getString)
                .map(ResourceLocation::new)
                .map(ModRegistries.SPELL_MODIFIERS::getValue)
                .toArray(SpellModifier[]::new);
    }

    public static byte[] getModifierMetadataFromStack(ItemStack stack, SpellModifier modifier, int stage, int ordinal){
        if (!stack.hasTag()) return new byte[0];
        String identifier = String.format("%s%s_%d_%d", MODIFIER_META_PREFIX, modifier.getRegistryName(), stage, ordinal);
        return stack.getTag().getByteArray(identifier);
    }

    public static int numStages(ItemStack stack){
        if (stack == null || !stack.hasTag())
            return 0;
        return stack.getTag().hasUniqueId(STAGES_IDENTIFIER) ? stack.getTag().getInt(STAGES_IDENTIFIER) : stack.getTag().getInt(SHAPE_PREFIX);
    }

    public static SpellShape getShapeForStage(ItemStack stack, int stage){
        if (stack == null || !stack.hasTag()) return null;
        String shapeIndex = stack.getTag().getString(SHAPE_PREFIX + stage);
        if (shapeIndex.isEmpty())
            return null;

        return ModRegistries.SPELL_SHAPES.getValue(new ResourceLocation(shapeIndex));
    }

    public static SpellComponent[] getComponentsForStage(ItemStack stack, int stage){
        if (stack == null || !stack.hasTag())
            return new SpellComponent[0];

        return stack.getTag().getList(COMPONENT_PREFIX + stage, Constants.NBT.TAG_STRING)
                .stream()
                .map(inbt -> (StringNBT)inbt)
                .map(StringNBT::getString)
                .map(ResourceLocation::new)
                .map(ModRegistries.SPELL_COMPONENTS::getValue)
                .toArray(SpellComponent[]::new);
    }

    public static ItemStack popStackStage(ItemStack stack){
        if (!stack.hasTag())
            return stack;

        ItemStack workingStack = stack.copy();
        int stages = numStages(workingStack);
        if (stages == 0) return workingStack;
        for (int i = 1; i < stages; ++i){
            workingStack.getTag().putIntArray(COMPONENT_PREFIX + (i - 1), workingStack.getTag().getIntArray(COMPONENT_PREFIX + i));
            workingStack.getTag().putIntArray(MODIFIER_PREFIX + (i - 1), workingStack.getTag().getIntArray(MODIFIER_PREFIX + i));
            workingStack.getTag().putInt(SHAPE_PREFIX + (i - 1), workingStack.getTag().getInt(SHAPE_PREFIX + i));
        }
        workingStack.getTag().putInt(STAGES_IDENTIFIER, stages - 1);

        workingStack.getTag().remove(COMPONENT_PREFIX + (stages - 1));
        workingStack.getTag().remove(MODIFIER_PREFIX + (stages - 1));
        workingStack.getTag().remove(SHAPE_PREFIX + (stages - 1));

        return workingStack;
    }

    public static SpellRequirements getSpellRequirements(ItemStack stack, LivingEntity caster){
        if (!spellRequirementsPresent(stack, caster)){
            writeSpellRequirements(stack, caster, calculateSpellRequirements(stack, caster));
        }

        return modifySpellRequirementsByAffinity(stack, caster, parseSpellRequirements(stack, caster));
    }

    public static SpellRequirements modifySpellRequirementsByAffinity(ItemStack stack, LivingEntity caster, SpellRequirements reqs){
        /*HashMap<Affinity, Float> affinities = this.AffinityFor(stack);
        AffinityData affData = AffinityData.For(caster);

        if (affData == null)*/
            return reqs;

        /*float manaCost = reqs.manaCost;

        *//*for (Affinity aff : affinities.keySet()){
            float depth = affData.getAffinityDepth(aff);
            //calculate the modifier based on the player's affinity depth as well as how much of the spell is that affinity
            float effectiveness = affinities.get(aff); //how much does this affinity affect the overall mana cost?
            float multiplier = 0.5f * depth; //how much will the player's affinity reduce the mana cost? (max 50%)

            float manaMod = manaCost * effectiveness;
            manaCost -= manaMod * multiplier;
        }*//*

        return new SpellRequirements(manaCost, reqs.burnout, reqs.reagents);*/
    }

    private static SpellRequirements calculateSpellRequirements(ItemStack stack, LivingEntity caster){
        float manaCost = 0;
        float burnout = 0;
        ArrayList<ItemStack> reagents = new ArrayList<ItemStack>();
        int stages = numStages(stack);

        for (int i = stages - 1; i >= 0; --i){
            float stageManaCost = 0;
            float stageBurnout = 0;

            SpellShape shape = getShapeForStage(stack, i);
            SpellComponent[] components = getComponentsForStage(stack, i);
            SpellModifier[] modifiers = getModifiersForStage(stack, i);

            for (SpellComponent component : components){
                ItemStack[] componentReagents = component.getReagents(caster);
                if (componentReagents != null)
                    for (ItemStack reagentStack : componentReagents) reagents.add(reagentStack);
                stageManaCost += component.getManaCost(caster);
                stageBurnout += component.getBurnout(caster);
            }

            HashMap<SpellModifier, Integer> modifierWithQuantity = new HashMap<SpellModifier, Integer>();
            for (SpellModifier modifier : modifiers){
                if (modifierWithQuantity.containsKey(modifier)){
                    Integer qty = modifierWithQuantity.get(modifier);
                    if (qty == null) qty = 1;
                    qty++;
                    modifierWithQuantity.put(modifier, qty);
                }else{
                    modifierWithQuantity.put(modifier, 1);
                }
            }

            for (SpellModifier modifier : modifierWithQuantity.keySet()){
                stageManaCost *= modifier.getManaCostMultiplier(stack, i, modifierWithQuantity.get(modifier));
            }

            manaCost += (stageManaCost * shape.getManaCostMultiplier(stack));
            burnout += stageBurnout;
        }

        return new SpellRequirements(manaCost, burnout, reagents);
    }

    private static SpellRequirements parseSpellRequirements(ItemStack stack, LivingEntity caster){
        float burnoutPct = (MagicHelper.getBurnout(caster) / MagicHelper.getMaxBurnout(caster)) + 1f;

        float manaCost = stack.getTag().getFloat(BASE_MANA_COST_IDENTIFIER) * burnoutPct;
        float burnout = stack.getTag().getFloat(BASE_BURNOUT_IDENTIFIER);

        ListNBT reagentList = stack.getTag().getList(BASE_REAGENTS_IDENTIFIER, Constants.NBT.TAG_COMPOUND);
        ArrayList<ItemStack> reagents = new ArrayList<ItemStack>();
        for (net.minecraft.nbt.INBT inbt : reagentList) {
            CompoundNBT entry = (CompoundNBT) inbt;
            reagents.add(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getString("name"))), entry.getInt("amount")));
        }

        return new SpellRequirements(manaCost, burnout, reagents);
    }

    private static void writeSpellRequirements(ItemStack stack, LivingEntity caster, SpellRequirements requirements){
        if (!stack.hasTag()) return;

        stack.getTag().putFloat(BASE_MANA_COST_IDENTIFIER, requirements.manaCost);
        stack.getTag().putFloat(BASE_BURNOUT_IDENTIFIER, requirements.burnout);

        ListNBT reagentList = new ListNBT();
        for (ItemStack reagentStack : requirements.reagents){
            CompoundNBT reagent = new CompoundNBT();
            reagent.putString("name", reagentStack.getItem().getRegistryName().toString());
            reagent.putInt("amount", reagentStack.getCount());
            reagentList.add(reagent);
        }

        stack.getTag().put(BASE_REAGENTS_IDENTIFIER, reagentList);
        writeModVersionToStack(stack);
    }

    private static boolean spellRequirementsPresent(ItemStack stack, LivingEntity caster){
        if (!stack.hasTag()) return false;
        if (isOldVersionSpell(stack)) return false;
        return stack.getTag().hasUniqueId(BASE_MANA_COST_IDENTIFIER) && stack.getTag().hasUniqueId(BASE_BURNOUT_IDENTIFIER) && stack.getTag().hasUniqueId(BASE_REAGENTS_IDENTIFIER);
    }

    public static void writeModVersionToStack(ItemStack stack){
        if (!stack.hasTag()) return;
        stack.getTag().putString("spell_mod_version", ArsMagicaLegacy.instance.getVersion());
    }

    public static boolean isOldVersionSpell(ItemStack stack){
        if (!stack.hasTag()) return false;
        String version = stack.getTag().getString("spell_mod_version");
        return !version.equals(ArsMagicaLegacy.instance.getVersion());
    }
}
