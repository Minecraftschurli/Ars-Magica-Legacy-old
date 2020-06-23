package minecraftschurli.arsmagicalegacy.util;

import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public final class BlockUtil {
    public static VoxelShape combineShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = VoxelShapes.combineAndSimplify(first, second, IBooleanFunction.OR);
        for(VoxelShape shape : others) result = VoxelShapes.combineAndSimplify(result, shape, IBooleanFunction.OR);
        return result;
    }
}
