package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class Rift extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(blockPos).getBlock().equals(Blocks.SPAWNER)) {
//            if (RitualShapeHelper.instance.matchesRitual(this, world, blockPos)) {
//                if (!world.isRemote) {
//                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
//                    RitualShapeHelper.instance.consumeReagents(this, world, blockPos);
//                    RitualShapeHelper.instance.consumeShape(this, world, blockPos);
//                    ItemEntity item = new ItemEntity(world, impactX, impactY, impactZ);
//                    item.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
//                    item.setItemStackToSlot(new ItemStack(ModBlocks.inertSpawner));
//                    world.addEntity(item);
//                }
//                return true;
//            }
        }
        if (world.isRemote) return true;
//        EntityRiftStorage storage = new EntityRiftStorage(world);
        int storageLevel = Math.min(1 + SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack), 3);
//        storage.setStorageLevel(storageLevel);
        switch (blockFace) {
            case UP:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5);
                break;
            case NORTH:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() - 1.5);
                break;
            case SOUTH:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 1.5);
                break;
            case WEST:
//                storage.setPosition(blockPos.getX() - 1.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                break;
            case EAST:
//                storage.setPosition(blockPos.getX() + 1.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                break;
            default:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() - 1.5, blockPos.getZ() + 0.5);
                break;
        }
//        world.addEntity(storage);
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 90;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.NONE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.WHITE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.PURPLE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.CHEST)),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_EYE))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.corruption;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.MONSTER_FOCUS.get()),
                new ItemStack(ModItems.ENDER_ESSENCE.get())
        };
    }

    //    @Override
//    public int getReagentSearchRadius() {
//        return 3;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}
