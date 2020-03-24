package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class RandomTeleport extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        Vec3d rLoc = getRandomTeleportLocation(world, stack, caster, target);
        return teleportTo(rLoc.getX(), rLoc.getY(), rLoc.getZ(), target);
    }

    private Vec3d getRandomTeleportLocation(World world, ItemStack stack, LivingEntity caster, Entity target) {
        Vec3d origin = new Vec3d(target.getPosX(), target.getPosY(), target.getPosZ());
        float maxDist = 9;
        maxDist = (float) SpellUtils.getModifiedDoubleMul(maxDist, stack, caster, target, world, SpellModifiers.RANGE);
        origin = origin.add(new Vec3d((world.rand.nextDouble() - 0.5) * maxDist, (world.rand.nextDouble() - 0.5) * maxDist, (world.rand.nextDouble() - 0.5) * maxDist));
        return origin;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.RANGE);
    }

    private boolean teleportTo(double par1, double par3, double par5, Entity target) {
        if (target instanceof LivingEntity) {
            EnderTeleportEvent event = new EnderTeleportEvent((LivingEntity) target, par1, par3, par5, 0);
            if (MinecraftForge.EVENT_BUS.post(event)) return false;
            par1 = event.getTargetX();
            par3 = event.getTargetY();
            par5 = event.getTargetZ();
        }
        double d3 = target.getPosX();
        double d4 = target.getPosY();
        double d5 = target.getPosZ();
        target.setPosition(par1, par3, par5);
        boolean locationValid = false;
        BlockPos pos = target.getPosition();
        Block l;
        target.world.getBlockState(pos);
        boolean targetBlockIsSolid = false;
        while (!targetBlockIsSolid && pos.getY() > 0) {
            l = target.world.getBlockState(pos.down()).getBlock();
            if (l != Blocks.AIR && l.isOpaqueCube(target.world.getBlockState(pos.down()), target.world, pos))
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
            target.setPosition(d3, d4, d5);
            return false;
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 52.5f;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        world.addParticle(ParticleTypes.PORTAL, target.getPosX() + (rand.nextDouble() - 0.5D) * target.getWidth(), target.getPosY() + rand.nextDouble() * target.getHeight() - 0.25D, target.getPosZ() + (rand.nextDouble() - 0.5D) * target.getWidth(), (rand.nextDouble() - 0.5D) * 2, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2);
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.ENDER.get());
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL)),
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}
