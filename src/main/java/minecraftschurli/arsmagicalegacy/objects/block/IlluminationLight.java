package minecraftschurli.arsmagicalegacy.objects.block;

import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class IlluminationLight extends Block {
    public IlluminationLight() {
        super(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0).doesNotBlockMovement().lightValue(15));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.create(0, 0, 0, 1, 0.02, 1);
    }
}
