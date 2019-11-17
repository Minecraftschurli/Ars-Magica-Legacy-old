package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public final class SpellHelper {
    public static SpellCastResult applyStageToGround(ItemStack stack, LivingEntity caster, World world, int blockX, int blockY, int blockZ, Direction blockFace, double impactX, double impactY, double impactZ, int stage, boolean consumeMBR){
        SpellShape stageShape = SpellUtils.getShapeForStage(stack, 0);
        if (stageShape == null){
            return SpellCastResult.MALFORMED_SPELL_STACK;
        }

        SpellComponent[] components = SpellUtils.getComponentsForStage(stack, 0);

        for (SpellComponent component : components){

            //special logic for spell sealed doors
            /*if (ModBlocks..spellSealedDoor.applyComponentToDoor(world, component, blockX, blockY, blockZ))
                continue;*/

            if (component.applyEffectBlock(stack, world, new BlockPos(blockX, blockY, blockZ), blockFace, new Vec3d(impactX, impactY, impactZ), caster)){
                if (world.isRemote){
                    int color = -1;
                    if (SpellUtils.modifierIsPresent(SpellModifier.Type.COLOR, stack, 0)){
                        SpellModifier[] mods = SpellUtils.getModifiersForStage(stack, 0);
                        int ordinalCount = 0;
                        for (SpellModifier mod : mods){
                            if (mod instanceof Color){
                                byte[] meta = SpellUtils.getModifierMetadataFromStack(stack, mod, 0, ordinalCount++);
                                color = (int)mod.getModifier(SpellModifier.Type.COLOR, null, null, null, meta);
                            }
                        }
                    }
                    component.spawnParticles(world, new Vec3d(blockX, blockY, blockZ), caster, caster, world.rand, color);
                }
                /*if (consumeMBR)
                    SpellUtils.doAffinityShift(caster, component, stageShape);*/
            }
        }

        return SpellCastResult.SUCCESS;
    }

    public static SpellCastResult applyStageToEntity(ItemStack stack, LivingEntity caster, World world, Entity target, int stage, boolean shiftAffinityAndXP){
        SpellShape stageShape = SpellUtils.getShapeForStage(stack, 0);
        if (stageShape == null) return SpellCastResult.MALFORMED_SPELL_STACK;

        /*if ((!AMCore.config.getAllowCreativeTargets()) && target instanceof ServerPlayerEntity && ((ServerPlayerEntity) target).isCreative()) {
            return SpellCastResult.EFFECT_FAILED;
        }*/

        SpellComponent[] components = SpellUtils.getComponentsForStage(stack, 0);

        boolean appliedOneComponent = false;

        for (SpellComponent component : components){

            /*if (SkillTreeManager.isSkillDisabled(component))
                continue;*/

            if (component.applyEffectEntity(stack, world, caster, target)){
                appliedOneComponent = true;
                if (world.isRemote){
                    int color = -1;
                    if (SpellUtils.modifierIsPresent(SpellModifier.Type.COLOR, stack, 0)){
                        SpellModifier[] mods = SpellUtils.getModifiersForStage(stack, 0);
                        int ordinalCount = 0;
                        for (SpellModifier mod : mods){
                            if (mod instanceof Color){
                                byte[] meta = SpellUtils.getModifierMetadataFromStack(stack, mod, 0, ordinalCount++);
                                color = (int)mod.getModifier(SpellModifier.Type.COLOR, null, null, null, meta);
                            }
                        }
                    }
                    component.spawnParticles(world, new Vec3d(target.posX, target.posY + target.getEyeHeight(), target.posZ), caster, target, world.rand, color);
                }
                /*if (shiftAffinityAndXP)
                    SpellUtils.doAffinityShift(caster, component, stageShape);*/
            }
        }

        if (appliedOneComponent)
            return SpellCastResult.SUCCESS;
        else
            return SpellCastResult.EFFECT_FAILED;
    }
}
