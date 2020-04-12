package minecraftschurli.arsmagicalegacy.util;

import javafx.util.Pair;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.spell.AbstractSpellPart;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.util.NBTUtil;
import minecraftschurli.arsmagicalegacy.init.ModEffects;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.MissingShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class SpellUtil {
    public static final String TYPE_SHAPE = "Shape";
    public static final String TYPE_COMPONENT = "Component";
    public static final String TYPE_MODIFIER = "Modifier";
    public static final String TYPE = "Type";
    public static final String ID = "ID";
    public static final String SHAPE_GROUP = "ShapeGroup";
    public static final String STAGE = "Stage";
    public static final String SPELL_DATA = "SpellData";

    public static Map<Affinity, Float> affinityFor(ItemStack stack) {
        Map<Affinity, Float> depth = new HashMap<>();
        List<SpellComponent> components = SpellUtil.getComponents(stack, -1);
        for (SpellComponent component : components) {
            for (Affinity aff1 : component.getAffinity()) {
                if (depth.get(aff1) != null)
                    depth.put(aff1, depth.get(aff1) + component.getAffinityShift(aff1));
                else depth.put(aff1, component.getAffinityShift(aff1));
            }
        }
        return depth;
    }

    public static SpellCastResult applyStage(ItemStack stack, LivingEntity caster, LivingEntity target, double x, double y, double z, @Nullable Direction side, World world, boolean consumeMBR, boolean giveXP, int ticksUsed) {
        if (caster.isPotionActive(ModEffects.SILENCE.get()))
            return SpellCastResult.SILENCED;
        int group = NBTUtil.getAMLTag(stack.getTag()).getInt("CurrentGroup");
        if (group == 0) stack = merge(stack.copy());
        SpellShape shape = getShape(stack, group);
        if (!(caster instanceof PlayerEntity))
            return SpellCastResult.EFFECT_FAILED;
        if (shape instanceof MissingShape) return SpellCastResult.MALFORMED_SPELL_STACK;
        SpellCastEvent.Pre pre = new SpellCastEvent.Pre(stack, stack.getItem(), caster, getMana(stack, caster), getBurnout(stack, caster), shape == ModSpellParts.CHANNEL.get());
        MinecraftForge.EVENT_BUS.post(pre);
        float manaCost = pre.manaCost;
        float burnoutCost = pre.burnout;
        SpellCastResult result = null;
        if (consumeMBR && !((PlayerEntity) caster).isCreative()) {
            if (!CapabilityHelper.hasEnoughtMana(caster, manaCost)) result = SpellCastResult.NOT_ENOUGH_MANA;
            else if (CapabilityHelper.isBurnedOut(caster, burnoutCost)) result = SpellCastResult.BURNED_OUT;
            else if (!hasReagents(caster, stack)) {
                if (world.isRemote)
                    caster.sendMessage(new StringTextComponent(missingReagents(caster, stack)));
                result = SpellCastResult.REAGENTS_MISSING;
            }
        }
        if (result == null) {
            NBTUtil.getAMLTag(stack.getTag()).putInt("CurrentGroup", group + 1);
            if (group != 0) NBTUtil.getAMLTag(stack.getTag()).putInt("CurrentGroup", group + 1);
            result = shape.beginStackStage(stack.getItem(), stack, caster, target, world, x, y, z, side, giveXP, ticksUsed);
        }
        SpellCastEvent.Post post = new SpellCastEvent.Post(stack, stack.getItem(), caster, manaCost, burnoutCost, shape == ModSpellParts.CHANNEL.get(), result);
        MinecraftForge.EVENT_BUS.post(post);
        manaCost = post.manaCost;
        burnoutCost = post.burnout;
        result = post.castResult;
        if (consumeMBR && !((PlayerEntity) caster).isCreative()) {
            if (result == SpellCastResult.SUCCESS || result == SpellCastResult.SUCCESS_REDUCE_MANA || result == SpellCastResult.MALFORMED_SPELL_STACK) {
                CapabilityHelper.use(caster, manaCost, burnoutCost);
                consumeReagents(caster, stack);
            }
        }
        return result;
    }

    public static SpellCastResult applyStageBlock(ItemStack stack, LivingEntity caster, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, boolean consumeMBR) {
        SpellShape stageShape = SpellUtil.getShape(stack, 0);
        if (stageShape == null || Objects.equals(stageShape.getRegistryName(), SpellRegistry.MISSING_SHAPE))
            return SpellCastResult.MALFORMED_SPELL_STACK;
        boolean isPlayer = caster instanceof PlayerEntity;
        int group = NBTUtil.getAMLTag(stack.getTag()).getInt("CurrentGroup");
        List<SpellComponent> components = SpellUtil.getComponents(stack, group);
        for (SpellComponent component : components) {
            if (component.applyEffectBlock(stack, world, pos, blockFace, impactX, impactY, impactZ, caster)) {
                if (isPlayer && !world.isRemote && component.getAffinity() != null)
                    CapabilityHelper.doAffinityShift(caster, component, stageShape);
                if (world.isRemote) {
                    int color = -1;
                    if (hasModifier(SpellModifiers.COLOR, stack)) {
                        List<SpellModifier> mods = SpellUtil.getModifiers(stack, -1);
                        for (SpellModifier mod : mods)
                            if (mod instanceof Color)
                                color = (int) mod.getModifier(SpellModifiers.COLOR, caster, null, world, NBTUtil.getAMLTag(stack.getTag()));
                    }
                    component.spawnParticles(world, pos.getX(), pos.getY(), pos.getZ(), caster, caster, world.rand, color);
                }
            }
        }
        return SpellCastResult.SUCCESS;
    }

    public static SpellCastResult applyStageEntity(ItemStack stack, LivingEntity caster, World world, Entity target, boolean shiftAffinityAndXP) {
        SpellShape stageShape = SpellUtil.getShape(stack, 0);
        if (stageShape == null) return SpellCastResult.MALFORMED_SPELL_STACK;
        if (target instanceof ServerPlayerEntity && ((ServerPlayerEntity) target).abilities.isCreativeMode)
            return SpellCastResult.EFFECT_FAILED;
        int group = NBTUtil.getAMLTag(stack.getTag()).getInt("CurrentGroup");
        List<SpellComponent> components = SpellUtil.getComponents(stack, group);
        boolean appliedOneComponent = false;
        for (SpellComponent component : components) {
            if (caster instanceof PlayerEntity && CapabilityHelper.getLearnedSkills((PlayerEntity) caster).contains(component))
                continue;
            if (component.applyEffectEntity(stack, world, caster, target)) {
                if (caster instanceof PlayerEntity && !world.isRemote && component.getAffinity() != null)
                    CapabilityHelper.doAffinityShift(caster, component, stageShape);
                appliedOneComponent = true;
                if (world.isRemote) {
                    int color = -1;
                    if (SpellUtil.hasModifier(SpellModifiers.COLOR, stack)) {
                        List<SpellModifier> mods = SpellUtil.getModifiers(stack, -1);
                        for (SpellModifier mod : mods)
                            if (mod instanceof Color)
                                color = (int) mod.getModifier(SpellModifiers.COLOR, caster, target, world, NBTUtil.getAMLTag(stack.getTag()));
                    }
                    component.spawnParticles(world, target.getPositionVec().x, target.getPositionVec().y + target.getEyeHeight(), target.getPositionVec().z, caster, target, world.rand, color);
                }
                if (caster instanceof PlayerEntity) CapabilityHelper.doAffinityShift(caster, component, stageShape);
            }
        }
        if (appliedOneComponent)
            return SpellCastResult.SUCCESS;
        else
            return SpellCastResult.EFFECT_FAILED;
    }

    public static SpellCastResult applyUsingStage(ItemStack stack, LivingEntity caster, LivingEntity target, double x, double y, double z, World world, boolean consumeMBR, boolean giveXP, int ticks) {
        if (SpellUtil.stageNum(stack) == 0) return SpellCastResult.SUCCESS;
        if (!SpellUtil.getShape(stack).isChanneled()) return SpellCastResult.EFFECT_FAILED;
        return applyStage(stack, caster, target, x, y, z, null, world, consumeMBR, giveXP, ticks);
    }

    public static boolean attackWithType(ItemStack spellStack, Entity target, DamageSource damagesource, float magnitude) {
        if (target.world.isRemote) return true;
//        PlayerEntity dmgSrcPlayer;
        if (damagesource.getTrueSource() != null) {
            if (damagesource.getTrueSource() instanceof LivingEntity) {
                LivingEntity source = (LivingEntity) damagesource.getTrueSource();
                if (target.getClass() == CreeperEntity.class) return false;
                else if (source instanceof PlayerEntity && target instanceof PlayerEntity && !target.world.isRemote && (!((ServerPlayerEntity) target).server.isPVPEnabled() || ((PlayerEntity) target).isCreative()))
                    return false;
                if (source.isPotionActive(ModEffects.FURY.get())) magnitude += 4;
            }
//            if (damagesource.getTrueSource() instanceof PlayerEntity) {
//                dmgSrcPlayer = (PlayerEntity)damagesource.getTrueSource();
//                int armorSet = ArmorHelper.getFullArsMagicaArmorSet(dmgSrcPlayer);
//                if (armorSet == ArsMagicaArmorMaterial.MAGE.getMaterialID()) magnitude *= 1.05f;
//                else if (armorSet == ArsMagicaArmorMaterial.BATTLEMAGE.getMaterialID()) magnitude *= 1.025f;
//                else if (armorSet == ArsMagicaArmorMaterial.ARCHMAGE.getMaterialID()) magnitude *= 1.1f;
//                ItemStack equipped = dmgSrcPlayer.getActiveItemStack();
//                if (equipped != null && equipped.getItem() == ItemDefs.arcaneSpellbook) magnitude *= 1.1f;
//            }
        }
        if (target instanceof LivingEntity && SummonUtil.isSummon((LivingEntity) target) && damagesource.damageType.equals("magic"))
            magnitude *= 3;
        return target.attackEntityFrom(damagesource, magnitude);
    }

    public static double calculate(int operation, double val, double mod) {
        if (operation == 0) return val + mod;
        if (operation == 1) return val - mod;
        if (operation == 2) return val * mod;
        if (operation == 3) return val / mod;
        return 0;
    }

    public static void changeEnchantmentsForGroup(ItemStack stack) {
//        ItemStack constructed = merge(stack);
//        int looting = 0;
//        int silkTouch = 0;
//        for (int i = 0; i < numStages(constructed); i++) {
//            looting += countModifiers(SpellModifiers.FORTUNE_LEVEL, constructed);
//            silkTouch += countModifiers(SpellModifiers.SILKTOUCH_LEVEL, constructed);
//        }
//        AMEnchantmentHelper.fortuneStack(stack, looting);
//        AMEnchantmentHelper.lootingStack(stack, looting);
//        AMEnchantmentHelper.silkTouchStack(stack, silkTouch);
    }

    public static void consumeReagents(LivingEntity caster, ItemStack spellStack) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            if (player.isCreative()) return;
            for (SpellComponent part : getComponents(spellStack, -1)) {
                if (part.getReagents(caster) == null) continue;
                for (ItemStack stack : part.getReagents(caster)) {
                    if (stack != null) {
                        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                            ItemStack is = player.inventory.getStackInSlot(i);
                            if (is.isEmpty()) continue;
                            if (is.getItem() == stack.getItem()) {
                                if (is.getCount() >= stack.getCount()) {
                                    is.setCount(is.getCount() - stack.getCount());
                                    if (is.getCount() <= 0)
                                        player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                                    else player.inventory.setInventorySlotContents(i, is);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static int countModifiers(SpellModifiers mod, ItemStack stack) {
        List<SpellModifier> mods = getModifiers(stack, -1);
        int i = 0;
        for (SpellModifier m : mods) if (m.getAspectsModified().contains(mod)) i++;
        return i;
    }

    public static int currentStage(ItemStack spellStack) {
        return NBTUtil.getAMLTag(spellStack.getTag()).getInt("CurrentGroup");
    }

    public static int cycleShapeGroup(ItemStack stack) {
        if (!stack.hasTag())
            return 0;
        int current = NBTUtil.getAMLTag(stack.getTag()).getInt("CurrentShapeGroup");
        int max = NBTUtil.getAMLTag(stack.getTag()).getInt("NumShapeGroups");
        if (max == 0)
            return 0;
        return (current + 1) % max;
    }

    public static Pair<ArrayList<AbstractSpellPart>, CompoundNBT> decode(CompoundNBT toDecode) {
        if (toDecode == null) return null;
        try {
            ArrayList<AbstractSpellPart> parts = new ArrayList<>();
            for (int j = 0; j < NBTUtil.getAMLTag(toDecode).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(toDecode), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tmp = stageTag.getCompound(i);
                    String type = tmp.getString(TYPE);
                    if (type.equalsIgnoreCase(TYPE_COMPONENT))
                        parts.add(SpellRegistry.getComponentFromName(tmp.getString(ID)));
                    if (type.equalsIgnoreCase(TYPE_MODIFIER))
                        parts.add(SpellRegistry.getModifierFromName(tmp.getString(ID)));
                    if (type.equalsIgnoreCase(TYPE_SHAPE)) parts.add(SpellRegistry.getShapeFromName(tmp.getString(ID)));
                }
            }
            return new Pair<>(parts, toDecode.getCompound(SPELL_DATA));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean doBlockWithEntity(SpellComponent component, ItemStack stack, World world, LivingEntity caster, Entity target) {
        return component.applyEffectBlock(stack, world, target.getPosition(), null, target.getPosX(), target.getPosY(), target.getPosZ(), caster);
    }

    public static CompoundNBT encode(Pair<List<AbstractSpellPart>, CompoundNBT> toEncode) {
        CompoundNBT group = new CompoundNBT();
        group.put(SPELL_DATA, toEncode.getValue());
        int stage = 0;
        for (AbstractSpellPart part : toEncode.getKey()) {
            ListNBT stageTag = NBTUtil.addCompoundList(group, STAGE + stage);
            CompoundNBT tmp = new CompoundNBT();
            String id = SpellRegistry.getSkillFromPart(part).getID();
            tmp.putString(ID, id);
            String type = "";
            if (part instanceof SpellShape) type = TYPE_SHAPE;
            if (part instanceof SpellModifier) type = TYPE_MODIFIER;
            if (part instanceof SpellComponent) type = TYPE_COMPONENT;
            tmp.putString(TYPE, type);
            if (part instanceof SpellShape) stage++;
            stageTag.add(tmp);
        }
        group.putInt("StageNum", stage);
        return group;
    }

    public static float getBurnout(ItemStack stack, LivingEntity caster) {
        if (stack.getTag() == null)
            return 0;
        ItemStack mergedStack = merge(stack);
        try {
            float cost = 0;
            for (int j = 0; j < NBTUtil.getAMLTag(mergedStack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(mergedStack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tmp = stageTag.getCompound(i);
                    String type = tmp.getString(TYPE);
                    if (type.equalsIgnoreCase(TYPE_COMPONENT)) {
                        SpellComponent component = SpellRegistry.getComponentFromName(tmp.getString(ID));
                        if (component != null) cost += component.getBurnout(caster);
                    }
                }
            }
            return cost;
        } catch (Exception e) {
            return 0;
        }
    }

    public static float getMana(ItemStack stack, LivingEntity caster) {
        if (stack.getTag() == null)
            return 0;
        ItemStack mergedStack = merge(stack);
        Affinity[] affinities = null;
        if (caster instanceof PlayerEntity) affinities = CapabilityHelper.getHighestAffinities(caster);
        try {
            float cost = 0;
            float modMultiplier = 1;
            for (int j = 0; j < NBTUtil.getAMLTag(mergedStack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(mergedStack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tmp = stageTag.getCompound(i);
                    String type = tmp.getString(TYPE);
                    if (type.equalsIgnoreCase(TYPE_COMPONENT)) {
                        SpellComponent component = SpellRegistry.getComponentFromName(tmp.getString(ID));
                        if (component != null) cost += component.getManaCost(caster);
                        if (caster instanceof PlayerEntity) {
                            for (Affinity aff : affinities) {
                                for (Affinity aff2 : component.getAffinity()) {
                                    if (aff == aff2 && CapabilityHelper.getAffinityDepth(caster, aff) > 0) {
                                        cost = cost - (float) (cost * (0.5f * CapabilityHelper.getAffinityDepth(caster, aff)));
                                        break;
                                    } else cost = cost + (cost * (0.10f));
                                }
                            }
                        }
                    }
                    if (type.equalsIgnoreCase(TYPE_MODIFIER)) {
                        SpellModifier mod = SpellRegistry.getModifierFromName(tmp.getString(ID));
                        if (mod != null) modMultiplier *= mod.getManaCostMultiplier(mergedStack, j, 1);
                    }
                    if (type.equalsIgnoreCase(TYPE_SHAPE)) {
                        SpellShape shape = SpellRegistry.getShapeFromName(tmp.getString(ID));
                        if (shape != null) modMultiplier *= shape.manaCostMultiplier(mergedStack);
                    }
                }
            }
            cost *= modMultiplier;
            if (caster instanceof PlayerEntity) {
                if (CapabilityHelper.getAffinityDepth(caster, Affinity.ARCANE) > 0.5f) {
                    float reduction = (float) (1 - (0.5 * CapabilityHelper.getAffinityDepth(caster, Affinity.ARCANE)));
                    cost *= reduction;
                }
            }
            return cost;
        } catch (Exception e) {
            return 0;
        }
    }

    public static List<SpellComponent> getComponents(ItemStack stack, int stage) {
        try {
            List<SpellComponent> mods = new ArrayList<>();
            if (stage != -1) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + stage);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_COMPONENT))
                        mods.add(SpellRegistry.getComponentFromName(tag.getString(ID)));
                }
            } else {
                for (int j = 0; j <= NBTUtil.getAMLTag(stack.getTag()).getInt("StageNum"); j++) {
                    ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + j);
                    for (int i = 0; i < stageTag.size(); i++) {
                        CompoundNBT tag = stageTag.getCompound(i);
                        String tagType = tag.getString(TYPE);
                        if (tagType.equalsIgnoreCase(TYPE_COMPONENT))
                            mods.add(SpellRegistry.getComponentFromName(tag.getString(ID)));
                    }
                }
            }
            return mods;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static ArrayList<AbstractSpellPart> getGroupParts(ItemStack stack, int shapeGroup) {
        try {
            ArrayList<AbstractSpellPart> mods = new ArrayList<>();
            CompoundNBT tag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), "ShapeGroups").getCompound(shapeGroup);
            for (int j = 0; j <= tag.getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(tag, STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tmp = stageTag.getCompound(i);
                    mods.add(SpellRegistry.getComponentFromName(tmp.getString(ID)));
                }
            }
            return mods;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<SpellModifier> getModifiers(ItemStack stack, int stage) {
        List<SpellModifier> mods = new ArrayList<>();
        if (stack.getTag() == null)
            return mods;
        if (stage != -1) {
            ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + stage);
            for (int i = 0; i < stageTag.size(); i++) {
                CompoundNBT tag = stageTag.getCompound(i);
                String tagType = tag.getString(TYPE);
                if (tagType.equalsIgnoreCase(TYPE_MODIFIER))
                    mods.add(SpellRegistry.getModifierFromName(tag.getString(ID)));
            }
        } else {
            for (int j = 0; j <= NBTUtil.getAMLTag(stack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_MODIFIER))
                        mods.add(SpellRegistry.getModifierFromName(tag.getString(ID)));
                }
            }
        }
        return mods;
    }

    public static ArrayList<AbstractSpellPart> getParts(ItemStack stack) {
        try {
            ArrayList<AbstractSpellPart> mods = new ArrayList<>();
            for (int j = 0; j <= NBTUtil.getAMLTag(stack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    mods.add(RegistryHandler.getSpellPartRegistry().getValue(new ResourceLocation(tag.getString(ID))));
                }
            }
            return mods;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static List<AbstractSpellPart> getParts(ItemStack stack, int group) {
        List<AbstractSpellPart> mods = new ArrayList<>();
        try {
            CompoundNBT compound = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), "ShapeGroups").getCompound(NBTUtil.getAMLTag(stack.getTag()).getInt("CurrentShapeGroup")).copy();
            for (int j = 0; j <= compound.getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(compound, STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_MODIFIER))
                        mods.add(SpellRegistry.getModifierFromName(tag.getString(ID)));
                    if (tagType.equalsIgnoreCase(TYPE_SHAPE))
                        mods.add(SpellRegistry.getShapeFromName(tag.getString(ID)));
                }
            }
            return mods;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static SpellShape getShape(ItemStack is, int stage) {
        if (is == null || !is.hasTag())
            return (SpellShape) RegistryHandler.getSpellPartRegistry().getValue(SpellRegistry.MISSING_SHAPE);
        ItemStack stack = merge(is.copy());
        CompoundNBT am2Tag = NBTUtil.getAMLTag(stack.getTag());
        ListNBT stageTag = NBTUtil.addCompoundList(am2Tag, STAGE + stage);
        String shapeName = "null";
        for (int i = 0; i < stageTag.size(); i++)
            if (stageTag.getCompound(i).getString(TYPE).equals(TYPE_SHAPE)) {
                shapeName = stageTag.getCompound(i).getString(ID);
                break;
            }
        return SpellRegistry.getShapeFromName(shapeName) != null ? SpellRegistry.getShapeFromName(shapeName) : (SpellShape) RegistryHandler.getSpellPartRegistry().getValue(SpellRegistry.MISSING_SHAPE);
    }

    public static SpellShape getShape(ItemStack stack) {
        return getShape(stack, currentStage(stack));
    }

    public static String getSpellTag(ItemStack stack, String string) {
        return NBTUtil.addTag(NBTUtil.getAMLTag(stack.getTag()), SPELL_DATA).getString(string);
    }

    public static boolean hasComponent(ItemStack stack, Class<? extends SpellComponent> clazz) {
        for (SpellComponent comp : getComponents(stack, currentStage(stack))) if (clazz.isInstance(comp)) return true;
        return false;
    }

    public static boolean hasModifier(SpellModifiers mod, ItemStack stack) {
        List<SpellModifier> mods = getModifiers(stack, -1);
        if (mods.isEmpty()) return false;
        for (SpellModifier m : mods) if (m.getAspectsModified().contains(mod)) return true;
        return false;
    }

    public static boolean hasReagents(LivingEntity caster, ItemStack spellStack) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            if (player.isCreative()) return true;
            for (SpellComponent part : getComponents(spellStack, -1)) {
                if (part.getReagents(caster) == null) continue;
                for (ItemStack stack : part.getReagents(caster)) {
                    if (stack != null) {
                        boolean foundMatch = false;
                        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                            ItemStack is = player.inventory.getStackInSlot(i);
                            if (is.isEmpty()) continue;
                            if (is.getItem() == stack.getItem()) {
                                if (is.getCount() >= stack.getCount()) {
                                    foundMatch = true;
                                    break;
                                }
                            }
                        }
                        if (!foundMatch) return false;
                    }
                }
            }
        }
        return true;
    }

    public static ItemStack makeSpellStack(List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroups, List<AbstractSpellPart> spellDef, CompoundNBT encodedData) {
        ItemStack stack = new ItemStack(ModItems.SPELL.get());
        CompoundNBT tag = new CompoundNBT();
        CompoundNBT aml = NBTUtil.getAMLTag(tag);
        aml.put(SPELL_DATA, encodedData);
        ListNBT shapeGroupList = NBTUtil.addCompoundList(aml, "ShapeGroups");
        for (Pair<List<AbstractSpellPart>, CompoundNBT> shapeGroup : shapeGroups) {
            if (shapeGroup.getKey().isEmpty()) continue;
            CompoundNBT group = new CompoundNBT();
            group.put(SPELL_DATA, shapeGroup.getValue());
            int stage = 0;
            boolean lastWasShape = false;
            for (AbstractSpellPart part : shapeGroup.getKey()) {
                ArsMagicaLegacy.LOGGER.debug(part);
                ListNBT stageTag = NBTUtil.addCompoundList(group, STAGE + stage);
                CompoundNBT tmp = new CompoundNBT();
                String id = part.getRegistryName().toString();
                tmp.putString(ID, id);
                String type = "";
                if (part instanceof SpellShape) type = TYPE_SHAPE;
                if (part instanceof SpellModifier) type = TYPE_MODIFIER;
                if (part instanceof SpellComponent) type = TYPE_COMPONENT;
                tmp.putString(TYPE, type);
                if (part instanceof SpellShape) {
                    stage++;
                    lastWasShape = true;
                } else lastWasShape = false;
                stageTag.add(tmp);
            }
            group.putInt("StageNum", stage);
            group.putBoolean("LastWasShape", lastWasShape);
            group.putInt("CurrentGroup", 0);
            shapeGroupList.add(group);
        }
        int stage = 0;
        for (AbstractSpellPart part : spellDef) {
            ListNBT stageTag = NBTUtil.addCompoundList(aml, STAGE + stage);
            CompoundNBT tmp = new CompoundNBT();
            String id = SpellRegistry.getSkillFromPart(part).getID();
            tmp.putString(ID, id);
            String type = "";
            if (part instanceof SpellShape) type = TYPE_SHAPE;
            if (part instanceof SpellModifier) type = TYPE_MODIFIER;
            if (part instanceof SpellComponent) type = TYPE_COMPONENT;
            tmp.putString(TYPE, type);
            if (part instanceof SpellShape) stage++;
            stageTag.add(tmp);
        }
        aml.putInt("StageNum", stage + 1);
        aml.putInt("NumShapeGroups", shapeGroupList.size());
        aml.putInt("CurrentShapeGroup", shapeGroupList.size() == 0 ? -1 : 0);
        aml.putInt("CurrentGroup", 0);
        stack.setTag(tag);
        return stack;
    }

    public static ItemStack makeSpellStack(List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroupSetup, Pair<List<AbstractSpellPart>, CompoundNBT> curRecipeSetup) {
        return makeSpellStack(shapeGroupSetup, curRecipeSetup.getKey(), curRecipeSetup.getValue());
    }

    public static ItemStack merge(ItemStack spellIn) {
        if (spellIn.getTag() == null) return spellIn;
        if (NBTUtil.getAMLTag(spellIn.getTag()).getInt("CurrentShapeGroup") == -1) return spellIn;
        ItemStack newStack = spellIn.copy();
        if (spellIn.getItem() != ModItems.SPELL.get())
            newStack = new ItemStack(ModItems.SPELL.get(), newStack.getCount(), newStack.getTag());
        CompoundNBT group = NBTUtil.addCompoundList(NBTUtil.getAMLTag(newStack.getOrCreateTag()), "ShapeGroups").getCompound(NBTUtil.getAMLTag(newStack.getTag()).getInt("CurrentShapeGroup")).copy();
        int stageNum = stageNum(newStack);
        for (int i = 0; i < stageNum; i++) {
            ListNBT list = NBTUtil.addCompoundList(NBTUtil.getAMLTag(newStack.getTag()), STAGE + i).copy();
            if (i == 0 && !group.getBoolean("LastWasShape")) {
                ListNBT newList = NBTUtil.addCompoundList(group, "Stage_" + group.getInt("StageNum")).copy();
                for (int j = 0; j < list.size(); j++) newList.add(list.getCompound(j));
                list = newList;
            }
            group.put(STAGE + (i + group.getInt("StageNum")), list);
        }
        group.putInt("StageNum", group.getInt("StageNum") + stageNum);
        group.putInt("CurrentShapeGroup", -1);
        group.getCompound(SPELL_DATA).merge(NBTUtil.getAMLTag(newStack.getTag()).getCompound(SPELL_DATA));
        newStack.setTag(NBTUtil.addTag(new CompoundNBT(), group, "AM2"));
        return newStack;
    }

    public static String missingReagents(LivingEntity caster, ItemStack spellStack) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            if (player.isCreative()) return "";
            StringBuilder string = new StringBuilder(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.missingReagents").toString());
            boolean first = true;
            for (SpellComponent part : getComponents(spellStack, -1)) {
                if (part.getReagents(caster) == null) continue;
                for (ItemStack stack : part.getReagents(caster))
                    if (stack != null) {
                        boolean foundMatch = false;
                        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                            ItemStack is = player.inventory.getStackInSlot(i);
                            if (is.isEmpty()) continue;
                            if (is.getItem() == stack.getItem()) {
                                if (is.getCount() >= stack.getCount()) {
                                    foundMatch = true;
                                    break;
                                }
                            }
                        }
                        if (!foundMatch) {
                            if (!first) string.append(", ");
                            string.append(stack.getCount()).append("x ").append(stack.getDisplayName());
                            first = false;
                        }
                    }
            }
            return string.toString();
        }
        return "";
    }

    public static float modifyDamage(LivingEntity caster, float damage) {
        float factor;
        if (caster instanceof PlayerEntity)
            factor = (float) (CapabilityHelper.getCurrentLevel((PlayerEntity) caster) < 20 ? 0.5 + (0.5 * (CapabilityHelper.getCurrentLevel((PlayerEntity) caster) / 19)) : 1.0 + (1.0 * (CapabilityHelper.getCurrentLevel((PlayerEntity) caster) - 20) / 79));
        else factor = 1;
        return damage * factor;
    }

    public static double modifyDoubleAdd(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return modifyStat(defaultValue, 0, stack, caster, target, world, -1, modified);
    }

    public static double modifyDoubleAdd(ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return modifyStat(modified.getDefaultValueInt(), 0, stack, caster, target, world, -1, modified);
    }

    public static double modifyDoubleMul(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return modifyStat(defaultValue, 2, stack, caster, target, world, -1, modified);
    }

    public static double modifyDoubleMul(ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return modifyStat(modified.getDefaultValue(), 2, stack, caster, target, world, -1, modified);
    }

    public static int modifyIntAdd(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return (int) modifyDoubleAdd(defaultValue, stack, caster, target, world, modified);
    }

    public static int modifyIntAdd(ItemStack stack, LivingEntity caster, LivingEntity target, World world, SpellModifiers modified) {
        return modifyIntAdd(modified.getDefaultValueInt(), stack, caster, target, world, modified);
    }

    public static int modifyIntMul(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return (int) modifyStat(defaultValue, 2, stack, caster, target, world, -1, modified);
    }

    public static double modifyStat(double defaultValue, int operation, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifiers modified) {
        double val = defaultValue;
        if (stage != -1) {
            ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + stack);
            for (int i = 0; i < stageTag.size(); i++) {
                CompoundNBT tag = stageTag.getCompound(i);
                String tagType = tag.getString(TYPE);
                if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                    String tagID = tag.getString(ID);
                    SpellModifier mod = SpellRegistry.getModifierFromName(tagID);
                    if (mod.getAspectsModified().contains(modified))
                        val = calculate(operation, val, mod.getModifier(modified, caster, target, world, stack.getTag()));
                }
            }
        } else {
            for (int j = 0; j < NBTUtil.getAMLTag(stack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtil.addCompoundList(NBTUtil.getAMLTag(stack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                        String tagID = tag.getString(ID);
                        SpellModifier mod = SpellRegistry.getModifierFromName(tagID);
                        if (mod.getAspectsModified().contains(modified))
                            val = calculate(operation, val, mod.getModifier(modified, caster, target, world, stack.getTag()));
                    }
                }
            }
        }
        return val;
    }

    public static double modifyStat(int operation, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifiers modified) {
        return modifyStat(modified.getDefaultValue(), operation, stack, caster, target, world, stage, modified);
    }

    public static int numShapeGroups(ItemStack stack) {
        return NBTUtil.getAMLTag(stack.getTag()).getInt("NumShapeGroups");
    }

    public static boolean potionSpell(Effect effect, ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            int duration = SpellUtil.modifyIntMul(ModEffects.DEFAULT_BUFF_DURATION, stack, caster, target, world, SpellModifiers.DURATION);
//            if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
            duration += 3600 * (SpellUtil.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1);
//                RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
//            }
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new EffectInstance(effect, duration, SpellUtil.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    public static void setGroup(ItemStack stack, int newShapeGroupOrdinal) {
        if (stack.hasTag())
            NBTUtil.getAMLTag(stack.getTag()).putInt("CurrentShapeGroup", newShapeGroupOrdinal);
    }

    public static void setMetadata(ItemStack stack, String string, String s) {
        NBTUtil.addTag(NBTUtil.getAMLTag(stack.getTag()), SPELL_DATA).putString(string, s);
    }

    public static void setMetadata(CompoundNBT stack, String string, String s) {
        NBTUtil.addTag(NBTUtil.getAMLTag(stack), SPELL_DATA).putString(string, s);
    }

    public static int stageNum(ItemStack stack) {
        return NBTUtil.getAMLTag(stack.getTag()).getInt("StageNum");
    }
}
