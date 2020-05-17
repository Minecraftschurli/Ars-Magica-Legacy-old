package minecraftschurli.arsmagicalegacy.api.affinity;

import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeItem;

/**
 * @author Minecraftschurli
 * @version 2020-05-17
 */
public interface IAffinityItem extends IForgeItem {
    String AFFINITY_KEY = "affinity";

    static Affinity getAffinity(ItemStack stack) {
        ResourceLocation affinity;
        if (stack.isEmpty() || !(stack.getItem() instanceof IAffinityItem) || !stack.hasTag() || !stack.getTag().contains(AFFINITY_KEY)) {
            affinity = Affinity.NONE;
        } else {
            affinity = ResourceLocation.tryCreate(stack.getTag().getString(AFFINITY_KEY));
        }
        return RegistryHandler.getAffinityRegistry().getValue(affinity);
    }

    static <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, ResourceLocation affinity) {
        return item.getStackForAffinity(affinity);
    }

    static <T extends Item & IAffinityItem> ItemStack getStackForAffinity(T item, Affinity affinity) {
        return item.getStackForAffinity(affinity);
    }

    default ItemStack getStackForAffinity(ResourceLocation affinity) {
        return setAffinity(new ItemStack(getItem()), affinity);
    }

    default ItemStack getStackForAffinity(Affinity affinity) {
        return setAffinity(new ItemStack(getItem()), affinity);
    }

    default ItemStack setAffinity(ItemStack itemStack, Affinity affinity) {
        return setAffinity(itemStack, RegistryHandler.getAffinityRegistry().getKey(affinity));
    }

    default ItemStack setAffinity(ItemStack itemStack, ResourceLocation affinity) {
        if (affinity == null)
            throw new IllegalStateException("Affinity not registered");
        itemStack.getOrCreateTag().putString(AFFINITY_KEY, affinity.toString());
        return itemStack;
    }
}
