package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.extensions.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
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

public class LifeTap extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (world.getBlockState(pos).getBlock().equals(Blocks.MOB_SPAWNER)) {
            boolean hasMatch = RitualShapeHelper.instance.matchesRitual(this, world, pos);
            if (hasMatch) {
                if (!world.isRemote) {
                    world.setBlockToAir(pos);
                    RitualShapeHelper.instance.consumeReagents(this, world, pos);
                    RitualShapeHelper.instance.consumeShape(this, world, pos);
                    ItemEntity item = new ItemEntity(world);
                    item.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    item.setItemEntityStack(new ItemStack(ModBlocks.inertSpawner));
                    world.addEntity(item);
                } else {
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (!world.isRemote) {
            double damage = SpellUtils.getModifiedDoubleMul(2, stack, caster, target, world, SpellModifiers.DAMAGE);
            IEntityExtension casterProperties = EntityExtension.For(caster);
            float manaRefunded = (float) (((damage * 0.01)) * casterProperties.getMaxMana());
            if ((caster).attackEntityFrom(DamageSource.outOfWorld, (int) Math.floor(damage))) {
                casterProperties.setCurrentMana(casterProperties.getCurrentMana() + manaRefunded);
            } else {
                return false;
            }
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
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(2, 2, 2);
                particle.setMaxAge(15);
                particle.setParticleScale(0.1f);
                particle.AddParticleController(new ParticleApproachEntity(particle, target, 0.1, 0.1, 1, false));
                if (rand.nextBoolean())
                    particle.setRGBColorF(0.4f, 0.1f, 0.5f);
                else
                    particle.setRGBColorF(0.1f, 0.5f, 0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.LIFE, Affinity.ENDER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLACK_RUNE.get()),
                ModBlocks.aum
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
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