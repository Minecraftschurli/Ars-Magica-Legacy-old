package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class SpellIngredientList extends ArrayList<ISpellIngredient> implements INBTSerializable<ListNBT> {
    private boolean computed = false;

    @Override
    public boolean add(ISpellIngredient iSpellIngredient) {
        computed = false;
        return super.add(iSpellIngredient);
    }

    @Override
    public void add(int index, ISpellIngredient element) {
        computed = false;
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends ISpellIngredient> c) {
        computed = false;
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ISpellIngredient> c) {
        computed = false;
        return super.addAll(index, c);
    }

    @Override
    public ListNBT serializeNBT() {
        return stream().map(INBTSerializable::serializeNBT).collect(Collector.of(ListNBT::new, AbstractList::add, (inbts, inbts2) -> {inbts.addAll(inbts2);return inbts;}));
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        this.clear();
        this.addAll(nbt.stream().map(SpellIngredientList::deserializeIngredient).collect(Collectors.toList()));
    }

    public SpellIngredientList compute() {
        for (ISpellIngredient ingredient1 : this) {
            for (ISpellIngredient ingredient2 : this) {
                if (ingredient1 == ingredient2) continue;
                if (! canCombine(ingredient1, ingredient2)) continue;
                this.remove(ingredient1);
                this.remove(ingredient2);
                this.add(combine(ingredient1, ingredient2));
            }
        }
        computed = true;
        return this;
    }

    public List<ITextComponent> getTooltip() {
        if (!computed) compute();
        return stream().map(ISpellIngredient::getTooltip).collect(Collectors.toList());
    }

    private ISpellIngredient combine(ISpellIngredient ingredient1, ISpellIngredient ingredient2) {
        return ingredient1.combine(ingredient2);
    }

    private boolean canCombine(ISpellIngredient ingredient1, ISpellIngredient ingredient2) {
        return ingredient1.canCombine(ingredient2) && ingredient2.canCombine(ingredient1);
    }

    private static ISpellIngredient deserializeIngredient(INBT inbt) {
        CompoundNBT nbt = (CompoundNBT)inbt;
        return IngredientTypes.get(nbt.getString(ISpellIngredient.TYPE_KEY), nbt);
    }
}
