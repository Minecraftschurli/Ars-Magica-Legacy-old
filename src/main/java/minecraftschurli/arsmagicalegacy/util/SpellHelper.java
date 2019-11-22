package minecraftschurli.arsmagicalegacy.util;

import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellShape;
import minecraftschurli.arsmagicalegacy.event.SpellCastingEvent;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import minecraftschurli.arsmagicalegacy.objects.spell.modifier.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

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

        if (appliedOneComponent) return SpellCastResult.SUCCESS;
        return SpellCastResult.EFFECT_FAILED;
    }

    public static SpellCastResult applyStackStage(ItemStack stack, LivingEntity caster, LivingEntity target, Vec3d pos, Direction side, World world, boolean consumeMBR, boolean giveXP, int ticksUsed){

        /*if (caster.isPotionActive(BuffList.silence.id))
            return SpellCastResult.SILENCED;*/

        //ItemStack parsedStack = SpellUtils.constructSpellStack(stack);

        if (SpellUtils.numStages(stack) == 0){
            return SpellCastResult.SUCCESS;
        }
        SpellShape shape = SpellUtils.getShapeForStage(stack, 0);
        SpellItem item = (SpellItem) stack.getItem();

        if (!(caster instanceof PlayerEntity)){
            consumeMBR = false;
        }

        SpellCastingEvent.Pre checkEvent = null;
        if (consumeMBR){
            checkEvent = preSpellCast(stack, caster, false);
            if (checkEvent.castResult != SpellCastResult.SUCCESS){
                if (checkEvent.castResult == SpellCastResult.NOT_ENOUGH_MANA && caster.world.isRemote){
                    //AMCore.proxy.flashManaBar();
                }
                SpellCastingEvent.Post event = new SpellCastingEvent.Post(stack, (SpellItem) stack.getItem(), caster, checkEvent.manaCost, checkEvent.burnout, false, checkEvent.castResult);
                MinecraftForge.EVENT_BUS.post(event);

                return checkEvent.castResult;
            }
        }

        SpellCastResult result = SpellCastResult.MALFORMED_SPELL_STACK;

        if (shape != null){
            result = shape.beginStackStage(item, stack, caster, target, world, pos, side, giveXP, ticksUsed);

            /*if (!world.isRemote){
                AMDataWriter writer = new AMDataWriter();
                writer.add(stack);
                writer.add(caster.getEntityId());
                if (target != null){
                    writer.add(true);
                    writer.add(target.getEntityId());
                }else{
                    writer.add(false);
                }
                writer.add(pos);
                writer.add(side);
                writer.add(ticksUsed);

                NetworkHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.x, pos.y, pos.z, 32, world.dimension.getType())), writer.generate());
            }*/
        }

        float manaCost = 0;
        float burnout = 0;

        if (consumeMBR){
            manaCost = checkEvent.manaCost;
            burnout = checkEvent.burnout;

            if (result == SpellCastResult.SUCCESS_REDUCE_MANA){
                result = SpellCastResult.SUCCESS;
                manaCost *= 0.2f;
                burnout *= 0.2f;
            }
        }

        if (result == SpellCastResult.SUCCESS){
            if (consumeMBR){
                MagicHelper.use((PlayerEntity) caster, manaCost, burnout);
            }
            /*if (world.isRemote){
                String sfx = shape.getSoundForAffinity(SpellUtils.instance.mainAffinityFor(parsedStack), parsedStack, null);
                if (sfx != null){
                    if (!shape.isChanneled()){
                        world.playSound(caster.posX, caster.posY, caster.posZ, sfx, 0.4f, world.rand.nextFloat() * 0.1F + 0.9F, false);
                    }else{
                        //SoundHelper.instance.loopSound(world, (float)x, (float)y, (float)z, sfx, 0.6f);
                    }
                }
            }*/
        }

        SpellCastingEvent.Post event = new SpellCastingEvent.Post(stack, (SpellItem) stack.getItem(), caster, manaCost, burnout, false, result);
        MinecraftForge.EVENT_BUS.post(event);

        return result;
    }

    private static SpellCastingEvent.Pre preSpellCast(ItemStack stack, LivingEntity caster, boolean isChanneled){

        SpellRequirements reqs = SpellUtils.getSpellRequirements(stack, caster);

        float manaCost = reqs.manaCost;
        float burnout = reqs.burnout;
        /*ArrayList<ItemStack> reagents = reqs.reagents;

        ManaCostEvent mce = new ManaCostEvent(stack, caster, manaCost, burnout);

        MinecraftForge.EVENT_BUS.post(mce);

        manaCost = mce.manaCost;
        burnout = mce.burnout;*/

        SpellCastingEvent.Pre event = new SpellCastingEvent.Pre(stack, (SpellItem)stack.getItem(), caster, manaCost, burnout, isChanneled);

        /*if (MinecraftForge.EVENT_BUS.post(event)){
            event.castResult = SpellCastResult.EFFECT_FAILED;
            return event;
        }

        event.castResult = SpellCastResult.SUCCESS;

        if (!SpellUtils.casterHasAllReagents(caster, reagents))
            event.castResult = SpellCastResult.REAGENTS_MISSING;
        if (!SpellUtils.casterHasMana(caster, manaCost))
            event.castResult = SpellCastResult.NOT_ENOUGH_MANA;*/

        return event;
    }
}
