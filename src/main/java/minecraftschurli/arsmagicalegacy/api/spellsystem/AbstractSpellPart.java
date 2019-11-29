package minecraftschurli.arsmagicalegacy.api.spellsystem;

import net.minecraft.nbt.*;
import net.minecraftforge.registries.*;

import java.util.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-16
 */
public abstract class AbstractSpellPart extends ForgeRegistryEntry<AbstractSpellPart> {
    /**
     * Supports :
     * ItemStacks
     * OreDict String
     * Essence Strings ("E:mask1|mask2" (* for any), num)
     *
     * @return
     */
    public abstract ISpellIngredient[] getRecipe();

    public abstract void encodeBasicData(CompoundNBT tag, ISpellIngredient[] recipe);

    /**
     * What modifier affect this spell part?
     *
     * @return
     */
    public abstract EnumSet<SpellModifiers> getModifiers();
}
