package minecraftschurli.arsmagicalegacy.objects.armor;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

import java.util.function.*;

public class ArmorMaterial implements IArmorMaterial {
    private static final int[] MAX_DAMAGE = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamage;
    private final int[] armorValues;
    private final int enchantability;
    private final float toughness;
    private final LazyValue<Ingredient> repair;

    public ArmorMaterial(String name, int maxDamage, int head, int chest, int legs, int feet, int enchantability, float toughness, Supplier<Item> repairItem) {
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

    public SoundEvent getSoundEvent() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repair.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }
}
