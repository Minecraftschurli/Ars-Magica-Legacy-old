package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.blocks.*;
import minecraftschurli.arsmagicalegacy.api.rituals.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.blocks.tileentity.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Transplace extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(blockPos).getBlock();
        if (!world.isRemote && caster instanceof PlayerEntity && block == ModBlocks.inertSpawner) {
            if (RitualShapeHelper.instance.matchesRitual(this, world, blockPos)) {
                RitualShapeHelper.instance.consumeReagents(this, world, blockPos);
                RitualShapeHelper.instance.consumeShape(this, world, blockPos);
                world.setBlockState(blockPos, ModBlocks.otherworldAura.getDefaultState());
                TileEntity te = world.getTileEntity(blockPos);
                if (te != null && te instanceof TileEntityOtherworldAura) {
                    ((TileEntityOtherworldAura) te).setPlacedByUsername(caster.getName());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!world.isRemote && target != null && !target.isDead) {
            double tPosX = target.posX;
            double tPosY = target.posY;
            double tPosZ = target.posZ;
            double cPosX = caster.posX;
            double cPosY = caster.posY;
            double cPosZ = caster.posZ;
            caster.setPositionAndUpdate(tPosX, tPosY, tPosZ);
            if (target instanceof EntityLiving)
                ((EntityLiving) target).setPositionAndUpdate(cPosX, cPosY, cPosZ);
            else
                target.setPosition(cPosX, cPosY, cPosZ);
        }
        if (target instanceof EntityLiving)
            ((EntityLiving) target).faceEntity(caster, 180f, 180f);
        return true;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 100;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.AddParticleController(new ParticleArcToPoint(particle, 1, target.posX, target.posY + target.getEyeHeight(), target.posZ, false).SetSpeed(0.05f).generateControlPoints());
                particle.setMaxAge(40);
                particle.setParticleScale(0.2f);
                particle.setRGBColorF(1, 0, 0);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
        for (int i = 0; i < 15; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", target.posX, target.posY + target.getEyeHeight(), target.posZ);
            if (particle != null) {
                particle.addRandomOffset(1, 1, 1);
                particle.AddParticleController(new ParticleArcToPoint(particle, 1, caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ, false).SetSpeed(0.05f).generateControlPoints());
                particle.setMaxAge(40);
                particle.setParticleScale(0.2f);
                particle.setRGBColorF(0, 0, 1);
                if (colorModifier > -1) {
                    particle.setRGBColorF((0xFF - ((colorModifier >> 16) & 0xFF)) / 255.0f, (0xFF - ((colorModifier >> 8) & 0xFF)) / 255.0f, (0xFF - (colorModifier & 0xFF)) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ENDER);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.RED_RUNE.get()),
                Items.COMPASS,
                new ItemStack(ModItems.BLUE_RUNE.get()),
                Items.ENDER_PEARL
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.02f;
    }

    @Override
    public MultiblockStructureDefinition getRitualShape() {
        return RitualShapeHelper.instance.ringedCross;
    }

    @Override
    public ItemStack[] getReagents() {
        return new ItemStack[]{
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
                new ItemStack(ModItems.mageArmor),
                new ItemStack(ModItems.mageBoots),
                new ItemStack(ModItems.mageHood),
                new ItemStack(ModItems.mageLeggings),
                new ItemStack(ModItems.playerFocus)
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
        return new ItemStack(ModBlocks.otherworldAura);
    }
}