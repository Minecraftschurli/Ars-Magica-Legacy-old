package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;

public class PressurePlate extends PressurePlateBlock {
    public PressurePlate(Properties builder) {
        super(Sensitivity.EVERYTHING, builder);
    }
}
