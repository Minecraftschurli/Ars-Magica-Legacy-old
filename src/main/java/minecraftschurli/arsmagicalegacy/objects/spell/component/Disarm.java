package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.enchantments.*;
import minecraftschurli.arsmagicalegacy.entity.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.particles.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITasks.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class Disarm extends SpellComponent {
    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        Random rnd = new Random();
        double damage = SpellUtils.getModifiedIntMul(1, stack, caster, target, world, SpellModifiers.DAMAGE);
        if (target instanceof EntityLightMage)
            return false;
        if (target instanceof EntityDarkMage && !world.isRemote) {
            ItemEntity item = new ItemEntity(world);
            ItemStack dropstack = ((MobEntity) target).getHeldItemMainhand().copy();
            if (dropstack.getMaxDamage() > 0)
                dropstack.setItemDamage((int) Math.floor(dropstack.getMaxDamage() * (0.8f + (world.rand.nextFloat() * 0.19f))));
            item.setItemEntityStack(dropstack);
            item.setPosition(target.posX, target.posY, target.posZ);
            item.setPickupDelay(15);
            world.addEntity(item);
            ((EntityDarkMage) target).setItemStackToSlot(MobEntity.getSlotForItemStack(stack), null);
            ((EntityDarkMage) target).disarm();
            return true;
        }
        if (target instanceof PlayerEntity && (!ArsMagica2.config.getDisarmAffectsPlayers() || (!world.isRemote && !FMLCommonHandler.instance().getMinecraftServerInstance().isPVPEnabled())))
            return false;
        if (target instanceof PlayerEntity && ((PlayerEntity) target).getHeldItemOffhand() != null && !target.worldObj.isRemote && (rnd.nextInt(9) + 1 <= damage)
                && EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((PlayerEntity) target).getHeldItemOffhand()) <= 0) {
            //Drop mainhand item? 1 line. You want to drop the offhand? You'd better like workarounds.
            ItemEntity item = new ItemEntity(world);
            ItemStack dropstack = ((PlayerEntity) target).getHeldItemOffhand().copy();
            item.setItemEntityStack(dropstack);
            item.setPosition(target.posX, target.posY, target.posZ);
            item.setDefaultPickupDelay();
            world.addEntity(item);
            ((PlayerEntity) target).inventory.offHandInventory[0] = null;
        }
        if (target instanceof PlayerEntity && ((PlayerEntity) target).getHeldItemMainhand() != null && !target.worldObj.isRemote) {
            if (EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((PlayerEntity) target).getHeldItemMainhand()) > 0)
                return true;
            ((PlayerEntity) target).dropItem(true);
            return true;
        } else if (target instanceof EntityEnderman) {
            BlockState blockID = ((EntityEnderman) target).getHeldBlockState();
            if (blockID != null) {
                ((EntityEnderman) target).setHeldBlockState(null);
                ItemStack dropstack = new ItemStack(blockID.getBlock(), 1, blockID.getBlock().getMetaFromState(blockID));
                ItemEntity item = new ItemEntity(world);
                item.setItemEntityStack(dropstack);
                item.setPosition(target.posX, target.posY, target.posZ);
                world.addEntity(item);
            }
            ((MobEntity) target).setAttackTarget(caster);
        } else if (target instanceof MobEntity && ((MobEntity) target).getHeldItemMainhand() != null) {
            if (EnchantmentHelper.getEnchantmentLevel(AMEnchantments.soulbound, ((MobEntity) target).getActiveItemStack()) > 0)
                return true;
            if (!world.isRemote) {
                ItemEntity item = new ItemEntity(world);
                ItemStack dropstack = ((MobEntity) target).getHeldItemMainhand().copy();
                if (dropstack.getMaxDamage() > 0)
                    dropstack.setItemDamage((int) Math.floor(dropstack.getMaxDamage() * (0.8f + (world.rand.nextFloat() * 0.19f))));
                item.setItemEntityStack(dropstack);
                item.setPosition(target.posX, target.posY, target.posZ);
                item.setDefaultPickupDelay();
                world.addEntity(item);
            }
            target.setItemStackToSlot(MobEntity.getSlotForItemStack(stack), null);
            ((MobEntity) target).setAttackTarget(caster);
            Iterator<EntityAITaskEntry> it = ((MobEntity) target).tasks.taskEntries.iterator();
            boolean removed = false;
            while (it.hasNext()) {
                EntityAITaskEntry task = it.next();
                if (task.action instanceof EntityAIAttackRanged) {
                    it.remove();
                    removed = true;
                    break;
                }
            }
            if (removed) {
                ((MobEntity) target).tasks.addTask(5, new EntityAIAttackMelee((CreatureEntity) target, 0.5, true));
                ((MobEntity) target).setCanPickUpLoot(true);
            }
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
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
        for (int i = 0; i < 25; ++i) {
            AMParticle particle = (AMParticle) ArsMagica2.proxy.particleManager.spawn(world, "sparkle2", x, y, z);
            if (particle != null) {
                particle.addRandomOffset(1, 2, 1);
                particle.AddParticleController(new ParticleMoveOnHeading(particle, MathHelper.wrapDegrees((target instanceof LivingEntity ? ((LivingEntity) target).rotationYawHead : target.rotationYaw) + 90), MathHelper.wrapDegrees(target.rotationPitch), 0.1 + rand.nextDouble() * 0.5, 1, false));
                particle.AddParticleController(new ParticleFadeOut(particle, 1, false).setFadeSpeed(0.05f));
                particle.setAffectedByGravity();
                if (rand.nextBoolean())
                    particle.setRGBColorF(0.7f, 0.7f, 0.1f);
                else
                    particle.setRGBColorF(0.1f, 0.7f, 0.1f);
                particle.setMaxAge(40);
                particle.setParticleScale(0.1f);
                if (colorModifier > -1) {
                    particle.setRGBColorF(((colorModifier >> 16) & 0xFF) / 255.0f, ((colorModifier >> 8) & 0xFF) / 255.0f, (colorModifier & 0xFF) / 255.0f);
                }
            }
        }
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.NONE);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStack(ModItems.ORANGE_RUNE.get()),
                Items.IRON_SWORD
        };
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace,
                                    double impactX, double impactY, double impactZ, LivingEntity caster) {
        return false;
    }
}