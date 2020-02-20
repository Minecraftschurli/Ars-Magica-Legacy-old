package minecraftschurli.arsmagicalegacy.objects.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.registries.*;

import javax.annotation.*;

public class PotionBundleItem extends PotionItem {
    public static final String USES_KEY = "Uses";

    public PotionBundleItem(Properties properties) {
        super(properties);
        this.addPropertyOverride(new ResourceLocation("uses"), (stack, world, entity) -> {
            //noinspection ConstantConditions
            if (!stack.hasTag() || !stack.getTag().contains(USES_KEY))
                return 0;
            return stack.getTag().getInt(USES_KEY);
        });
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity entity) {
        //noinspection ConstantConditions
        if(!stack.hasTag() || !stack.getTag().contains(USES_KEY) || PotionUtils.getPotionFromItem(stack) == Potions.EMPTY)
            return stack;
        PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
        if (playerentity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerentity, stack);
        }
        if (!world.isRemote) {
            for(EffectInstance effectinstance : PotionUtils.getEffectsFromStack(stack)) {
                if (effectinstance.getPotion().isInstant()) {
                    effectinstance.getPotion().affectEntity(playerentity, playerentity, entity, effectinstance.getAmplifier(), 1.0D);
                } else {
                    entity.addPotionEffect(new EffectInstance(effectinstance));
                }
            }
        }
        CompoundNBT tag = stack.getTag();
        if (playerentity != null) {
            playerentity.addStat(Stats.ITEM_USED.get(this));
            tag.putInt(USES_KEY, tag.getInt(USES_KEY) - 1);
        }
        if (playerentity != null) {
            if (!playerentity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                InventoryHelper.spawnItemStack(world, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        if(tag.getInt(USES_KEY) == 1)
            return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION, 1), PotionUtils.getPotionFromItem(stack));
        return stack;
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for (Potion potion : ForgeRegistries.POTION_TYPES) {
                if (potion == Potions.EMPTY)
                    continue;
                ItemStack stack = PotionUtils.addPotionToItemStack(new ItemStack(this), potion);
                stack.getOrCreateTag().putInt(USES_KEY, 3);
                items.add(stack);
            }
        }
    }
}
