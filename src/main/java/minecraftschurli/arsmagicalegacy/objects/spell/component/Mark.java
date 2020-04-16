package minecraftschurli.arsmagicalegacy.objects.spell.component;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Mark extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
//        EntityExtension.For(caster).setMark(impactX, impactY, impactZ, caster.world.getDimension());
//        if (caster instanceof PlayerEntity && world.isRemote) caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.markSet"));
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
//        if (!(target instanceof LivingEntity)) return false;
//        if (caster.dimension.getId() != -512) {
//            caster.dimension = DimensionType.getById(512);
//            if (caster instanceof PlayerEntity && world.isRemote) caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.markClear"));
//        } else {
//            caster.setMark(target.getPosX(), target.getPosY(), target.getPosZ(), caster.world.getDimension());
//            if (caster instanceof PlayerEntity && world.isRemote) caster.sendMessage(new TranslationTextComponent(ArsMagicaAPI.MODID + ".chat.markSet"));
//        }
        return false;//true;
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(ModSpellParts.NONE.get());
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
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        makeParticle(world, caster.getPosX() - 0.5f, caster.getPosY() + 1, caster.getPosZ(), 0.2, 0, colorModifier);
//        makeParticle(world, caster.getPosX() + 0.5f, caster.getPosY() + 1, caster.getPosZ(), -0.2, 0, colorModifier);
//        makeParticle(world, caster.getPosX(), caster.getPosY() + 1, caster.getPosZ() - 0.5f, 0, 0.2, colorModifier);
//        makeParticle(world, caster.getPosX(), caster.getPosY() + 1, caster.getPosZ() + 0.5f, 0, -0.2, colorModifier);
    }

    private void makeParticle(World world, double x, double y, double z, double motionx, double motionz, int colorModifier) {
//        AMParticle effect = (AMParticle) ArsMagicaLegacy.proxy.particleManager.spawn(world, "symbols", x, y, z);
//        if (effect != null) {
//            effect.AddParticleController(new ParticleConverge(effect, motionx, -0.1, motionz, 1, true));
//            effect.setMaxAge(40);
//            effect.setIgnoreMaxAge(false);
//            effect.setParticleScale(0.1f);
//            if (colorModifier > -1) effect.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255, ((colorModifier >> 8) & 0xFF) / 255, (colorModifier & 0xFF) / 255);
//        }
    }
}
