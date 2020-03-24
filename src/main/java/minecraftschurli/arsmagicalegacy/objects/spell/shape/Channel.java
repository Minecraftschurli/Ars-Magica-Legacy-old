package minecraftschurli.arsmagicalegacy.objects.spell.shape;

import java.util.EnumSet;
import minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import minecraftschurli.arsmagicalegacy.api.spell.SpellModifiers;
import minecraftschurli.arsmagicalegacy.api.spell.SpellShape;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.EtheriumSpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ISpellIngredient;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.ItemStackSpellIngredient;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Attract;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Repel;
import minecraftschurli.arsmagicalegacy.objects.spell.component.Telekinesis;
import minecraftschurli.arsmagicalegacy.util.SpellUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public class Channel extends SpellShape {
    @Override
    public boolean isChanneled() {
        return true;
    }

    @Override
    public float manaCostMultiplier(ItemStack spellStack) {
        return 1;
    }

    @Override
    public boolean isTerminusShape() {
        return true;
    }

    @Override
    public boolean isPrincipumShape() {
        return false;
    }

    @Override
    public SpellCastResult beginStackStage(Item item, ItemStack stack, LivingEntity caster, LivingEntity target, World world, double x, double y, double z, Direction side, boolean giveXP, int useCount) {
        if (useCount % 10 == 0 || SpellUtils.componentIsPresent(stack, Telekinesis.class) || SpellUtils.componentIsPresent(stack, Attract.class) || SpellUtils.componentIsPresent(stack, Repel.class))
            return SpellUtils.applyStageToEntity(stack, caster, world, caster, giveXP);
        return SpellCastResult.SUCCESS;
    }

    @Override
    public ISpellIngredient[] getRecipe() {
        return new ISpellIngredient[]{
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ASH.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.TARMA_ROOT.get())),
                new ItemStackSpellIngredient(new ItemStack(ModItems.ARCANE_ESSENCE.get())),
                new EtheriumSpellIngredient(500)
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
