package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Rune extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
//        int procs = SpellUtils.getModifiedIntAdd(1, stack, caster, target, world, SpellModifiers.PROCS);
//        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifiers.TARGET_NONSOLID_BLOCKS, stack);
//        RayTraceResult mop = item.getMovingObjectPosition(caster, world, 8, true, targetWater);
//        if (mop == null || mop.getType() == RayTraceResult.Type.ENTITY || !BlockDefs.spellRune.placeAt(world, ((BlockRayTraceResult)mop).getPos().up(), BlockDefs.spellRune.getDefaultState()))
        return SpellCastResult.EFFECT_FAILED;
//        if (!world.isRemote) {
//            world.setTileEntity(((BlockRayTraceResult)mop).getPos().up(), BlockDefs.spellRune.createNewTileEntity(world, 0));
//            BlockDefs.spellRune.setSpellStack(world, ((BlockRayTraceResult)mop).getPos().up(), stack);
//            BlockDefs.spellRune.setPlacedBy(world, ((BlockRayTraceResult)mop).getPos().up(), caster);
//            BlockDefs.spellRune.setNumTriggers(world, ((BlockRayTraceResult)mop).getPos().up(), world.getBlockState(((BlockRayTraceResult)mop).getPos().up()), procs);
//        }
//        return SpellCastResult.SUCCESS;
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
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return true;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1.8f;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return null;
    }
}
