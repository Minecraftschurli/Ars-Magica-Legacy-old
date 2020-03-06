package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;

public class Sapling extends SaplingBlock {
    public Sapling(Tree treeIn) {
        super(treeIn, Block.Properties.create(Material.PLANTS).tickRandomly().doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT));
    }
}
