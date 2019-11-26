package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellModifier;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class Absorption extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, Vec3d impact, LivingEntity caster) {
        return false;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity){
//            if (((LivingEntity)target).getCanHeal){
//                int duration = SpellUtils.getModifiedIntMul(240, stack, caster, target, world, 0, SpellModifier.Type.DURATION);
//                duration = SpellUtils.modifyDurationBasedOnArmor(caster, duration);
//                if (!world.isRemote) ((LivingEntity)target).addPotionEffect(new EffectInstance(Effects.ABSORPTION, duration, SpellUtils.countModifiers(SpellModifier.Type.BUFF_POWER, stack, 0) * 2));
//                ((LivingEntity)target).setHealCooldown(60);
//                return true;
//            }
        }
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 225;
    }

    @Override
    public float getBurnout(LivingEntity caster) {
        return 0;
    }

    @Override
    public float getCooldown(LivingEntity caster) {
        return 0;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public ISpellIngredient[] getRecipeItems() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.CYAN_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.GOLDEN_APPLE))
        };
    }

    @Override
    public void spawnParticles(World world, Vec3d pos, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        for (int i = 0; i < 25; ++i){
//            AMParticle particle = (AMParticle)AMCore.proxy.particleManager.spawn(world, "sparkle", pos.getX(), pos.getY() - 1, pos.getZ());
//            if (particle != null){
//                particle.addRandomOffset(1, 1, 1);
//                particle.AddParticleController(new ParticleFloatUpward(particle, 0, 0.1f, 1, false));
//                particle.AddParticleController(new ParticleOrbitEntity(particle, target, 0.5f, 2, false).setIgnoreYCoordinate(true).SetTargetDistance(0.3f + rand.nextDouble() * 0.3));
//                particle.setMaxAge(20);
//                particle.setParticleScale(0.2f);
//                particle.setRGBColorF(0f, 0.5f, 1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
//        }
    }
}
