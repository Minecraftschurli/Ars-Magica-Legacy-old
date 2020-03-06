package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.EntityUtils;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Chain extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1.5f;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        RayTraceResult mop = EntityUtils.getMovingObjectPosition(caster, world, 8, true, false);
        double range = SpellUtils.getModifiedDoubleMul(8, stack, caster, target, world, SpellModifiers.RANGE);
        int num_targets = SpellUtils.getModifiedIntAdd(3, stack, caster, target, world, SpellModifiers.PROCS);
        ArrayList<LivingEntity> targets = new ArrayList<>();
        if (target != null) mop = new EntityRayTraceResult(target);
        if (mop != null && mop.getType() == RayTraceResult.Type.ENTITY) {
            ((EntityRayTraceResult) mop).getEntity();
            Entity e = ((EntityRayTraceResult) mop).getEntity();
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                e = ((EnderDragonPartEntity) e).dragon;
            if (e instanceof LivingEntity) {
                do {
                    targets.add((LivingEntity) e);
                    List<LivingEntity> nearby = world.getEntitiesWithinAABB(LivingEntity.class, e.getBoundingBox().expand(range, range, range));
                    LivingEntity closest = null;
                    for (LivingEntity near : nearby) {
                        if (targets.contains(near) || near == caster) continue;
                        if (closest == null || closest.getDistanceSq(e) > near.getDistanceSq(e)) closest = near;
                    }
                    e = closest;
                } while (e != null && targets.size() < num_targets);
            }
        }
        boolean atLeastOneApplication = false;
        SpellCastResult result = SpellCastResult.SUCCESS;
        LivingEntity prevEntity = null;
        for (LivingEntity e : targets) {
            if (e == caster) continue;
            result = SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP);
            SpellUtils.applyStackStage(stack, caster, e, e.getPosX(), e.getPosY(), e.getPosZ(), null, world, true, giveXP, 0);
            if (world.isRemote) {
//                if (prevEntity == null) spawnChainParticles(world, x, y, z, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
//                else spawnChainParticles(world, prevEntity.posX, prevEntity.posY + e.getEyeHeight(), prevEntity.posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ, stack);
            }
            prevEntity = e;
            if (result == SpellCastResult.SUCCESS) atLeastOneApplication = true;
        }
        if (atLeastOneApplication) return SpellCastResult.SUCCESS;
        return result;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.GEMS_SUNSTONE),
                new ItemTagSpellIngredient(Tags.Items.INGOTS_IRON),
                new ItemStackSpellIngredient(new ItemStack(Items.LEAD)),
                new ItemStackSpellIngredient(new ItemStack(Items.STRING)),
                new ItemStackSpellIngredient(new ItemStack(Items.TRIPWIRE_HOOK))
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.PROCS);
    }

    private void spawnChainParticles(World world, double startX, double startY, double startZ, double endX, double endY, double endZ, ItemStack spellStack){
        int color = -1;
        if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, spellStack)){
            List<SpellModifier> mods = SpellUtils.getModifiersForStage(spellStack, -1);
            for (SpellModifier mod : mods) if (mod instanceof Color) color = (int)mod.getModifier(SpellModifiers.COLOR, null, null, null, spellStack.getTag());
        }
//        Affinity aff = AffinityShiftUtils.getMainShiftForStack(spellStack);
//        if (aff.equals(Affinity.LIGHTNING)) ArsMagica2.proxy.particleManager.BoltFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, 1, color);
//        else {
//            if (color == -1) color = aff.getColor();
//            ArsMagica2.proxy.particleManager.BeamFromPointToPoint(world, startX, startY, startZ, endX, endY, endZ, color);
//        }
    }
}
