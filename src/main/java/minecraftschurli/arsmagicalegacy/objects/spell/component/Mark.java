package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.EnumSet;
import java.util.Random;

public class Mark extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        EntityExtension.For(caster).setMark(impactX, impactY, impactZ, caster.world.getDimension());
        if (caster instanceof PlayerEntity && world.isRemote)
            caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.markSet"));
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (!(target instanceof LivingEntity)) return false;
        if (caster.dimension.getId() != -512) {
            caster.dimension = DimensionType.getById(512);
            if (caster instanceof PlayerEntity && world.isRemote)
                caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.markClear"));
        } else {
//            caster.setMark(target.getPosX(), target.getPosY(), target.getPosZ(), caster.world.getDimension());
            if (caster instanceof PlayerEntity && world.isRemote)
                caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.markSet"));
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
        setupParticle(world, caster.getPosX() - 0.5f, caster.getPosY() + offset, caster.getPosZ(), 0.2, 0, colorModifier);
        setupParticle(world, caster.getPosX() + 0.5f, caster.getPosY() + offset, caster.getPosZ(), -0.2, 0, colorModifier);
        setupParticle(world, caster.getPosX(), caster.getPosY() + offset, caster.getPosZ() - 0.5f, 0, 0.2, colorModifier);
        setupParticle(world, caster.getPosX(), caster.getPosY() + offset, caster.getPosZ() + 0.5f, 0, -0.2, colorModifier);
    }

    private void setupParticle(World world, double x, double y, double z, double motionx, double motionz, int colorModifier) {
//        AMParticle effect = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "symbols", x, y, z);
//        if (effect != null) {
//            effect.AddParticleController(new ParticleConverge(effect, motionx, -0.1, motionz, 1, true));
//            effect.setMaxAge(40);
//            effect.setIgnoreMaxAge(false);
//            effect.setParticleScale(0.1f);
//            if (colorModifier > -1) effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
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
