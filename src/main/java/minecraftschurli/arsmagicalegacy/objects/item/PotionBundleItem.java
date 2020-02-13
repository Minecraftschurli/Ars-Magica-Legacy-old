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

public class PotionBundleItem extends Item {
    public static final String USES_KEY = "uses";
    public static final String POTION_KEY = "potion";

    public PotionBundleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onCreated(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull PlayerEntity playerIn) {
        stack.getOrCreateTag().putInt(USES_KEY, 3);
    }

    @Nonnull
    @Override
    public UseAction getUseAction(@Nonnull ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 32;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, @Nonnull PlayerEntity playerIn, @Nonnull Hand handIn) {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
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
        if(!((PlayerEntity) entity).addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE)))
            InventoryHelper.spawnItemStack(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.GLASS_BOTTLE));
        if(tag.getInt(USES_KEY) == 0)
            return new ItemStack(Items.GLASS_BOTTLE);
        return stack;
    }
}
