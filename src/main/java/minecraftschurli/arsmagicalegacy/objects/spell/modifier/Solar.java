package minecraftschurli.arsmagicalegacy.objects.spell.modifier;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public final class Solar extends SpellModifier {
    @Override
    public EnumSet<SpellModifiers> getAspectsModified() {
        return EnumSet.of(SpellModifiers.RANGE, SpellModifiers.RADIUS, SpellModifiers.DAMAGE, SpellModifiers.DURATION, SpellModifiers.HEALING);
    }

    @Override
    public float getManaCostMultiplier(ItemStack spellStack, int stage, int quantity) {
        return 0;
    }

    @Override
    public float getModifier(SpellModifiers type, LivingEntity caster, Entity target, World world, CompoundNBT nbt) {
        switch (type) {
            case DAMAGE:
                return modifyValueOnTime(world, 2.4f);
            case DURATION:
                return modifyValueOnTime(world, 5);
            case HEALING:
                return modifyValueOnTime(world, 2);
            case RANGE:
            case RADIUS:
                return 2;
        }
        return 1;
    }

    private float modifyValueOnTime(World world, float value) {
        float multiplierFromTime = (float) (Math.cos((((world.getGameTime() % 24000) / 3800f) * ((world.getGameTime() % 24000) / 24000f) - 13000f) * (180f / 3.14159265358979)) * 1.5f) + 1;
        if (multiplierFromTime < 0)
            multiplierFromTime *= -0.5f;
        return value * multiplierFromTime;
    }
}
