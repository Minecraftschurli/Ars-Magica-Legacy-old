package minecraftschurli.arsmagicalegacy.objects.armor;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ArmorMaterial implements IArmorMaterial {
    private static final int[] MAX_DAMAGE = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamage;
    private final int[] armorValues;
    private final int enchantability;
    private final float toughness;
    private final LazyValue<Ingredient> repair;

    public ArmorMaterial(String name, int maxDamage, int head, int chest, int legs, int feet, int enchantability, float toughness, Supplier<? extends IItemProvider> repairItem) {
        this.name = name;
        this.maxDamage = maxDamage;
        this.armorValues = new int[]{head, chest, legs, feet};
        this.enchantability = enchantability;
        this.toughness = toughness;
        this.repair = new LazyValue<>(() -> Ingredient.fromItems(repairItem.get()));
    }

    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE[slotIn.getIndex()] * this.maxDamage;
    }

    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return this.armorValues[slotIn.getIndex()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    @Nonnull
    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        return this.repair.getValue();
    }

    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }
}
