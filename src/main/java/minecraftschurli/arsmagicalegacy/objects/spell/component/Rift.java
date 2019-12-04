package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.entity.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Rift extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(blockPos).getBlock().equals(Blocks.MOB_SPAWNER)) {
            if (RitualShapeHelper.instance.matchesRitual(this, world, blockPos)) {
                if (!world.isRemote) {
                    world.setBlockToAir(blockPos);
                    RitualShapeHelper.instance.consumeReagents(this, world, blockPos);
                    RitualShapeHelper.instance.consumeShape(this, world, blockPos);
                    ItemEntity item = new ItemEntity(world);
                    item.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                    item.setItemEntityStack(new ItemStack(ModBlocks.inertSpawner));
                    world.addEntity(item);
                } else {
                }
                return true;
            }
        }
        if (world.isRemote)
            return true;
        EntityRiftStorage storage = new EntityRiftStorage(world);
        int storageLevel = Math.min(1 + SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack), 3);
        storage.setStorageLevel(storageLevel);
        switch (blockFace) {
            case UP:
                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5);
                break;
            case NORTH:
                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() - 1.5);
                break;
            case SOUTH:
                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 1.5);
                break;
            case WEST:
                storage.setPosition(blockPos.getX() - 1.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                break;
            case EAST:
                storage.setPosition(blockPos.getX() + 1.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                break;
            default:
                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() - 1.5, blockPos.getZ() + 0.5);
                break;
        }
        world.addEntity(storage);
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
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NONE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.WHITE_RUNE.get()),
                new ItemStack(ModItems.PURPLE_RUNE.get()),
                Blocks.CHEST,
                Items.ENDER_EYE
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        return RitualShapeHelper.instance.corruption;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                new ItemStack(ModItems.mobFocus),
                new ItemStack(ModItems.ENDER_ESSENCE.get())
        };
    }

    @Override
    public int getReagentSearchRadius() {
        return 3;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getResult() {
        return new ItemStack(ModBlocks.inertSpawner);
    }
}