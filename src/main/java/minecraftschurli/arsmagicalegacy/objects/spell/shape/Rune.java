package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.SpellParts;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Rune extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, Vec3d pos, Direction side, boolean giveXP, int useCount){
        int procs = SpellUtils.getModifiedIntAdd(1, stack, caster, target, world, 0, SpellModifier.Type.PROCS);
        boolean targetWater = SpellUtils.modifierIsPresent(SpellModifier.Type.TARGET_NONSOLID_BLOCKS, stack, 0);
        EntityRayTraceResult mop = null;//item.getMovingObjectPosition(caster, world, 8.0f, true, targetWater);
        if (mop == null || mop.getType() == RayTraceResult.Type.ENTITY/* || !ModBlocks.spellRune.placeAt(world, mop.getX(), mop.getY() + 1, mop.getZ(), SpellUtils.mainAffinityFor(stack).ordinal())*/) return SpellCastResult.EFFECT_FAILED;
//        if (!world.isRemote) {
//            world.setTileEntity(mop.getEntity().getPosition().getX(), mop.getEntity().getPosition().getY() + 1, mop.getEntity().getPosition().getZ(), BlocksCommonProxy.spellRune.createTileEntity(world, 0));
//            BlocksCommonProxy.spellRune.setSpellStack(world, mop.blockX, mop.blockY + 1, mop.blockZ, SpellUtils.popStackStage(stack));
//            BlocksCommonProxy.spellRune.setPlacedBy(world, mop.blockX, mop.blockY + 1, mop.blockZ, caster);
//            int meta = world.getBlockMetadata(mop.blockX, mop.blockY + 1, mop.blockZ);
//            BlocksCommonProxy.spellRune.setNumTriggers(world, mop.blockX, mop.blockY + 1, mop.blockZ, meta, procs);
//        }
        return SpellCastResult.SUCCESS;
    }

    @Override
    public boolean isChanneled(){
        return false;
    }

    @Override
    public ISpellIngredient[] getRecipeItems(){
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
    public float getManaCostMultiplier(ItemStack spellStack){
        return 1.8f;
    }

    @Override
    public boolean isTerminusShape(){
        return false;
    }

    @Override
    public boolean isPrincipumShape(){
        return true;
    }
//
//    @Override
//    public String getSoundForAffinity(Affinity affinity, ItemStack stack, World world){
//        return "arsmagica2:spell.rune.cast";
//    }
}
