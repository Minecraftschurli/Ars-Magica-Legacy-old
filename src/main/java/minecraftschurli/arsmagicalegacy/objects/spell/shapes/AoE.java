package minecraftschurli.arsmagicalegacy.objects.spell.shapes;

import minecraftschurli.arsmagicalegacy.api.spellsystem.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.entity.*;
import minecraftschurli.arsmagicalegacy.objects.item.*;
import minecraftschurli.arsmagicalegacy.objects.spell.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.dragon.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class AoE extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        int radius = 0;
        int stages = SpellUtils.numStages(spellStack);
        for (int i = SpellUtils.currentStage(spellStack); i < stages; ++i) {
            if (!SpellUtils.getShapeForStage(spellStack, i).equals(this)) continue;
            List<SpellModifier> mods = SpellUtils.getModifiersForStage(spellStack, i);
            for (SpellModifier modifier : mods)
                if (modifier.getAspectsModified().contains(SpellModifiers.RADIUS)) radius++;
        }
        return 2 * (radius + 1);
    }

    @Override
    public boolean isTerminusShape() {
        return true;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public SpellCastResult beginStackStage(SpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        double radius = SpellUtils.getModifiedDoubleAdd(1, stack, caster, target, world, SpellModifiers.RADIUS);
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
        boolean appliedToAtLeastOneEntity = false;
        for (Entity e : entities) {
            if (e == caster || e instanceof SpellProjectileEntity) continue;
            if (e instanceof EnderDragonPartEntity && ((EnderDragonPartEntity) e).dragon != null) e = ((EnderDragonPartEntity) e).dragon;
            if (SpellUtils.applyStageToEntity(stack, caster, world, e, giveXP) == SpellCastResult.SUCCESS) appliedToAtLeastOneEntity = true;
        }
        BlockPos pos = new BlockPos(x, y, z);
        if (side != null) {
            switch (side) {
                case UP:
                case DOWN:
                    if (world.isRemote) spawnAoEParticles(stack, caster, world, x + 0.5f, y + ((side.equals(Direction.DOWN)) ? 0.5f : (target != null ? target.getEyeHeight() : -2.0f)), z + 0.5f, (int) radius);
                    int gravityMagnitude = SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack);
                    return applyStageHorizontal(stack, caster, world, pos, side, (int) Math.floor(radius), gravityMagnitude, giveXP);
                case NORTH:
                case SOUTH:
                    if (world.isRemote) spawnAoEParticles(stack, caster, world, x + 0.5f, y - 1, z + 0.5f, (int) radius);
                    return applyStageVerticalZ(stack, caster, world, pos, side, (int) Math.floor(radius), giveXP);
                case EAST:
                case WEST: 
                    if (world.isRemote) spawnAoEParticles(stack, caster, world, x + 0.5f, y - 1, z + 0.5f, (int) radius);
                    return applyStageVerticalX(stack, caster, world, pos, side, (int) Math.floor(radius), giveXP);
            }
        } else {
            if (world.isRemote) spawnAoEParticles(stack, caster, world, x, y - 1, z, (int) radius);
            int gravityMagnitude = SpellUtils.countModifiers(SpellModifiers.GRAVITY, stack);
            return applyStageHorizontal(stack, caster, world, pos, null, (int) Math.floor(radius), gravityMagnitude, giveXP);
        }
        if (appliedToAtLeastOneEntity) {
            if (world.isRemote) spawnAoEParticles(stack, caster, world, x, y + 1, z, (int) radius);
            return SpellCastResult.SUCCESS;
        }
        return SpellCastResult.EFFECT_FAILED;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.MOONSTONE_ORE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.TNT)),
                new EssenceSpellIngredient(EssenceType.ANY)
//                AffinityShiftUtils.getEssenceForAffinity(Affinity.AIR),
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return null;
    }

    private void spawnAoEParticles(ItemStack stack, LivingEntity caster, World world, double x, double y, double z, int radius) {
//        String pfxName = AMParticleDefs.getParticleForAffinity(AffinityShiftUtils.getMainShiftForStack(stack));
//        float speed = 0.08f * radius;
//        int color = 0xFFFFFF;
//        if (SpellUtils.modifierIsPresent(SpellModifiers.COLOR, stack)) {
//            List<SpellModifier> mods = SpellUtils.getModifiersForStage(stack, -1);
//            for (SpellModifier mod : mods) if (mod instanceof Colour) color = (int) mod.getModifier(SpellModifiers.COLOR, null, null, null, stack.getTagCompound());
//        }
//        for (int i = 0; i < 360; i += ArsMagica2.config.FullGFX() ? 20 : ArsMagica2.config.LowGFX() ? 40 : 60) {
//            AMParticle effect = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, pfxName, x, y + 1.5f, z);
//            if (effect != null) {
//                effect.setIgnoreMaxAge(true);
//                effect.AddParticleController(new ParticleMoveOnHeading(effect, i, 0, speed, 1, false));
//                effect.setRGBColorI(color);
//                effect.AddParticleController(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.05f).setKillParticleOnFinish(true));
//                effect.AddParticleController(
//                        new ParticleLeaveParticleTrail(effect, pfxName, false, 5, 1, false)
//                                .addControllerToParticleList(new ParticleFadeOut(effect, 1, false).setFadeSpeed(0.1f).setKillParticleOnFinish(true))
//                                .setParticleRGB_I(color)
//                                .addRandomOffset(0.2f, 0.2f, 0.2f)
//                );
//            }
//        }
    }

    private SpellCastResult applyStageHorizontal(ItemStack stack, LivingEntity caster, World world, BlockPos pos, Direction face, int radius, int gravityMagnitude, boolean giveXP) {
        for (int i = -radius; i <= radius; ++i) for (int j = -radius; j <= radius; ++j) {
            BlockPos lookPos = pos.add(i, 0, j);
            int searchDist = 0;
            if (gravityMagnitude > 0) while (world.isAirBlock(lookPos) && searchDist < gravityMagnitude) {
                pos.down();
                searchDist++;
            }
            if (world.isAirBlock(lookPos)) continue;
            SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, lookPos, face == null ? Direction.UP : face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
            if (result != SpellCastResult.SUCCESS) return result;
        }
        return SpellCastResult.SUCCESS;
    }

    private SpellCastResult applyStageVerticalX(ItemStack stack, LivingEntity caster, World world, BlockPos pos, Direction face, int radius, boolean giveXP) {
        for (int i = -radius; i <= radius; ++i) for (int j = -radius; j <= radius; ++j) {
            BlockPos lookPos = pos.add(0, j, i);
            if (world.isAirBlock(lookPos)) continue;
            SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, lookPos, face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
            if (result != SpellCastResult.SUCCESS) return result;
        }
        return SpellCastResult.SUCCESS;
    }

    private SpellCastResult applyStageVerticalZ(ItemStack stack, LivingEntity caster, World world, BlockPos pos, Direction face, int radius, boolean giveXP) {
        for (int i = -radius; i <= radius; ++i) for (int j = -radius; j <= radius; ++j) {
            BlockPos lookPos = pos.add(i, j, 0);
            if (world.isAirBlock(lookPos)) continue;
            SpellCastResult result = SpellUtils.applyStageToGround(stack, caster, world, lookPos, face, lookPos.getX(), lookPos.getY(), lookPos.getZ(), giveXP);
            if (result != SpellCastResult.SUCCESS) return result;
        }
        return SpellCastResult.SUCCESS;
    }
}
