package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spellsystem.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spellsystem.SpellComponent;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class Accelerate extends SpellComponent {
    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos pos, Direction blockFace, Vec3d impact, LivingEntity caster) {
        if (world.rand.nextDouble() < 0.5){
            Block block = world.getBlockState(pos).getBlock();
//            if (!(block instanceof AirBlock)) block.updateTick(world, blocky, blocky, blockz, world.rand);
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        caster.setAIMoveSpeed(caster.getAIMoveSpeed() * 1.6f);
        return false;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 6;
    }

    @Override
    public float getBurnout(LivingEntity caster) {
        return 2;
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
                new ItemStackSpellIngredient(new ItemStack(ModItems.YELLOW_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.LEATHER_BOOTS)),
                new ItemStackSpellIngredient(new ItemStack(Items.REDSTONE))
        };
    }

    @Override
    public void spawnParticles(World world, Vec3d pos, LivingEntity caster, Entity target, Random rand, int colorModifier) {
//        AMParticle particle = (AMParticle)AMCore.proxy.particleManager.spawn(world, "sparkle", pos.getX(), pos.getY(), pos.getZ());
//        if (particle != null) {
//            particle.AddParticleController(new ParticleOrbitEntity(particle, caster, 0.1f, 1, false).SetTargetDistance(rand.nextDouble() + 0.5));
//            particle.setMaxAge(25 + rand.nextInt(10));
//            if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//        }
    }
}
