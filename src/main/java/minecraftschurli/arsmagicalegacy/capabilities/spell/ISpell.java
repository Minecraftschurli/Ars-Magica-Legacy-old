package minecraftschurli.arsmagicalegacy.capabilities.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
