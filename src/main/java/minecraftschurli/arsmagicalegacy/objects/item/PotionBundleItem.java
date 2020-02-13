package minecraftschurli.arsmagicalegacy.objects.item;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class PotionBundleItem extends Item {
    private Potion potion;
    public static final String KEY = "uses";

    public PotionBundleItem(Properties properties, Potion potion) {
        super(properties);
        this.potion = potion;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        stack.getTag().putInt(KEY, 3);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote || !playerIn.getHeldItem(handIn).hasTag() || !playerIn.getHeldItem(handIn).getTag().contains(KEY)) return new ActionResult<>(ActionResultType.FAIL, playerIn.getHeldItem(handIn));
        else {
            for(EffectInstance effect : potion.getEffects()) playerIn.addPotionEffect(effect);
            playerIn.getHeldItem(handIn).getTag().putInt(KEY, playerIn.getHeldItem(handIn).getTag().getInt(KEY) - 1);
            if(playerIn.getHeldItem(handIn).getTag().getInt(KEY) == 0) playerIn.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            else if(!playerIn.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) playerIn.dropItem(new ItemStack(Items.GLASS_BOTTLE), true);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
