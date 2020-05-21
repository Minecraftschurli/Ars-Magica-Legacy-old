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
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public final class Transplace extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!world.isRemote && target != null && target.isAlive()) {
            double tPosX = target.getPosX();
            double tPosY = target.getPosY();
            double tPosZ = target.getPosZ();
            double cPosX = caster.getPosX();
            double cPosY = caster.getPosY();
            double cPosZ = caster.getPosZ();
            caster.setPositionAndUpdate(tPosX, tPosY, tPosZ);
            if (target instanceof LivingEntity) target.setPositionAndUpdate(cPosX, cPosY, cPosZ);
            else target.setPosition(cPosX, cPosY, cPosZ);
        }
//        if (target instanceof LivingEntity) ((LivingEntity) target).lookAt(caster, 180, 180);
        return true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModAffinities.ENDER.get());
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.02f;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 100;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    /*TODO move to ritual
    @Override
    public Set<ItemStack> getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.MAGE_BOOTS.get()),
                new ItemStack(ModItems.MAGE_CHESTPLATE.get()),
                new ItemStack(ModItems.MAGE_HELMET.get()),
                new ItemStack(ModItems.MAGE_LEGGINGS.get()),
                new ItemStack(ModItems.PLAYER_FOCUS.get()),
                new ItemStack(ModItems.PURIFIED_VINTEUM.get())
        };
    }*/

    //    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.ringedCross;
//    }
//
    @Override
    public void spawnParticles(ServerWorld world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; i++) {
            ParticleUtil.addParticle(world, target, ModParticles.SHINY, colorModifier, 0xff00000, x + rand.nextInt(2) - 1, y + rand.nextInt(2) - 1, z + rand.nextInt(2) - 1);
            ParticleUtil.addParticle(world, target, ModParticles.SHINY, colorModifier, 0x00000ff, x + rand.nextInt(2) - 1, y + rand.nextInt(2) - 1, z + rand.nextInt(2) - 1);
        }
    }
}
