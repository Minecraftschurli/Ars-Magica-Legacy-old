package minecraftschurli.arsmagicalegacy.objects.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class PotionBundleItem extends PotionItem {
    public static final String USES_KEY = "Uses";
    public static final String POTION_KEY = "Potion";

    public PotionBundleItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull LivingEntity entity) {
        //noinspection ConstantConditions
        if(world.isRemote || !(entity instanceof PlayerEntity) || !entity.getActiveItemStack().hasTag() || !entity.getActiveItemStack().getTag().contains(USES_KEY))
            return stack;
        CompoundNBT tag = entity.getActiveItemStack().getTag();
        Potion potion = ForgeRegistries.POTION_TYPES.getValue(ResourceLocation.tryCreate(tag.getString(POTION_KEY)));
        if (potion == null)
            return stack;
        for(EffectInstance effect : potion.getEffects()) {
            entity.addPotionEffect(effect);
        }
        tag.putInt(USES_KEY, tag.getInt(USES_KEY) - 1);
        if(tag.getInt(USES_KEY) == 0)
            return new ItemStack(Items.GLASS_BOTTLE);
        if(!((PlayerEntity) entity).addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE)))
            InventoryHelper.spawnItemStack(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.GLASS_BOTTLE));
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
