package minecraftschurli.arsmagicalegacy.objects.item.affinity;

import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2020-05-17
 */
public class AffinityEssenceItem extends Item implements IAffinityItem {
    public AffinityEssenceItem() {
        super(ModItems.ITEM_64);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
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
    public String getTranslationKey(ItemStack stack) {
        return "affinityessence." + IAffinityItem.getAffinity(stack).getTranslationKey();
    }
}
