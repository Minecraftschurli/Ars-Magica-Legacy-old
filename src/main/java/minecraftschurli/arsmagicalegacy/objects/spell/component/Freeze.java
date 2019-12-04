package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.buffs.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Freeze extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(pos).getBlock();
        if (block.equals(Blocks.WATER) || block.equals(Blocks.FLOWING_WATER)) //flowing or still water
        {
            world.setBlockState(pos, Blocks.ICE.getDefaultState());
            return true;
        } else if (block.equals(Blocks.LAVA)) {
            world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            return true;
        } else if (block.equals(Blocks.FLOWING_LAVA)) {
            world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
            return true;
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity) {
            int duration = SpellUtils.getModifiedIntMul(PotionEffectsDefs.default_buff_duration, stack, caster, target, world, SpellModifiers.DURATION);
            //duration = SpellUtils.modifyDurationBasedOnArmor(caster, duration);
            if (RitualShapeHelper.instance.matchesRitual(this, world, target.getPosition())) {
                duration += (3600 * (SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack) + 1));
                RitualShapeHelper.instance.consumeReagents(this, world, target.getPosition());
            }
            if (!world.isRemote)
                ((LivingEntity) target).addPotionEffect(new BuffEffectFrostSlowed(duration, SpellUtils.countModifiers(SpellModifiers.BUFF_POWER, stack)));
            return true;
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION, SpellModifiers.BUFF_POWER);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 29;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 5; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "snowflakes", x + 0.5, y + 0.5, z + 0.5);
            if (particle != null) {
                particle.addRandomOffset(1, 0.5, 1);
                particle.addVelocity(rand.nextDouble() * 0.2 - 0.1, 0.3, rand.nextDouble() * 0.2 - 0.1);
                particle.setAffectedByGravity();
                particle.setDontRequireControllers();
                particle.setMaxAge(10);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ICE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.BLUE_RUNE.get()),
                Blocks.SNOW
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.02f;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        return RitualShapeHelper.instance.hourglass;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                new ItemStack(Blocks.ICE)
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
        return null;
    }
}