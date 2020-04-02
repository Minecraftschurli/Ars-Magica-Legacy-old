package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class RandomTeleport extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        Vec3d origin = new Vec3d(target.getPosX(), target.getPosY(), target.getPosZ()).add(new Vec3d((world.rand.nextDouble() - 0.5) * SpellUtil.modifyDoubleMul(9, stack, caster, target, world, SpellModifiers.RANGE), (world.rand.nextDouble() - 0.5) * SpellUtil.modifyDoubleMul(9, stack, caster, target, world, SpellModifiers.RANGE), (world.rand.nextDouble() - 0.5) * SpellUtil.modifyDoubleMul(9, stack, caster, target, world, SpellModifiers.RANGE)));
        if (target instanceof LivingEntity) {
            EnderTeleportEvent event = new EnderTeleportEvent((LivingEntity) target, origin.getX(), origin.getY(), origin.getZ(), 0);
            if (MinecraftForge.EVENT_BUS.post(event)) return false;
            origin = new Vec3d(event.getTargetX(), event.getTargetY(), event.getTargetZ());
        }
        double x = target.getPosX();
        double y = target.getPosY();
        double z = target.getPosZ();
        target.setPosition(origin.getX(), origin.getY(), origin.getZ());
        boolean locationValid = false;
        BlockPos pos = target.getPosition();
        Block l;
        boolean targetBlockIsSolid = false;
        while (!targetBlockIsSolid && pos.getY() > 0) {
            l = target.world.getBlockState(pos.down()).getBlock();
            if (l != Blocks.AIR && l.isSolid(target.world.getBlockState(pos.down())))
                targetBlockIsSolid = true;
            else {
                target.setPosition(target.getPosX(), target.getPosY() - 1, target.getPosZ());
                pos = pos.down();
            }
        }
        if (targetBlockIsSolid) {
            target.setPosition(target.getPosX(), target.getPosY(), target.getPosZ());
            if (target.world.getEmptyCollisionShapes(target, target.getBoundingBox(), new HashSet<>()).toArray().length == 0)
                locationValid = true;
        }
        if (!locationValid) {
            target.setPosition(x, y, z);
            return false;
        }
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 52.5f;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(Tags.Items.ENDER_PEARLS),
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get()))
        };
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        world.addParticle(ParticleTypes.PORTAL, target.getPosX() + (rand.nextDouble() - 0.5D) * target.getWidth(), target.getPosY() + rand.nextDouble() * target.getHeight() - 0.25D, target.getPosZ() + (rand.nextDouble() - 0.5D) * target.getWidth(), (rand.nextDouble() - 0.5D) * 2, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2);
    }
}
