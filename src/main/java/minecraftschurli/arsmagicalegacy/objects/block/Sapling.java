package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.trees.Tree;

public class Sapling extends SaplingBlock {
    public Sapling(Tree treeIn, Block.Properties properties) {
        super(treeIn, properties.doesNotBlockMovement().hardnessAndResistance(0.0f).sound(SoundType.PLANT));
    }
}
