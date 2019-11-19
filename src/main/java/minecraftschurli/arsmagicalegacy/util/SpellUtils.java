package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.mana.IManaStorage;
import minecraftschurli.arsmagicalegacy.capabilities.research.CapabilityResearch;
import minecraftschurli.arsmagicalegacy.capabilities.spell.ISpell;
import minecraftschurli.arsmagicalegacy.capabilities.spell.TestSpell;
import minecraftschurli.arsmagicalegacy.event.ModifierCalculatedEvent;
import minecraftschurli.arsmagicalegacy.init.ModRegistries;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.network.SyncBurnout;
import minecraftschurli.arsmagicalegacy.network.SyncMana;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;

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

    public static boolean use(PlayerEntity player, int manaCost, int burnoutCost) {
        if (player instanceof ServerPlayerEntity){
            LazyOptional<IManaStorage> manaStorage = player.getCapability(CapabilityMana.MANA);
            LazyOptional<IBurnoutStorage> burnoutStorage = player.getCapability(CapabilityBurnout.BURNOUT);
            if (manaStorage.map(IManaStorage::getMana).orElse(0.0f) >= manaCost && burnoutStorage.map(iBurnoutStorage -> iBurnoutStorage.getMaxBurnout() - iBurnoutStorage.getBurnout()).orElse(0.0f) >= burnoutCost) {
                manaStorage.ifPresent(iManaStorage -> iManaStorage.decrease(manaCost));
                burnoutStorage.ifPresent(iBurnoutStorage -> iBurnoutStorage.increase(burnoutCost));
                syncMana((ServerPlayerEntity)player);
                syncBurnout((ServerPlayerEntity) player);
                return true;
            }
        }
        return false;
    }

    public static void syncMana(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncMana(
                    iManaStorage.getMana(),
                    iManaStorage.getMaxMana()
                )
        ));
    }

    public static void syncBurnout(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBurnout(
                        iBurnoutStorage.getBurnout(),
                        iBurnoutStorage.getMaxBurnout()
                )
        ));
    }

    public static void regenMana(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> {
                iManaStorage.increase(amount);
            });
            syncMana((ServerPlayerEntity) player);
        }
    }

    public static void regenBurnout(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> {
                iBurnoutStorage.decrease(amount);
            });
            syncBurnout((ServerPlayerEntity) player);
        }
    }

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

        /*int[] modifierIDs = stack.getTag().getIntArray(MODIFIER_PREFIX + stage);
        SpellModifier.Type[] modifiers = new SpellModifier.Type[modifierIDs.length];
        int count = 0;
        for (int i : modifierIDs){
            SkillTreeEntry modifier = SkillManager.getSkill(i);
            *//*if (SkillTreeManager.instance.isSkillDisabled(modifier)){
                modifiers[count++] = SkillManager.missingModifier;
                continue;
            }*//*
            modifiers[count++] = (SpellModifier)modifier;
        }
        return modifiers;*/
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
        /*int[] componentIDs = stack.stackTagCompound.getIntArray();
        ISpellComponent[] components = new ISpellComponent[componentIDs.length];
        int count = 0;
        for (int i : componentIDs){
            ISkillTreeEntry component = SkillManager.instance.getSkill(i);
            if (SkillTreeManager.instance.isSkillDisabled(component)){
                components[count++] = SkillManager.instance.missingComponent;
                continue;
            }
            components[count++] = component != null && component instanceof ISpellComponent ? (ISpellComponent)component : SkillManager.instance.missingComponent;
        }
        return components;*/
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
}
