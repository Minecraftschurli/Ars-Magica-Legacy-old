package minecraftschurli.arsmagicalegacy.objects.item;

import javafx.scene.transform.Affine;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.SkillPointRegistry;
import minecraftschurli.arsmagicalegacy.api.affinity.Affinity;
import minecraftschurli.arsmagicalegacy.api.skill.SkillPoint;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2020-02-20
 */
public class AffinityTomeItem extends Item {
    private static final String AFFINITY_KEY = "affinity";

    public AffinityTomeItem() {
        super(ModItems.ITEM_64);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            for (Affinity affinity : ArsMagicaAPI.getAffinityRegistry()) {
                items.add(setAffinity(new ItemStack(this), affinity));
            }
        }
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent(getTranslationKey(stack), getAffinity(stack).getName());
    }

    private Affinity getAffinity(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains(AFFINITY_KEY))
            return null;
        return ArsMagicaAPI.getAffinityRegistry().getValue(ResourceLocation.tryCreate(stack.getTag().getString(AFFINITY_KEY)));
    }

    private ItemStack setAffinity(ItemStack itemStack, Affinity affinity) {
        ResourceLocation rl = ArsMagicaAPI.getAffinityRegistry().getKey(affinity);
        if (rl == null)
            throw new IllegalStateException("Affinity not registered");
        itemStack.getOrCreateTag().putString(AFFINITY_KEY, rl.toString());
        return itemStack;
    }
}
