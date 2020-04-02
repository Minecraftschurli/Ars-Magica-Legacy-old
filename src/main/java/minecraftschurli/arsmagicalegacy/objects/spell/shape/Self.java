package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemTagSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTags;
import minecraftschurli.arsmagicalegacy.util.SpellUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.EnumSet;

public final class Self extends SpellShape {
    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        SpellCastResult result = SpellUtil.applyStageEntity(stack, caster, world, caster, giveXP);
        if (result != SpellCastResult.SUCCESS) return result;
        return SpellUtil.applyStage(stack, caster, target, x, y, z, null, world, true, giveXP, 0);
    }

    @Override
    public EnumSet<SpellModifiers> getModifiers() {
        return EnumSet.noneOf(SpellModifiers.class);
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemTagSpellIngredient(ModTags.Items.DUSTS_VINTEUM),
                new ItemStackSpellIngredient(new ItemStack(ModItems.AUM.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.LESSER_FOCUS.get())),
                new EtheriumSpellIngredient(500, EtheriumType.NEUTRAL)
        };
    }

    @Override
    public boolean isChanneled() {
        return false;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public boolean isTerminusShape() {
        return false;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 0.5f;
    }
}
