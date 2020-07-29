package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import java.util.List;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.objects.entity.SpellProjectileEntity;
import minecraftschurli.arsmagicalegacy.objects.particle.SimpleParticleData;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class AoE extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        double radius = SpellUtil.modifyDoubleAdd(1, stack, caster, target, world, SpellModifiers.RADIUS);
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius), entity -> entity.getType() != EntityType.ITEM);
        boolean appliedToAtLeastOneEntity = false;
        for (Entity e : entities) {
            if (e == caster || e instanceof SpellProjectileEntity) continue;
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null)
                e = ((EnderDragonPartEntity) e).dragon;
            if (SpellUtil.applyStageEntity(stack, caster, world, e, giveXP) == SpellCastResult.SUCCESS)
                appliedToAtLeastOneEntity = true;
        }
        BlockPos pos = new BlockPos(x, y, z);
        if (appliedToAtLeastOneEntity) {
            addParticles(stack, caster, target, world, pos, y + 1, side);
            return SpellCastResult.SUCCESS;
        }
        if (side != null) {
            switch (side.getAxis()) {
                case Y:
                    for (int i = (int) -Math.floor(radius); i <= radius; i++) {
                        for (int j = (int) -Math.floor(radius); j <= radius; j++) {
                            BlockPos lookPos = pos.add(i, 0, j);
                            if (world.isAirBlock(lookPos)) continue;
                            SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, side, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                            if (result != SpellCastResult.SUCCESS) return result;
                        }
                    }
                    break;
                case Z:
                    for (int i = (int) -Math.floor(radius); i <= radius; i++) {
                        for (int j = (int) -Math.floor(radius); j <= radius; j++) {
                            BlockPos lookPos = pos.add(i, j, 0);
                            if (world.isAirBlock(lookPos)) continue;
                            SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, side, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                            if (result != SpellCastResult.SUCCESS) return result;
                        }
                    }
                    break;
                case X:
                    for (int i = (int) -Math.floor(radius); i <= radius; i++) {
                        for (int j = (int) -Math.floor(radius); j <= radius; j++) {
                            BlockPos lookPos = pos.add(0, j, i);
                            if (world.isAirBlock(lookPos)) continue;
                            SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, side, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                            if (result != SpellCastResult.SUCCESS) return result;
                        }
                    }
                    break;
            }
        } else {
            int gravityMagnitude = SpellUtil.countModifiers(SpellModifiers.GRAVITY, stack);
            for (int i = (int) -Math.floor(radius); i <= radius; i++) {
                for (int j = (int) -Math.floor(radius); j <= radius; j++) {
                    BlockPos lookPos = pos.add(i, 0, j);
                    if (gravityMagnitude > 0) {
                        for (int searchDist = 0; world.isAirBlock(lookPos) && searchDist < gravityMagnitude; searchDist++) {
                            lookPos = lookPos.down();
                        }
                    }
                    if (world.isAirBlock(lookPos)) continue;
                    SpellCastResult result = SpellUtil.applyStageBlock(stack, caster, world, lookPos, Direction.UP, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
                    if (result != SpellCastResult.SUCCESS) return result;
                }
            }
        }
        addParticles(stack, caster, target, world, pos, radius, side);
        return SpellCastResult.SUCCESS;
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
        return Math.abs(2 * radius + 2);
    }

    private void addParticles(ItemStack stack, LivingEntity caster, LivingEntity target, World world, BlockPos pos, double radius, Direction side) {
        int color = 0xFFFFFF;
        for (SpellModifier mod : SpellUtil.getModifiers(stack, -1)) {
            if (mod instanceof Color) {
                color = (int) mod.getModifier(SpellModifiers.COLOR, caster, target, world, stack.getTag());
            }
        }
        color += 0xFF000000;
        double x, y, z;
        for (int i = 0; i < 360; i += 10) {
            double sin = (radius * Math.sin(Math.toRadians(i))) + 0.5;
            double cos = (radius * Math.cos(Math.toRadians(i))) + 0.5;
            switch (side) {
                case NORTH:
                    x = pos.getX() + cos;
                    y = pos.getY() + sin;
                    z = pos.getZ();
                    break;
                case SOUTH:
                    x = pos.getX() + cos;
                    y = pos.getY() + sin;
                    z = pos.getZ() + 1;
                    break;
                case EAST:
                    x = pos.getX();
                    y = pos.getY() + sin;
                    z = pos.getZ() + cos;
                    break;
                case WEST:
                    x = pos.getX() + 1;
                    y = pos.getY() + sin;
                    z = pos.getZ() + cos;
                    break;
                case DOWN:
                    x = pos.getX() + sin;
                    y = pos.getY();
                    z = pos.getZ() + cos;
                    break;
                case UP:
                    x = pos.getX() + sin;
                    y = pos.getY() + 1;
                    z = pos.getZ() + cos;
                    break;
                default:
                    x = pos.getX();
                    y = pos.getY();
                    z = pos.getZ();
                    break;
            }
            ((ServerWorld) world).spawnParticle(new SimpleParticleData(ModParticles.LENS_FLARE.get(), color), x, y, z, 0, 0, 0, 0, 0);
        }
    }
}
