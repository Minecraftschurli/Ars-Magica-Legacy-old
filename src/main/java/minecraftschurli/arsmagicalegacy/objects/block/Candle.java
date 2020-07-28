package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.TorchBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

/**
 * @author Minecraftschurli
 * @version 2020-05-17
 */
public class Candle extends TorchBlock {
    public Candle() {
        super(Properties.create(Material.WOOD).harvestTool(ToolType.AXE).lightValue(12));
    }
}
