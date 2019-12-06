package minecraftschurli.arsmagicalegacy.objects.spell.component;

import minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class Disarm extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        Random rnd = new Random();
        double damage = SpellUtils.getModifiedIntMul(1, stack, caster, target, world, SpellModifiers.DAMAGE);
//        if (target instanceof EntityLightMage) return false;
        if (/*target instanceof EntityDarkMage && */!world.isRemote) {
            ItemEntity item = new ItemEntity(world, target.posX, target.posY, target.posZ);
            ItemStack dropstack = ((MobEntity) target).getHeldItemMainhand().copy();
            if (dropstack.getMaxDamage() > 0)
                dropstack.setDamage((int) Math.floor(dropstack.getMaxDamage() * (0.8f + (world.rand.nextFloat() * 0.19f))));
            item.setItem(dropstack);
            item.setPosition(target.posX, target.posY, target.posZ);
            item.setPickupDelay(15);
            world.addEntity(item);
//            ((EntityDarkMage) target).setItemStackToSlot(MobEntity.getSlotForItemStack(stack), null);
//            ((EntityDarkMage) target).disarm();
            return true;
        }
//        if (target instanceof PlayerEntity && (!ArsMagica2.config.getDisarmAffectsPlayers() || (!world.isRemote && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled()))) return false;
        if (target instanceof PlayerEntity && ((PlayerEntity) target).getHeldItemOffhand() != null && !target.world.isRemote && (rnd.nextInt(9) + 1 <= damage) /*&& EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((PlayerEntity) target).getHeldItemOffhand()) <= 0*/) {
            ItemEntity item = new ItemEntity(world, target.posX, target.posY, target.posZ);
            ItemStack dropstack = ((PlayerEntity) target).getHeldItemOffhand().copy();
            item.setItem(dropstack);
            item.setPosition(target.posX, target.posY, target.posZ);
            item.setDefaultPickupDelay();
            world.addEntity(item);
            ((PlayerEntity) target).setHeldItem(Hand.OFF_HAND, null);
        }
        if (target instanceof PlayerEntity && ((PlayerEntity) target).getHeldItemMainhand() != null && !target.world.isRemote) {
//            if (EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((PlayerEntity) target).getHeldItemMainhand()) > 0) return true;
            ((PlayerEntity) target).dropItem(true);
            return true;
        } else if (target instanceof EndermanEntity) {
            BlockState blockID = ((EndermanEntity) target).getHeldBlockState();
            if (blockID != null) {
                ((EndermanEntity) target).setHeldBlockState(null);
                ItemStack dropstack = new ItemStack(blockID.getBlock());
                ItemEntity item = new ItemEntity(world, target.posX, target.posY, target.posZ);
                item.setItem(dropstack);
                item.setPosition(target.posX, target.posY, target.posZ);
                world.addEntity(item);
            }
            ((MobEntity) target).setAttackTarget(caster);
        } else if (target instanceof MobEntity && ((MobEntity) target).getHeldItemMainhand() != null) {
//            if (EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((MobEntity) target).getActiveItemStack()) > 0) return true;
            if (!world.isRemote) {
                ItemEntity item = new ItemEntity(world, target.posX, target.posY, target.posZ);
                ItemStack dropstack = ((MobEntity) target).getHeldItemMainhand().copy();
                if (dropstack.getMaxDamage() > 0)
                    dropstack.setDamage((int) Math.floor(dropstack.getMaxDamage() * (0.8f + (world.rand.nextFloat() * 0.19f))));
                item.setItem(dropstack);
                item.setPosition(target.posX, target.posY, target.posZ);
                item.setDefaultPickupDelay();
                world.addEntity(item);
            }
            target.setItemStackToSlot(MobEntity.getSlotForItemStack(stack), null);
            ((MobEntity) target).setAttackTarget(caster);
        }
        return false;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DAMAGE);
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 130;
    }

    @Override
    public ItemStack[] getReagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
//            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
//            if (particle != null) {
//                particle.addRandomOffset(1, 2, 1);
//                particle.AddParticleController(new ParticleMoveOnHeading(particle, MathHelper.wrapDegrees((target instanceof LivingEntity ? ((LivingEntity) target).rotationYawHead : target.rotationYaw) + 90), MathHelper.wrapDegrees(target.rotationPitch), 0.1 + rand.nextDouble() * 0.5, 1, false));
//                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
//                particle.setAffectedByGravity();
//                if (rand.nextBoolean()) particle.setRGBColorF(0.7f, 0.7f, 0.1f);
//                else particle.setRGBColorF(0.1f, 0.7f, 0.1f);
//                particle.setMaxAge(40);
//                particle.setParticleScale(0.1f);
//                if (colorModifier > -1) particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
//            }
        }
    }

    //    @Override
//    public Set<Affinity> getAffinity() {
//        return Sets.newHashSet(Affinity.NONE);
//    }
//
    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ORANGE_RUNE.get())),
                new ItemStackSpellIngredient(new ItemStack(Items.IRON_SWORD))
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

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}
