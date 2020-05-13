package minecraftschurli.arsmagicalegacy.objects.item.affinitytome;

import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2020-02-20
 */
public class AffinityTomeItem extends Item {
    private static final String AFFINITY_KEY = "affinity";

    public AffinityTomeItem() {
        super(ModItems.ITEM_64);
    }

    public static Affinity getAffinity(ItemStack stack) {
        ResourceLocation affinity;
        if (!stack.hasTag() || !stack.getTag().contains(AFFINITY_KEY))
            affinity = Affinity.NONE;
        else
            affinity = ResourceLocation.tryCreate(stack.getTag().getString(AFFINITY_KEY));
        return RegistryHandler.getAffinityRegistry().getValue(affinity);
    }

    public static ItemStack getStackForAffinity(Affinity affinity) {
        return ModItems.AFFINITY_TOME.get().setAffinity(new ItemStack(ModItems.AFFINITY_TOME.get()), affinity);
    }

    public static ItemStack getStackForAffinity(ResourceLocation affinity) {
        return ModItems.AFFINITY_TOME.get().setAffinity(new ItemStack(ModItems.AFFINITY_TOME.get()), affinity);
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            for (Affinity affinity : RegistryHandler.getAffinityRegistry()) {
                if (Affinity.NONE.equals(affinity.getRegistryName()))
                    continue;
                items.add(setAffinity(new ItemStack(this), affinity));
            }
        }
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
        return new TranslationTextComponent(getTranslationKey(stack));
    }

    @Nonnull
    @Override
    public String getTranslationKey(ItemStack stack) {
        return "affinitytome." + getAffinity(stack).getTranslationKey();
    }

    private ItemStack setAffinity(ItemStack itemStack, Affinity affinity) {
        ResourceLocation rl = RegistryHandler.getAffinityRegistry().getKey(affinity);
        if (rl == null)
            throw new IllegalStateException("Affinity not registered");
        itemStack.getOrCreateTag().putString(AFFINITY_KEY, rl.toString());
        return itemStack;
    }

    private ItemStack setAffinity(ItemStack itemStack, ResourceLocation affinity) {
        if (affinity == null)
            throw new IllegalStateException("Affinity not registered");
        itemStack.getOrCreateTag().putString(AFFINITY_KEY, affinity.toString());
        return itemStack;
    }
}
