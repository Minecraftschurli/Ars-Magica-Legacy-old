package minecraftschurli.arsmagicalegacy.objects.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

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
            ItemHandlerHelper.giveItemToPlayer(playerentity, new ItemStack(Items.GLASS_BOTTLE));
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
