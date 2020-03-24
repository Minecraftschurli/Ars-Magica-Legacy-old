package minecraftschurli.arsmagicalegacy.objects.spell.component;

import java.util.EnumSet;
import java.util.Random;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireDamage extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
//        if (block == ModBlocks.obelisk) {
//            if (RitualShapeHelper.instance.matchesRitual(this, world, pos)) {
//                if (!world.isRemote) {
////                    RitualShapeHelper.instance.consumeReagents(this, world, pos);
////                    RitualShapeHelper.instance.consumeShape(this, world, pos);
////                    world.setBlockState(pos, ModBlocks.blackAurem.getDefaultState());
////                    PowerNodeRegistry.For(world).registerPowerNode((IPowerNode<?>) world.getTileEntity(pos));
//                }
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        float baseDamage = 6;
        double damage = SpellUtils.getModifiedDoubleAdd(baseDamage, stack, caster, target, world, SpellModifiers.DAMAGE);
        return target.isImmuneToFire() || target instanceof ZombiePigmanEntity || target instanceof GhastEntity || target instanceof WitherSkeletonEntity || target instanceof BlazeEntity;//SpellUtils.attackTargetSpecial(stack, target, DamageSources.causeFireDamage(caster), SpellUtils.modifyDamage(caster, (float) damage));
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 120;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "explosion_2", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 0.5, 1);
//                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, rand.nextDouble() * 0.2, rand.nextDouble() * 0.2 - 0.1);
//                particle.setAffectedByGravity();
//                particle.setDontRequireControllers();
//                particle.setMaxAge(5);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.FIRE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(Items.FLINT_AND_STEEL))
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.01f;
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
                new ItemStack(ModItems.SUNSTONE.get())
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
