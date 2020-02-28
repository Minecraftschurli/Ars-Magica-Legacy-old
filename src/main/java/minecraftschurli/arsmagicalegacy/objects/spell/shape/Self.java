package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.*;
import minecraftschurli.arsmagicalegacy.api.spell.*;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class Self extends SpellShape {
    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 0.5f;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public SpellCastResult beginStackStage(ISpellItem item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        SpellCastResult result = SpellUtils.applyStageToEntity(stack, caster, world, caster, giveXP);
        if (result != SpellCastResult.SUCCESS) return result;
        return SpellUtils.applyStackStage(stack, caster, target, x, y, z, null, world, true, giveXP, 0);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.LESSER_FOCUS.get())),
                new EssenceSpellIngredient(500, EssenceType.NEUTRAL)
        };
    }

    @Override
    public void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe) {

    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }
}
