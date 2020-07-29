package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModAffinities;
import minecraftschurli.arsmagicalegacy.init.ModParticles;
import minecraftschurli.arsmagicalegacy.util.ParticleUtil;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Disarm extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        double damage = SpellUtil.modifyIntMul(1, stack, caster, target, world, SpellModifiers.DAMAGE);
        if (!world.isRemote) {
            if (target instanceof PlayerEntity && ((ServerPlayerEntity) target).server.isPVPEnabled())
                return false;
            ItemEntity item = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ());
            ItemStack dropstack = ItemStack.EMPTY;
            if (target instanceof MobEntity) dropstack = ((MobEntity) target).getHeldItemMainhand().copy();
            if (dropstack.getMaxDamage() > 0)
                dropstack.setDamage((int) Math.floor(dropstack.getMaxDamage() * (0.8f + (world.rand.nextFloat() * 0.19f))));
            item.setItem(dropstack);
            item.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
            item.setPickupDelay(15);
            world.addEntity(item);
            return true;
        }
        if (target instanceof PlayerEntity && !((PlayerEntity) target).getHeldItemOffhand().isEmpty() && !target.world.isRemote && (world.rand.nextInt(9) + 1 <= damage) /*&& EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((PlayerEntity) target).getHeldItemOffhand()) <= 0*/) {
            ItemEntity item = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ());
            ItemStack dropstack = ((PlayerEntity) target).getHeldItemOffhand().copy();
            item.setItem(dropstack);
            item.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
            item.setDefaultPickupDelay();
            world.addEntity(item);
            ((PlayerEntity) target).setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
            return true;
        }
        if (target instanceof PlayerEntity && !((PlayerEntity) target).getHeldItemMainhand().isEmpty() && !target.world.isRemote) {
            ((PlayerEntity) target).dropItem(((PlayerEntity) target).getHeldItemMainhand(), true);
            return true;
        } else if (target instanceof EndermanEntity) {
            BlockState blockID = ((EndermanEntity) target).getHeldBlockState();
            if (blockID != null) {
                ((EndermanEntity) target).setHeldBlockState(null);
                ItemStack dropstack = new ItemStack(blockID.getBlock());
                ItemEntity item = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ());
                item.setItem(dropstack);
                item.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
                world.addEntity(item);
            }
            ((MobEntity) target).setAttackTarget(caster);
            return true;
        } else if (target instanceof MobEntity) {
            ((MobEntity) target).getHeldItemMainhand();
            target.setItemStackToSlot(MobEntity.getSlotForItemStack(stack), ItemStack.EMPTY);
            ((MobEntity) target).setAttackTarget(caster);
            return true;
        }
        return false;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.NONE.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 130;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; i++)
            ParticleUtil.addParticle(world, target, ModParticles.SHINY, colorModifier, rand.nextBoolean() ? 0xb2b217 : 0x17b217, x + rand.nextInt(2) - 1, y + rand.nextInt(4) - 2, z + rand.nextInt(2) - 1, 0, -0.5f, 0);
    }
}
