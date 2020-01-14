package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Rune extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1.8f;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return true;
    }

    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        int procs = SpellUtils.getModifiedIntAdd(1, stack, caster, target, world, SpellModifiers.PROCS);
        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
        RayTraceResult mop = null;//item.getMovingObjectPosition(caster, world, 8.0f, true, targetWater);
        if (mop == null || mop.getType() == RayTraceResult.Type.ENTITY/* || !BlockDefs.spellRune.placeAt(world, ((BlockRayTraceResult)mop).getPos().up(), BlockDefs.spellRune.getDefaultState())*/)
            return SpellCastResult.EFFECT_FAILED;
        if (!world.isRemote) {
//            world.setTileEntity(((BlockRayTraceResult)mop).getPos().up(), BlockDefs.spellRune.createNewTileEntity(world, 0));
//            BlockDefs.spellRune.setSpellStack(world, ((BlockRayTraceResult)mop).getPos().up(), stack);
//            BlockDefs.spellRune.setPlacedBy(world, ((BlockRayTraceResult)mop).getPos().up(), caster);
//            BlockDefs.spellRune.setNumTriggers(world, ((BlockRayTraceResult)mop).getPos().up(), world.getBlockState(((BlockRayTraceResult)mop).getPos().up()), procs);
        }
        return SpellCastResult.SUCCESS;
    }

    /**
     * Gets the spell cooldown for this shape
     *
     * @param caster the caster of this spell
     * @return the cooldown in ticks
     */
    @Override
    public int getCooldown(LivingEntity caster) {
        return 6;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.WHITE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ORANGE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.YELLOW_RUNE.get()))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return null;
    }
}
