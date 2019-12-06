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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class LifeTap extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(pos).getBlock().equals(Blocks.SPAWNER)) {
//            boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, pos);
//            if (hasMatch) {
//                if (!world.isRemote) {
//                    world.setBlockToAir(pos);
//                    RitualShapeHelper.instance.consumeReagents(this, world, pos);
//                    RitualShapeHelper.instance.consumeShape(this, world, pos);
//                    ItemEntity item = new ItemEntity(world);
//                    item.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
//                    item.setItemEntityStack(new ItemStack(ModBlocks.inertSpawner));
//                    world.addEntity(item);
//                }
//                return true;
//            }
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (!world.isRemote) {
            double damage = SpellUtils.getModifiedDoubleMul(2, stack, caster, target, world, SpellModifiers.DAMAGE);
////            IEntityExtension casterProperties = EntityExtension.For(caster);
////            float manaRefunded = (float) (((damage * 0.01)) * casterProperties.getMaxMana());
////            if ((caster).attackEntityFrom(DamageSource.outOfWorld, (int) Math.floor(damage))) casterProperties.setCurrentMana(casterProperties.getCurrentMana() + manaRefunded);
//            else return false;
        }
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 0;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(2, 2, 2);
//                particle.setMaxAge(15);
//                particle.setParticleScale(0.1f);
//                particle.AddParticleController(new ParticleApproachEntity(particle, target, 0.1, 0.1, 1, false));
//                if (rand.nextBoolean()) particle.setRGBColorF(0.4f, 0.1f, 0.5f);
//                else particle.setRGBColorF(0.1f, 0.5f, 0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.LIFE, Affinity.ENDER);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLACK_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get())),
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
