package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.*;
import net.minecraft.world.*;
import net.minecraft.world.dimension.*;

import java.util.*;

public class Mark extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        EntityExtension.For(caster).setMark(impactX, impactY, impactZ, caster.world.getDimension());
        if (caster instanceof PlayerEntity && world.isRemote) caster.sendMessage(new TranslationTextComponent("minecraftschurli.arsmagicalegacy.tooltip.markSet"));
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (caster.dimension.getId() != -512) {
            caster.dimension = DimensionType.getById(512);
            if (caster instanceof PlayerEntity && world.isRemote) caster.sendMessage(new TranslationTextComponent("Mark Cleared"));
        } else {
//            caster.setMark(target.posX, target.posY, target.posZ, caster.world.getDimension());
            if (caster instanceof PlayerEntity && world.isRemote) caster.sendMessage(new TranslationTextComponent("Mark Set"));
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 5;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        int offset = 1;
        setupParticle(world, caster.posX - 0.5f, caster.posY + offset, caster.posZ, 0.2, 0, colorModifier);
        setupParticle(world, caster.posX + 0.5f, caster.posY + offset, caster.posZ, -0.2, 0, colorModifier);
        setupParticle(world, caster.posX, caster.posY + offset, caster.posZ - 0.5f, 0, 0.2, colorModifier);
        setupParticle(world, caster.posX, caster.posY + offset, caster.posZ + 0.5f, 0, -0.2, colorModifier);
    }

    private void setupParticle(World world, double x, double y, double z, double motionx, double motionz, int colorModifier) {
//        AMParticle effect = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "symbols", x, y, z);
//        if (effect != null) {
//            effect.AddParticleController(new ParticleConverge(effect, motionx, -0.1, motionz, 1, true));
//            effect.setMaxAge(40);
//            effect.setIgnoreMaxAge(false);
//            effect.setParticleScale(0.1f);
//            if (colorModifier > -1) effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//        }
    }

//    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.NONE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.RED_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.MAP))
        };
    }

//    @Override
//    public float getAffinityShift(Affinity affinity) {
//        return 0;
//    }
//
    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {
    }
}