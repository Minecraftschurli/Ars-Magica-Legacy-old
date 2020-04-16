package minecraftschurli.arsmagicalegacy.api.spell.crafting;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author Minecraftschurli
 * @version 2019-12-09
 */
public class SpellIngredientList extends ArrayList<ISpellIngredient> implements INBTSerializable<ListNBT> {
    private boolean computed = false;

    private static ISpellIngredient deserializeIngredient(INBT inbt) {
        CompoundNBT nbt = (CompoundNBT) inbt;
        return IngredientTypes.deserialize(nbt);
    }

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
        return stream().map(INBTSerializable::serializeNBT).collect(Collector.of(ListNBT::new, AbstractList::add, (inbts, inbts2) -> {
            inbts.addAll(inbts2);
            return inbts;
        }));
    }

    @Override
    public void deserializeNBT(ListNBT nbt) {
        this.clear();
        this.addAll(nbt.stream().map(SpellIngredientList::deserializeIngredient).collect(Collectors.toList()));
    }

    public SpellIngredientList compute() {
        SpellIngredientList copy = new SpellIngredientList();
        copy.addAll(this);
        boolean modified;
        do {
            modified = false;
            inner:
            for (int i = 0; i < copy.size(); i++) {
                for (int j = 0; j < copy.size(); j++) {
                    ISpellIngredient ingredient1 = copy.get(i);
                    ISpellIngredient ingredient2 = copy.get(j);
                    if (ingredient1 == ingredient2) continue;
                    if (canCombine(ingredient1, ingredient2)) {
                        copy.remove(ingredient1);
                        copy.remove(ingredient2);
                        copy.add(combine(ingredient1, ingredient2));
                        modified = true;
                        break inner;
                    }
                }
            }
        } while (modified);
        this.computed = true;
        return copy;
    }

    public List<ITextComponent> getTooltip() {
        return (computed ? this : compute()).stream().map(ISpellIngredient::getTooltip).collect(Collectors.toList());
    }

    private ISpellIngredient combine(ISpellIngredient ingredient1, ISpellIngredient ingredient2) {
        return ingredient1.combine(ingredient2);
    }

    private boolean canCombine(ISpellIngredient ingredient1, ISpellIngredient ingredient2) {
        return ingredient1.canCombine(ingredient2) && ingredient2.canCombine(ingredient1);
    }
}
