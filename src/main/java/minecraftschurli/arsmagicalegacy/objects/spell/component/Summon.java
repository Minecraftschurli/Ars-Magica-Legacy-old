package minecraftschurli.arsmagicalegacy.spell.component;

import com.google.common.collect.*;
import minecraftschurli.arsmagicalegacy.api.affinity.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.extensions.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.items.*;
import minecraftschurli.arsmagicalegacy.power.*;
import minecraftschurli.arsmagicalegacy.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

@SuppressWarnings("deprecation")
public class Summon extends SpellComponent {
    public EntityLiving summonCreature(ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z) {
        Class<? extends Entity> clazz = getSummonType(stack);
        EntityLiving entity = null;
        try {
            entity = (EntityLiving) clazz.getConstructor(World.class).newInstance(world);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
        if (entity == null) {
            return null;
        }
        if (entity instanceof EntitySkeleton) {
            ((EntitySkeleton) entity).setSkeletonType(SkeletonType.NORMAL);
            ((EntitySkeleton) entity).setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.BOW));
        } else if (entity instanceof EntityHorse && caster instanceof PlayerEntity) {
            ((EntityHorse) entity).setTamedBy(((PlayerEntity) caster));
        }
        entity.setPosition(x, y, z);
        world.addEntity(entity);
        if (caster instanceof PlayerEntity) {
            EntityUtils.makeSummon_PlayerFaction((CreatureEntity) entity, (PlayerEntity) caster, false);
        } else {
            EntityUtils.makeSummon_MonsterFaction((CreatureEntity) entity, false);
        }
        EntityUtils.setOwner(entity, caster);
        int duration = SpellUtils.getModifiedIntMul(4800, stack, caster, target, world, SpellModifiers.DURATION);
        EntityUtils.setSummonDuration(entity, duration);
        SpellUtils.applyStageToEntity(stack, caster, world, entity, false);
        return entity;
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.of(SpellModifiers.DURATION);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        //Chimerite, purified vinteum, blue orchid, monster focus, any filled crystal phylactery, 1500 dark power
        return new ISpellIngredient[]{
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_CHIMERITE),
                new ItemStack(ModItems.itemOre, 1, ItemOre.META_PURIFIED_VINTEUM),
                ModBlocks.cerublossom,
                ModItems.mobFocus,
                new ItemStack(ModItems.crystalPhylactery, 1, ItemCrystalPhylactery.META_FULL),
                "E:" + PowerTypes.DARK.ID(), 1500
        };
    }

    public void setSummonType(CompoundNBT stack, ItemStack phylacteryStack) {
        if (phylacteryStack.getItemDamage() == ItemCrystalPhylactery.META_FULL && phylacteryStack.getItem() instanceof ItemCrystalPhylactery) {
            setSummonType(stack, ModItems.crystalPhylactery.getSpawnClass(phylacteryStack));
        }
    }

    public Class<? extends Entity> getSummonType(ItemStack stack) {
        String s = SpellUtils.getSpellMetadata(stack, "SummonType");
        if (s == null || s == "")
            s = "Skeleton"; //default!  default!  default!
        Class<? extends Entity> clazz = (Class<? extends Entity>) EntityList.NAME_TO_CLASS.get(s);
        return clazz;
    }

    public void setSummonType(CompoundNBT stack, String s) {
        Class<? extends Entity> clazz = (Class<? extends Entity>) EntityList.NAME_TO_CLASS.get(s);
        setSummonType(stack, clazz);
    }

    public void setSummonType(CompoundNBT stack, Class<? extends Entity> clazz) {
        clazz = checkForSpecialSpawns(stack, clazz);
        String s = (String) EntityList.CLASS_TO_NAME.get(clazz);
        if (s == null)
            s = "";
        SpellUtils.setSpellMetadata(stack, "SpawnClassName", s);
        SpellUtils.setSpellMetadata(stack, "SummonType", s);
    }

    private Class<? extends Entity> checkForSpecialSpawns(CompoundNBT tag, Class<? extends Entity> clazz) {
//		if (clazz == EntityChicken.class){
//			if (SpellUtils.modifierIsPresent(SpellModifiers.DAMAGE, stack) && SpellUtils.componentIsPresent(stack, Haste.class)){
//				return EntityBattleChicken.class;
//			}
//		}else if (clazz == EntityCow.class){
//			if (SpellUtils.modifierIsPresent(SpellModifiers.DAMAGE, stack) && SpellUtils.componentIsPresent(stack, AstralDistortion.class)){
//				return EntityHellCow.class;
//			}
//		}
        return clazz;
    }

    @Override
    public boolean applyEffectBlock(ItemStack stack, World world, BlockPos blockPos, Direction blockFace, double impactX, double impactY, double impactZ, LivingEntity caster) {
        if (!world.isRemote) {
            if (EntityExtension.For(caster).getCanHaveMoreSummons()) {
                return summonCreature(stack, caster, caster, world, impactX, impactY, impactZ) != null;
            } else {
                if (caster instanceof PlayerEntity) {
                    ((PlayerEntity) caster).addChatMessage(new TextComponentString(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.noMoreSummons")));
                }
            }
        }
        return true;
    }

    @Override
    public boolean applyEffectEntity(ItemStack stack, World world, LivingEntity caster, Entity target) {
        if (target instanceof LivingEntity && EntityUtils.isSummon((LivingEntity) target))
            return false;
        if (!world.isRemote) {
            if (EntityExtension.For(caster).getCanHaveMoreSummons()) {
                return summonCreature(stack, caster, caster, world, target.posX, target.posY, target.posZ) != null;
            } else {
                if (caster instanceof PlayerEntity) {
                    ((PlayerEntity) caster).addChatComponentMessage(new TextComponentString(I18n.translateToLocal("minecraftschurli.arsmagicalegacy.tooltip.noMoreSummons")));
                }
            }
        }
        return true;
    }

    @Override
    public float getManaCost(LivingEntity caster) {
        return 400;
    }

    @Override
    public ItemStack[] reagents(LivingEntity caster) {
        return null;
    }

    @Override
    public void spawnParticles(World world, double x, double y, double z, LivingEntity caster, Entity target, Random rand, int colorModifier) {
    }

    @Override
    public Set<Affinity> getAffinity() {
        return Sets.newHashSet(Affinity.ENDER, Affinity.LIFE);
    }

    @Override
    public float getAffinityShift(Affinity affinity) {
        return 0.01f;
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, Object[] recipe) {
        for (Object obj : recipe) {
            if (obj instanceof ItemStack) {
                ItemStack is = (ItemStack) obj;
                if (is.getItem().equals(ModItems.crystalPhylactery))
                    setSummonType(tag, is);
            }
        }
    }
}