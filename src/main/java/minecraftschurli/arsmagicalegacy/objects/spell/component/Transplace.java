package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Transplace extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        Block block = world.getBlockState(blockPos).getBlock();
//        if (!world.isRemote && caster instanceof PlayerEntity && block == ModBlocks.inertSpawner) {
//            if (RitualShapeHelper.instance.matchesRitual(this, world, blockPos)) {
//                RitualShapeHelper.instance.consumeReagents(this, world, blockPos);
//                RitualShapeHelper.instance.consumeShape(this, world, blockPos);
//                world.setBlockState(blockPos, ModBlocks.otherworldAura.getDefaultState());
//                TileEntity te = world.getTileEntity(blockPos);
//                if (te != null && te instanceof TileEntityOtherworldAura) ((TileEntityOtherworldAura) te).setPlacedByUsername(caster.getName());
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!world.isRemote && target != null && target.isAlive()) {
            double tPosX = target.posX;
            double tPosY = target.posY;
            double tPosZ = target.posZ;
            double cPosX = caster.posX;
            double cPosY = caster.posY;
            double cPosZ = caster.posZ;
            caster.setPositionAndUpdate(tPosX, tPosY, tPosZ);
            if (target instanceof LivingEntity) target.setPositionAndUpdate(cPosX, cPosY, cPosZ);
            else target.setPosition(cPosX, cPosY, cPosZ);
        }
//        if (target instanceof LivingEntity) ((LivingEntity) target).lookAt(caster, 180f, 180f);
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
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 15; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ);
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.AddParticleController(new ParticleArcToPoint(particle, 1, target.posX, target.posY + target.getEyeHeight(), target.posZ, false).SetSpeed(0.05f).generateControlPoints());
//                particle.setMaxAge(40);
//                particle.setParticleScale(0.2f);
//                particle.setRGBColorF(1, 0, 0);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
        for (int i = 0; i < 15; ++i) {
//            AMParticle particle = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "sparkle2", target.posX, target.posY + target.getEyeHeight(), target.posZ);
//            if (particle != null) {
//                particle.addRandomOffset(1, 1, 1);
//                particle.AddParticleController(new ParticleArcToPoint(particle, 1, caster.posX, caster.posY + caster.getEyeHeight(), caster.posZ, false).SetSpeed(0.05f).generateControlPoints());
//                particle.setMaxAge(40);
//                particle.setParticleScale(0.2f);
//                particle.setRGBColorF(0, 0, 1);
//                if (colorModifier > -1) particle.setRGBColorF((0xFF - ((colorModifier >> 16) & 0xFF)) / 255.0f, (0xFF - ((colorModifier >> 8) & 0xFF)) / 255.0f, (0xFF - (colorModifier & 0xFF)) / 255.0f);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.ENDER);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.BLUE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.COMPASS)),
                new ItemStackSpellIngredient(new ItemStack(Items.ENDER_PEARL)),
        };
    }

    //    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0.02f;
//    }
//
//    @Override
//    public MultiblockStructureDefinition getRitualShape() {
//        return RitualShapeHelper.instance.ringedCross;
//    }
//
    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return new ItemStack[]{
                new ItemStack(ModItems.PURIFIED_VINTEUM.get()),
                new ItemStack(ModItems.PLAYER_FOCUS.get()),
//                new ItemStack(ModItems.MAGE_HELMET.get()),
//                new ItemStack(ModItems.MAGE_CHESTPLATE.get()),
//                new ItemStack(ModItems.MAGE_LEGGINGS.get()),
//                new ItemStack(ModItems.MAGE_BOOTS.get())
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
