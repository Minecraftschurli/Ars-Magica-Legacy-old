package minecraftschurli.arsmagicalegacy.objects.item;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-01-11
 */
public class InscriptionTableUpgrade extends Item {
    public InscriptionTableUpgrade() {
        super(ModItems.ITEM_1);
        this.addPropertyOverride(new ResourceLocation("tier"), (stack, world, entity) -> {
            if (!stack.hasTag() || !stack.getTag().contains("tier")) return 0;
            return stack.getTag().getInt("tier");
        });
    }

    public static int getTier(ItemStack stack) {
        return stack.getOrCreateTag().getInt("tier");
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) for (int i = 0; i < 3; i++) items.add(getTieredStack(i));
    }

    @Nonnull
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey(stack) + getTier(stack);
    }

    public ItemStack getTieredStack(int tier) {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("tier", tier);
        return stack;
    }
}
