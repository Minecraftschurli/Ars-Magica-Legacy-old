package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.*;
import net.minecraft.block.trees.*;

public class Sapling extends SaplingBlock {
    public Sapling(Tree treeIn, Block.Properties properties) {
        super(treeIn, properties.doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT));
    }
}
