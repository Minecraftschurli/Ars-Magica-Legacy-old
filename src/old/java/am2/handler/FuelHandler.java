package am2.handler;

import am2.blocks.*;
import am2.defs.BlockDefs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

/**
 * Created by Orinion on 09.01.2017.
 */
public class FuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        Item fuelItem = fuel.getItem();
        Block fuelBlock = Block.getBlockFromItem(fuelItem);

        if (fuelBlock == BlockDefs.witchwoodPlanks) return 300;
        if (fuelBlock == BlockDefs.witchwoodLog) return 300;
        if (fuelBlock == BlockDefs.witchwoodSapling) return 100;
        if (fuelBlock == BlockDefs.witchwoodStairs) return 300;

        return 0;
    }
}
