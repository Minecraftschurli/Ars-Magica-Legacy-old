package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Set;

public final class Rift extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        if (world.isRemote) return false;
//        EntityRiftStorage storage = new EntityRiftStorage(world);
//        int storageLevel = Math.min(1 + SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack), 3);
//        storage.setStorageLevel(storageLevel);
//        switch (blockFace) {
//            case UP:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 1.5, blockPos.getZ() + 0.5);
//                break;
//            case NORTH:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() - 1.5);
//                break;
//            case SOUTH:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 1.5);
//                break;
//            case WEST:
//                storage.setPosition(blockPos.getX() - 1.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
//                break;
//            case EAST:
//                storage.setPosition(blockPos.getX() + 1.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
//                break;
//            default:
//                storage.setPosition(blockPos.getX() + 0.5, blockPos.getY() - 1.5, blockPos.getZ() + 0.5);
//                break;
//        }
//        world.addEntity(storage);
        return false;//true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        return SpellUtil.doBlockWithEntity(this, stack, world, caster, target);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.NONE.get());
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 90;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.ENDER_ESSENCE.get()),
                new ItemStack(ModItems.MONSTER_FOCUS.get())
        };
    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.corruption;
//    }
}
