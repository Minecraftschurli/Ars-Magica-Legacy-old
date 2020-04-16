package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.objects.entity.WaveEntity;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class Wave extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (world.isRemote) return SpellCastResult.SUCCESS;
        WaveEntity entity = new WaveEntity(world);
        entity.setPosition(x, y + 1, z);
        entity.noClip = SpellUtil.hasModifier(SpellModifiers.PIERCING, stack);
        entity.setGravity(SpellUtil.countModifiers(SpellModifiers.GRAVITY, stack) * 0.5f);
        entity.setRadius((float) SpellUtil.modifyDoubleAdd(1, stack, caster, target, world, SpellModifiers.RADIUS));
        entity.setTicks(SpellUtil.modifyIntMul(20, stack, caster, target, world, SpellModifiers.DURATION));
        entity.setOwner(caster);
        entity.setStack(stack);
        world.addEntity(entity);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RADIUS, SpellModifiers.GRAVITY, SpellModifiers.DURATION, SpellModifiers.COLOR, SpellModifiers.SPEED, SpellModifiers.PIERCING, SpellModifiers.TARGET_NONSOLID_BLOCKS);
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return true;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 3;
    }
}
