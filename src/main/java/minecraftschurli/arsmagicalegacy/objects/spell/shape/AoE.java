package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public final class AoE extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        double radius = SpellUtil.modifyDoubleAdd(1, stack, caster, target, world, SpellModifiers.RADIUS);
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
        boolean appliedToAtLeastOneEntity = false;
        for (Entity e : entities) {
            if (e == caster || e instanceof SpellProjectileEntity) continue;
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                e = ((EnderDragonPartEntity) e).dragon;
            if (SpellUtil.applyStageEntity(stack, caster, world, e, giveXP) == SpellCastResult.SUCCESS)
                appliedToAtLeastOneEntity = true;
        }
        BlockPos pos = new BlockPos(x, y, z);
        if (side != null) {
            switch (side) {
                case UP:
                case DOWN:
                    if (world.isRemote)
                        spawnParticles(stack, world, x + 0.5f, y + ((side.equals(Direction.DOWN)) ? 0.5f : (target != null ? target.getEyeHeight() : -2)), z + 0.5f);
                    int gravityMagnitude = SpellUtil.countModifiers(SpellModifiers.GRAVITY, stack);
                    return applyHorizontal(stack, caster, world, pos, side, (int) Math.floor(radius), gravityMagnitude, giveXP);
                case NORTH:
                case SOUTH:
                    if (world.isRemote) spawnParticles(stack, world, x + 0.5f, y - 1, z + 0.5f);
                    for (int i = (int) -Math.floor(radius); i <= radius; i++)
                        for (int j = (int) -Math.floor(radius); j <= radius; j++) {
                            BlockPos lookPos = pos.add(i, j, 0);
                            if (world.isAirBlock(lookPos)) continue;
                            SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, side, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                            if (result != SpellCastResult.SUCCESS) return result;
                        }
                    return SpellCastResult.SUCCESS;
                case EAST:
                case WEST:
                    if (world.isRemote) spawnParticles(stack, world, x + 0.5f, y - 1, z + 0.5f);
                    for (int i = (int) -Math.floor(radius); i <= radius; i++)
                        for (int j = (int) -Math.floor(radius); j <= radius; j++) {
                            BlockPos lookPos = pos.add(i, j, 0);
                            if (world.isAirBlock(lookPos)) continue;
                            SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, side, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                            if (result != SpellCastResult.SUCCESS) return result;
                        }
                    return SpellCastResult.SUCCESS;
            }
        } else {
            if (world.isRemote) spawnParticles(stack, world, x, y - 1, z);
            int gravityMagnitude = SpellUtil.countModifiers(SpellModifiers.GRAVITY, stack);
            return applyHorizontal(stack, caster, world, pos, null, (int) Math.floor(radius), gravityMagnitude, giveXP);
        }
        if (appliedToAtLeastOneEntity) {
            if (world.isRemote) spawnParticles(stack, world, x, y + 1, z);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY);
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public boolean isTerminusShape() {
        return true;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        int radius = 0;
        int stages = SpellUtil.stageNum(spellStack);
        for (int i = SpellUtil.currentStage(spellStack); i < stages; i++) {
            if (!SpellUtil.getShape(spellStack, i).equals(this)) continue;
            List<SpellModifier> mods = SpellUtil.getModifiers(spellStack, i);
            for (SpellModifier modifier : mods)
                if (modifier.getAspectsModified().contains(SpellModifiers.RADIUS)) radius++;
        }
        return 2 * radius + 2;
    }

    private SpellCastResult applyHorizontal(ItemStack stack, LivingEntity caster, World world, BlockPos pos, Direction face, int radius, int gravityMagnitude, boolean giveXP) {
        for (int i = -radius; i <= radius; i++)
            for (int j = -radius; j <= radius; j++) {
                BlockPos lookPos = pos.add(i, 0, j);
                int searchDist = 0;
                if (gravityMagnitude > 0) while (world.isAirBlock(lookPos) && searchDist < gravityMagnitude) {
                    pos.down();
                    searchDist++;
                }
                if (world.isAirBlock(lookPos)) continue;
                SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, face == null ? Direction.UP : face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                if (result != SpellCastResult.SUCCESS) return result;
            }
        return SpellCastResult.SUCCESS;
    }

    private void spawnParticles(ItemStack stack, World world, double x, double y, double z) {
//        int color = 0xFFFFFF;
//        if (SpellUtil.hasModifier(SpellModifiers.COLOR, stack)) {
//            List<SpellModifier> mods = SpellUtil.getModifiers(stack, -1);
//            for (SpellModifier mod : mods)
//                if (mod instanceof Color)
//                    color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, stack.getTag());
//        }
//        for (int i = 0; i < 360; i += 60) RenderUtil.addParticle(world, ModParticles.LENS_FLARE.get(), color, x, y, z);
    }
}
