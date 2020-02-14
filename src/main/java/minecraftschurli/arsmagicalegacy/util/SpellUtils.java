package minecraftschurli.arsmagicalegacy.util;

import com.google.common.collect.*;
import javafx.util.*;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.capability.*;
import minecraftschurli.arsmagicalegacy.api.event.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.*;
import minecraftschurli.arsmagicalegacy.objects.spell.shape.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

import javax.annotation.*;
import java.util.*;

public class SpellUtils {

    public static final String TYPE_SHAPE = "Shape";
    public static final String TYPE_COMPONENT = "Component";
    public static final String TYPE_MODIFIER = "Modifier";
    public static final String TYPE = "Type";
    public static final String ID = "ID";
    public static final String SHAPE_GROUP = "ShapeGroup";
    public static final String STAGE = "Stage_";
    public static final String SPELL_DATA = "SpellData";

    public static SpellShape getShapeForStage(ItemStack oldIs, int stage) {
        if (oldIs == null || !oldIs.hasTag()) return (SpellShape) ArsMagicaAPI.getSpellPartRegistry().getValue(ArsMagicaAPI.MISSING_SHAPE);
        ItemStack stack = merge(oldIs.copy());
        CompoundNBT am2Tag = NBTUtils.getAM2Tag(stack.getTag());
        ListNBT stageTag = NBTUtils.addCompoundList(am2Tag, STAGE + stage);
        String shapeName = "null";
        for (int i = 0; i < stageTag.size(); i++) {
            if (stageTag.getCompound(i).getString(TYPE).equals(TYPE_SHAPE)) {
                shapeName = stageTag.getCompound(i).getString(ID);
                break;
            }
        }
        return SpellRegistry.getShapeFromName(shapeName) != null ? SpellRegistry.getShapeFromName(shapeName) : (SpellShape) ArsMagicaAPI.getSpellPartRegistry().getValue(ArsMagicaAPI.MISSING_SHAPE);
    }

    public static void changeEnchantmentsForShapeGroup(ItemStack stack) {
        ItemStack constructed = merge(stack);
        int looting = 0;
        int silkTouch = 0;
        for (int i = 0; i < numStages(constructed); ++i) {
            looting += countModifiers(SpellModifiers.FORTUNE_LEVEL, constructed);
            silkTouch += countModifiers(SpellModifiers.SILKTOUCH_LEVEL, constructed);
        }
        /*AMEnchantmentHelper.fortuneStack(stack, looting);
        AMEnchantmentHelper.lootingStack(stack, looting);
        AMEnchantmentHelper.silkTouchStack(stack, silkTouch);*/
    }

    public static float modifyDamage(LivingEntity caster, float damage) {
        float factor;
        if (caster instanceof PlayerEntity){
            factor = (float) (CapabilityHelper.getCurrentLevel((PlayerEntity) caster) < 20 ?
                0.5 + (0.5 * (CapabilityHelper.getCurrentLevel((PlayerEntity) caster) / 19)) :
                1.0 + (1.0 * (CapabilityHelper.getCurrentLevel((PlayerEntity) caster) - 20) / 79));
        } else factor = 1;
        return damage * factor;
    }


    public static boolean modifierIsPresent(SpellModifiers mod, ItemStack stack) {
        List<SpellModifier> mods = getModifiersForStage(stack, -1);
        if (mods.isEmpty())
            return false;
        for (SpellModifier m : mods) {
            if (m.getAspectsModified().contains(mod))
                return true;
        }

        return false;
    }

    public static int countModifiers(SpellModifiers mod, ItemStack stack) {
        List<SpellModifier> mods = getModifiersForStage(stack, -1);
        int i = 0;
        for (SpellModifier m : mods) {
            if (m.getAspectsModified().contains(mod))
                i++;
        }

        return i;
    }

    public static boolean attackTargetSpecial(ItemStack spellStack, Entity target, DamageSource damagesource, float magnitude) {

        if (target.world.isRemote)
            return true;

        PlayerEntity dmgSrcPlayer = null;

        if (damagesource.getTrueSource() != null) {
            if (damagesource.getTrueSource() instanceof LivingEntity) {
                LivingEntity source = (LivingEntity) damagesource.getTrueSource();
                if (/*(source instanceof EntityLightMage || source instanceof EntityDarkMage) && */target.getClass() == CreeperEntity.class){
                    return false;
//                }else if (source instanceof EntityLightMage && target instanceof EntityLightMage){
//                    return false;
//                }else if (source instanceof EntityDarkMage && target instanceof EntityDarkMage){
//                    return false;
                }else
                if (source instanceof PlayerEntity && target instanceof PlayerEntity && !target.world.isRemote && (!((ServerPlayerEntity) target).server.isPVPEnabled() || ((PlayerEntity) target).isCreative())) return false;
                if (source.isPotionActive(ModEffects.FURY.get()))
                    magnitude += 4;
            }
//TODO @minecraftschurli
            /*if (damagesource.getTrueSource() instanceof PlayerEntity){
                dmgSrcPlayer = (PlayerEntity)damagesource.getTrueSource();
                int armorSet = ArmorHelper.getFullArsMagicaArmorSet(dmgSrcPlayer);
                if (armorSet == ArsMagicaArmorMaterial.MAGE.getMaterialID()){
                    magnitude *= 1.05f;
                }else if (armorSet == ArsMagicaArmorMaterial.BATTLEMAGE.getMaterialID()){
                    magnitude *= 1.025f;
                }else if (armorSet == ArsMagicaArmorMaterial.ARCHMAGE.getMaterialID()){
                    magnitude *= 1.1f;
                }

                ItemStack equipped = dmgSrcPlayer.getActiveItemStack();
                if (equipped != null && equipped.getItem() == ItemDefs.arcaneSpellbook){
                    magnitude *= 1.1f;
                }
            }*/
        }

        /*if (target instanceof LivingEntity){
            if (EntityUtils.isSummon((LivingEntity)target) && damagesource.damageType.equals("magic")){
                magnitude *= 3;
            }
        }*/
//TODO @minecraftschurli
        //magnitude *= ArsMagica2.config.getDamageMultiplier();
        return target.attackEntityFrom(damagesource, magnitude);
    }

    public static CompoundNBT encode(Pair<List<AbstractSpellPart>, CompoundNBT> toEncode) {
        CompoundNBT group = new CompoundNBT();
        group.put(SPELL_DATA, toEncode.getValue());
        int stage = 0;
        for (AbstractSpellPart part : toEncode.getKey()) {
            ListNBT stageTag = NBTUtils.addCompoundList(group, STAGE + stage);
            CompoundNBT tmp = new CompoundNBT();
            String id = SpellRegistry.getSkillFromPart(part).getID();
            tmp.putString(ID, id);
            String type = "";
            if (part instanceof SpellShape) type = TYPE_SHAPE;
            if (part instanceof SpellModifier) type = TYPE_MODIFIER;
            if (part instanceof SpellComponent) type = TYPE_COMPONENT;
            tmp.putString(TYPE, type);
            if (part instanceof SpellShape) {
                stage++;
            }
            stageTag.add(tmp);
        }
        group.putInt("StageNum", stage);
        return group;
    }

    public static Pair<ArrayList<AbstractSpellPart>, CompoundNBT> decode(CompoundNBT toDecode) {
        if (toDecode == null)
            return null;
        try {
            ArrayList<AbstractSpellPart> parts = new ArrayList<>();
            for (int j = 0; j < NBTUtils.getAM2Tag(toDecode).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(toDecode), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tmp = stageTag.getCompound(i);
                    String type = tmp.getString(TYPE);
                    if (type.equalsIgnoreCase(TYPE_COMPONENT)) {
                        parts.add(SpellRegistry.getComponentFromName(tmp.getString(ID)));
                    }
                    if (type.equalsIgnoreCase(TYPE_MODIFIER)) {
                        parts.add(SpellRegistry.getModifierFromName(tmp.getString(ID)));
                    }
                    if (type.equalsIgnoreCase(TYPE_SHAPE)) {
                        parts.add(SpellRegistry.getShapeFromName(tmp.getString(ID)));
                    }
                }
            }
            return new Pair<>(parts, toDecode.getCompound(SPELL_DATA));
        } catch (Exception e) {
            return null;
        }
    }

    public static ItemStack createSpellStack(List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroups, List<AbstractSpellPart> spellDef, CompoundNBT encodedData) {
        ItemStack stack = new ItemStack(ModItems.SPELL.get());
        CompoundNBT tag = new CompoundNBT();
        CompoundNBT am2 = NBTUtils.getAM2Tag(tag);
        am2.put(SPELL_DATA, encodedData);
        ListNBT shapeGroupList = NBTUtils.addCompoundList(am2, "ShapeGroups");
        for (Pair<List<AbstractSpellPart>, CompoundNBT> shapeGroup : shapeGroups) {
            if (shapeGroup.getKey().isEmpty()) {
                continue;
            }
            CompoundNBT group = new CompoundNBT();
            group.put(SPELL_DATA, shapeGroup.getValue());
            int stage = 0;
            boolean lastWasShape = false;
            for (AbstractSpellPart part : shapeGroup.getKey()) {
                ArsMagicaLegacy.LOGGER.debug(part);
                ListNBT stageTag = NBTUtils.addCompoundList(group, STAGE + stage);
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
                } else {
                    lastWasShape = false;
                }
                stageTag.add(tmp);
            }
            group.putInt("StageNum", stage);
            group.putBoolean("LastWasShape", lastWasShape);
            group.putInt("CurrentGroup", 0);
            shapeGroupList.add(group);
        }
        int stage = 0;
        for (AbstractSpellPart part : spellDef) {
            ListNBT stageTag = NBTUtils.addCompoundList(am2, STAGE + stage);
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
        am2.putInt("StageNum", stage + 1);
        am2.putInt("NumShapeGroups", shapeGroupList.size());
        am2.putInt("CurrentShapeGroup", shapeGroupList.size() == 0 ? -1 : 0);
        am2.putInt("CurrentGroup", 0);
        stack.setTag(tag);
        return stack;
    }

    public static ItemStack merge(ItemStack spellIn) {

        if (spellIn.getTag() == null)
            return spellIn;
        if (NBTUtils.getAM2Tag(spellIn.getTag()).getInt("CurrentShapeGroup") == -1) return spellIn;
        ItemStack newStack = spellIn.copy();
        if (spellIn.getItem() != ModItems.SPELL.get()) newStack = new ItemStack(ModItems.SPELL.get(), newStack.getCount(), newStack.getTag());
        CompoundNBT group = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(newStack.getOrCreateTag()), "ShapeGroups").getCompound(NBTUtils.getAM2Tag(newStack.getTag()).getInt("CurrentShapeGroup")).copy();
        int stageNum = numStages(newStack);
        for (int i = 0; i < stageNum; i++) {
            ListNBT list = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(newStack.getTag()), STAGE + i).copy();
            if (i == 0 && !group.getBoolean("LastWasShape")) {
                ListNBT newList = NBTUtils.addCompoundList(group, "Stage_" + group.getInt("StageNum")).copy();
                for (int j = 0; j < list.size(); j++) newList.add(list.getCompound(j));
                list = newList;
            }
            // (group.getBoolean("LastWasShape") ? -1 : 0) +
            group.put(STAGE + (i + group.getInt("StageNum")), list);
        }
        group.putInt("StageNum", group.getInt("StageNum") + stageNum);
        group.putInt("CurrentShapeGroup", -1);
        group.getCompound(SPELL_DATA).merge(NBTUtils.getAM2Tag(newStack.getTag()).getCompound(SPELL_DATA));
        newStack.setTag(NBTUtils.addTag(new CompoundNBT(), group, "AM2"));
        return newStack;
    }

    public static ItemStack popStackStage(ItemStack is) {
        NBTUtils.getAM2Tag(is.getTag()).putInt("CurrentGroup", NBTUtils.getAM2Tag(is.getTag()).getInt("CurrentGroup") + 1);
        return is;
    }

    public static int numStages(ItemStack stack) {
        return NBTUtils.getAM2Tag(stack.getTag()).getInt("StageNum");
    }

    public static float getManaCost(ItemStack stack, LivingEntity caster) {
        if (stack.getTag() == null)
            return 0;
        ItemStack mergedStack = merge(stack);
        Affinity[] affinities = null;
        if (caster instanceof PlayerEntity) {
            affinities = CapabilityHelper.getHighestAffinities(caster);
        }
        try {
            float cost = 0;
            float modMultiplier = 1;
            for (int j = 0; j < NBTUtils.getAM2Tag(mergedStack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(mergedStack.getTag()), STAGE + j);
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
                                    } else {
                                        cost = cost + (cost * (0.10f));
                                    }
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

    public static float getBurnoutCost(ItemStack stack, LivingEntity caster) {
        if (stack.getTag() == null)
            return 0;
        ItemStack mergedStack = merge(stack);
        try {
            float cost = 0;
            for (int j = 0; j < NBTUtils.getAM2Tag(mergedStack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(mergedStack.getTag()), STAGE + j);
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

    public static SpellCastResult applyStackStage(ItemStack stack, LivingEntity caster, LivingEntity target, double x, double y, double z, @Nullable Direction side, World world, boolean consumeMBR, boolean giveXP, int ticksUsed) {
        if (caster.isPotionActive(ModEffects.SILENCE.get()))
            return SpellCastResult.SILENCED;

        int group = NBTUtils.getAM2Tag(stack.getTag()).getInt("CurrentGroup");
        if (group == 0) {
            stack = merge(stack.copy());
        }
        SpellShape shape = getShapeForStage(stack, group);
        if (!(caster instanceof PlayerEntity))
            return SpellCastResult.EFFECT_FAILED;
        if (shape instanceof MissingShape) {
            return SpellCastResult.MALFORMED_SPELL_STACK;
        }
        SpellCastEvent.Pre pre = new SpellCastEvent.Pre(stack, (SpellItem) stack.getItem(), caster, getManaCost(stack, caster), getBurnoutCost(stack, caster),shape == ModSpellParts.CHANNEL.get());
        MinecraftForge.EVENT_BUS.post(pre);
        float manaCost = pre.manaCost;
        float burnoutCost = pre.burnout;

        SpellCastResult result = null;

        if (consumeMBR && !((PlayerEntity) caster).isCreative()) {
            if (!CapabilityHelper.hasEnoughtMana(caster, manaCost)) {
                result =  SpellCastResult.NOT_ENOUGH_MANA;
            } else if (CapabilityHelper.isBurnedOut(caster, burnoutCost)) {
                result =  SpellCastResult.BURNED_OUT;
            } else if (!casterHasAllReagents(caster, stack)) {
                if (world.isRemote)
                    caster.sendMessage(new StringTextComponent(getMissingReagents(caster, stack)));
                result =  SpellCastResult.REAGENTS_MISSING;
            }
        }

        if (result == null) {
            ItemStack stack2 = stack.copy();
            NBTUtils.getAM2Tag(stack2.getTag()).putInt("CurrentGroup", group + 1);
            if (group == 0) {
                result = shape.beginStackStage((SpellItem) stack.getItem(), stack2, caster, target, world, x, y, z, side, giveXP, ticksUsed);
            } else {
                NBTUtils.getAM2Tag(stack.getTag()).putInt("CurrentGroup", group + 1);
                result = shape.beginStackStage((SpellItem) stack.getItem(), stack, caster, target, world, x, y, z, side, giveXP, ticksUsed);
            }
        }

        SpellCastEvent.Post post = new SpellCastEvent.Post(stack, (SpellItem) stack.getItem(), caster, manaCost, burnoutCost, shape == ModSpellParts.CHANNEL.get(), result);
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

    public static boolean casterHasAllReagents(LivingEntity caster, ItemStack spellStack) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            if (player.isCreative()) return true;
            for (SpellComponent part : getComponentsForStage(spellStack, -1)) {
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

    public static String getMissingReagents(LivingEntity caster, ItemStack spellStack) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            if (player.isCreative()) return "";
            StringBuilder string = new StringBuilder(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".chat.missingReagents").toString());
            boolean first = true;
            for (SpellComponent part : getComponentsForStage(spellStack, -1)) {
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
                        if (!foundMatch) {
                            if (!first) string.append(", ");
                            string.append(stack.getCount()).append("x ").append(stack.getDisplayName());
                            first = false;
                        }
                    }
                }
            }
            return string.toString();
        }
        return "";
    }

    public static void consumeReagents(LivingEntity caster, ItemStack spellStack) {
        if (caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) caster;
            if (player.isCreative()) return;
            for (SpellComponent part : getComponentsForStage(spellStack, -1)) {
                if (part.getReagents(caster) == null) continue;
                for (ItemStack stack : part.getReagents(caster)) {
                    if (stack != null) {
                        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                            ItemStack is = player.inventory.getStackInSlot(i);
                            if (is.isEmpty()) continue;
                            if (is.getItem() == stack.getItem()) {
                                if (is.getCount() >= stack.getCount()) {
                                    is.setCount(is.getCount() - stack.getCount());
                                    if (is.getCount() <= 0) {
                                        player.inventory.setInventorySlotContents(i, null);
                                    } else {
                                        player.inventory.setInventorySlotContents(i, is);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static double getModifiedStat(double defaultValue, int operation, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifiers modified) {
        double val = defaultValue;
        if (stage != -1) {
            ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + stack);
            for (int i = 0; i < stageTag.size(); i++) {
                CompoundNBT tag = stageTag.getCompound(i);
                String tagType = tag.getString(TYPE);
                if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                    String tagID = tag.getString(ID);
                    SpellModifier mod = SpellRegistry.getModifierFromName(tagID);
                    if (mod.getAspectsModified().contains(modified))
                        val = makeCalculation(operation, val, mod.getModifier(modified, caster, target, world, stack.getTag()));
                }
            }
        } else {
            for (int j = 0; j < NBTUtils.getAM2Tag(stack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                        String tagID = tag.getString(ID);
                        SpellModifier mod = SpellRegistry.getModifierFromName(tagID);
                        if (mod.getAspectsModified().contains(modified)) {
                            val = makeCalculation(operation, val, mod.getModifier(modified, caster, target, world, stack.getTag()));
                        }
                    }
                }
            }
        }
        return val;
    }

    private static double makeCalculation(int operation, double val, double mod) {
        if (operation == 0) {
            return val + mod;
        }
        if (operation == 1) {
            return val - mod;
        }
        if (operation == 2) {
            return val * mod;
        }
        if (operation == 3) {
            return val / mod;
        }
        return 0;
    }

    /**
     * @param operation 0 add, 1 subtract, 2 multiply, 3 divide
     * @param stage     set to -1 for all;
     * @param modified
     * @param stack
     * @return
     */
    public static double getModifiedStat(int operation, ItemStack stack, LivingEntity caster, Entity target, World world, int stage, SpellModifiers modified) {
        return getModifiedStat(modified.defaultValue, operation, stack, caster, target, world, stage, modified);
    }

    public static List<SpellModifier> getModifiersForStage(ItemStack stack, int stage) {
        List<SpellModifier> mods = new ArrayList<SpellModifier>();
        if (stack.getTag() == null)
            return mods;
        if (stage != -1) {
            ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + stage);
            for (int i = 0; i < stageTag.size(); i++) {
                CompoundNBT tag = stageTag.getCompound(i);
                String tagType = tag.getString(TYPE);
                if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                    mods.add(SpellRegistry.getModifierFromName(tag.getString(ID)));
                }
            }
        } else {
            for (int j = 0; j <= NBTUtils.getAM2Tag(stack.getTag()).getInt("StageNum"); j++) {

                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                        mods.add(SpellRegistry.getModifierFromName(tag.getString(ID)));
                    }
                }
            }
        }
        return mods;
    }

    public static List<AbstractSpellPart> getPartsForGroup(ItemStack stack, int group) {
        List<AbstractSpellPart> mods = new ArrayList<>();
        try {
            CompoundNBT compound = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), "ShapeGroups").getCompound(NBTUtils.getAM2Tag(stack.getTag()).getInt("CurrentShapeGroup")).copy();
            for (int j = 0; j <= compound.getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(compound, STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_MODIFIER)) {
                        mods.add(SpellRegistry.getModifierFromName(tag.getString(ID)));
                    }
                    if (tagType.equalsIgnoreCase(TYPE_SHAPE)) {
                        mods.add(SpellRegistry.getShapeFromName(tag.getString(ID)));
                    }
                }
            }
            return mods;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static ArrayList<AbstractSpellPart> getPartsForSpell(ItemStack stack) {
        try {
            ArrayList<AbstractSpellPart> mods = new ArrayList<AbstractSpellPart>();
            for (int j = 0; j <= NBTUtils.getAM2Tag(stack.getTag()).getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    mods.add(ArsMagicaAPI.getSpellPartRegistry().getValue(new ResourceLocation(tag.getString(ID))));
                }
            }
            return mods;
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static List<SpellComponent> getComponentsForStage(ItemStack stack, int stage) {
        try {
            List<SpellComponent> mods = new ArrayList<>();
            if (stage != -1) {
                ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + stage);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tag = stageTag.getCompound(i);
                    String tagType = tag.getString(TYPE);
                    if (tagType.equalsIgnoreCase(TYPE_COMPONENT)) {
                        mods.add(SpellRegistry.getComponentFromName(tag.getString(ID)));
                    }
                }
            } else {
                for (int j = 0; j <= NBTUtils.getAM2Tag(stack.getTag()).getInt("StageNum"); j++) {
                    ListNBT stageTag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), STAGE + j);
                    for (int i = 0; i < stageTag.size(); i++) {
                        CompoundNBT tag = stageTag.getCompound(i);
                        String tagType = tag.getString(TYPE);
                        if (tagType.equalsIgnoreCase(TYPE_COMPONENT)) {
                            mods.add(SpellRegistry.getComponentFromName(tag.getString(ID)));
                        }
                    }
                }
            }
            return mods;
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static SpellCastResult applyStageToGround(ItemStack stack, LivingEntity caster, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, boolean consumeMBR) {
        SpellShape stageShape = SpellUtils.getShapeForStage(stack, 0);
        if (stageShape == null || Objects.equals(stageShape.getRegistryName(), ArsMagicaAPI.MISSING_SHAPE)) {
            return SpellCastResult.MALFORMED_SPELL_STACK;
        }
        boolean isPlayer = caster instanceof PlayerEntity;
        int group = NBTUtils.getAM2Tag(stack.getTag()).getInt("CurrentGroup");
        List<SpellComponent> components = SpellUtils.getComponentsForStage(stack, group);
        for (SpellComponent component : components) {
            if (component.applyEffectBlock(stack, world, pos, blockFace, impactX, impactY, impactZ, caster)) {
                if (isPlayer && !world.isRemote) {
                    if (component.getAffinity() != null) {
                        CapabilityHelper.doAffinityShift(caster, component, stageShape);
                    }
                }
                if (world.isRemote) {
                    int color = -1;
                    if (modifierIsPresent(SpellModifiers.COLOR, stack)) {
                        List<SpellModifier> mods = SpellUtils.getModifiersForStage(stack, -1);
                        for (SpellModifier mod : mods) {
                            if (mod instanceof Color){
                                color = (int)mod.getModifier(SpellModifiers.COLOR, caster, null, world, NBTUtils.getAM2Tag(stack.getTag()));
                            }
                        }
                    }
                    component.spawnParticles(world, pos.getX(), pos.getY(), pos.getZ(), caster, caster, world.rand, color);
                }
            }
        }

        return SpellCastResult.SUCCESS;
    }

    public static SpellCastResult applyStageToEntity(ItemStack stack, LivingEntity caster, World world, Entity target, boolean shiftAffinityAndXP) {
        SpellShape stageShape = SpellUtils.getShapeForStage(stack, 0);
        if (stageShape == null) return SpellCastResult.MALFORMED_SPELL_STACK;

		if (/*(!AMCore.config.getAllowCreativeTargets()) &&*/ target instanceof ServerPlayerEntity && ((ServerPlayerEntity) target).abilities.isCreativeMode) {
			return SpellCastResult.EFFECT_FAILED;
		}
        int group = NBTUtils.getAM2Tag(stack.getTag()).getInt("CurrentGroup");
        List<SpellComponent> components = SpellUtils.getComponentsForStage(stack, group);

        boolean appliedOneComponent = false;
        boolean isPlayer = caster instanceof PlayerEntity;

        for (SpellComponent component : components) {

//			if (StoryManager.INSTANCE.isSkillDisabled(component))
//				continue;

            if (component.applyEffectEntity(stack, world, caster, target)) {
                if (isPlayer && !world.isRemote) {
                    if (component.getAffinity() != null) {
                        CapabilityHelper.doAffinityShift(caster, component, stageShape);
                    }
                }
                appliedOneComponent = true;
                if (world.isRemote) {
                    int color = -1;
                    if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, stack)) {
                        List<SpellModifier> mods = SpellUtils.getModifiersForStage(stack, -1);
                        for (SpellModifier mod : mods) {
                            if (mod instanceof Color){
                                color = (int)mod.getModifier(SpellModifiers.COLOR, caster, target, world, NBTUtils.getAM2Tag(stack.getTag()));
                            }
                        }
                    }
                    component.spawnParticles(world, target.getPositionVec().x, target.getPositionVec().y + target.getEyeHeight(), target.getPositionVec().z, caster, target, world.rand, color);
                }
                if (caster instanceof PlayerEntity) {
                    CapabilityHelper.doAffinityShift(caster, component, stageShape);
                }
            }
        }

        if (appliedOneComponent)
            return SpellCastResult.SUCCESS;
        else
            return SpellCastResult.EFFECT_FAILED;
    }

    public static int currentStage(ItemStack spellStack) {
        return NBTUtils.getAM2Tag(spellStack.getTag()).getInt("CurrentGroup");
    }

    public static boolean componentIsPresent(ItemStack stack, Class<? extends SpellComponent> clazz) {
        for (SpellComponent comp : getComponentsForStage(stack, currentStage(stack)))
            if (clazz.isInstance(comp))
                return true;
        return false;
    }

    public static int getModifiedIntMul(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return (int) getModifiedStat(defaultValue, 2, stack, caster, target, world, -1, modified);
    }

    public static double getModifiedDoubleMul(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return getModifiedStat(defaultValue, 2, stack, caster, target, world, -1, modified);
    }

    public static double getModifiedDoubleMul(ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return getModifiedStat(modified.defaultValue, 2, stack, caster, target, world, -1, modified);
    }

    public static int getModifiedIntAdd(int defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return (int) getModifiedDoubleAdd(defaultValue, stack, caster, target, world, modified);
    }

    public static double getModifiedDoubleAdd(double defaultValue, ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return getModifiedStat(defaultValue, 0, stack, caster, target, world, -1, modified);
    }

    public static double getModifiedDoubleAdd(ItemStack stack, LivingEntity caster, Entity target, World world, SpellModifiers modified) {
        return getModifiedStat(modified.defaultValueInt, 0, stack, caster, target, world, -1, modified);
    }

    public static int getModifiedIntAdd(ItemStack stack, LivingEntity caster, LivingEntity target, World world, SpellModifiers modified) {
        return getModifiedIntAdd(modified.defaultValueInt, stack, caster, target, world, modified);
    }

    public static String getSpellMetadata(ItemStack stack, String string) {
        return NBTUtils.addTag(NBTUtils.getAM2Tag(stack.getTag()), SPELL_DATA).getString(string);
    }

    public static void setSpellMetadata(ItemStack stack, String string, String s) {
        NBTUtils.addTag(NBTUtils.getAM2Tag(stack.getTag()), SPELL_DATA).putString(string, s);
    }

    public static void setSpellMetadata(CompoundNBT stack, String string, String s) {
        NBTUtils.addTag(NBTUtils.getAM2Tag(stack), SPELL_DATA).putString(string, s);
    }

    public static int numShapeGroups(ItemStack stack) {
        return NBTUtils.getAM2Tag(stack.getTag()).getInt("NumShapeGroups");
    }

    public static ArrayList<AbstractSpellPart> getShapeGroupParts(ItemStack stack, int shapeGroup) {
        try {
            ArrayList<AbstractSpellPart> mods = new ArrayList<AbstractSpellPart>();
            CompoundNBT tag = NBTUtils.addCompoundList(NBTUtils.getAM2Tag(stack.getTag()), "ShapeGroups").getCompound(shapeGroup);
            for (int j = 0; j <= tag.getInt("StageNum"); j++) {
                ListNBT stageTag = NBTUtils.addCompoundList(tag, STAGE + j);
                for (int i = 0; i < stageTag.size(); i++) {
                    CompoundNBT tmp = stageTag.getCompound(i);
                    mods.add(SpellRegistry.getComponentFromName(tmp.getString(ID)));
                }
            }
            return mods;
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static ItemStack createSpellStack(List<Pair<List<AbstractSpellPart>, CompoundNBT>> shapeGroupSetup, Pair<List<AbstractSpellPart>, CompoundNBT> curRecipeSetup) {
        return createSpellStack(shapeGroupSetup, curRecipeSetup.getKey(), curRecipeSetup.getValue());
    }

    public static SpellShape getShapeForStage(ItemStack stack) {
        return getShapeForStage(stack, currentStage(stack));
    }

    public static Map<Affinity, Float> affinityFor(ItemStack stack) {
        Map<Affinity, Float> customDepthMap = new HashMap<>();
        List<SpellComponent> components = SpellUtils.getComponentsForStage(stack, -1);
        for (SpellComponent component : components) {
            for (Affinity aff1 : component.getAffinity()) {
                if (customDepthMap.get(aff1) != null) {
                    customDepthMap.put(aff1, customDepthMap.get(aff1) + component.getAffinityShift(aff1));
                } else {
                    customDepthMap.put(aff1, component.getAffinityShift(aff1));
                }
            }
        }
        return customDepthMap;
    }

    public static SpellCastResult applyStackStageOnUsing(ItemStack stack, LivingEntity caster, LivingEntity target, double x, double y, double z, World world, boolean consumeMBR, boolean giveXP, int ticks) {
        if (SpellUtils.numStages(stack) == 0) {
            return SpellCastResult.SUCCESS;
        }

        if (!SpellUtils.getShapeForStage(stack).isChanneled()) {
            return SpellCastResult.EFFECT_FAILED;
        }

        return applyStackStage(stack, caster, target, x, y, z, null, world, consumeMBR, giveXP, ticks);
    }

    public static int cycleShapeGroup(ItemStack stack) {
        if (!stack.hasTag())
            return 0;
        int current = NBTUtils.getAM2Tag(stack.getTag()).getInt("CurrentShapeGroup");
        int max = NBTUtils.getAM2Tag(stack.getTag()).getInt("NumShapeGroups");
        if (max == 0)
            return 0;
        return (current + 1) % max;
    }

    public static void setShapeGroup(ItemStack stack, int newShapeGroupOrdinal) {
        if (stack.hasTag())
            NBTUtils.getAM2Tag(stack.getTag()).putInt("CurrentShapeGroup", newShapeGroupOrdinal);
    }

}
