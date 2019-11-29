package minecraftschurli.arsmagicalegacy.capabilities.spell;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public interface ISpell {
    boolean execute(World world, ItemStack stack, PlayerEntity player);

    int getCooldown();

    int getManaCost();

    int getBurnoutCost();
}
