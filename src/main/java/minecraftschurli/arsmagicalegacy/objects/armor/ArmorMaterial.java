package minecraftschurli.arsmagicalegacy.objects.armor;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;

public class ArmorMaterial implements IArmorMaterial {
    private static final int[] MAX_DAMAGE = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamage;
    private final int[] armorValues;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;

    public ArmorMaterial(String name, int maxDamage, int[] armor, int enchantability, SoundEvent sound, float toughness) {
        this.name = name;
        this.maxDamage = maxDamage;
        this.armorValues = armor;
        this.enchantability = enchantability;
        this.soundEvent = sound;
        this.toughness = toughness;
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
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }
}
