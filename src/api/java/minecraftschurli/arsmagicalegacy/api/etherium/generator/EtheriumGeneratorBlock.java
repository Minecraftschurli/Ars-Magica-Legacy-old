package minecraftschurli.arsmagicalegacy.api.etherium.generator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-04-23
 */
public abstract class EtheriumGeneratorBlock<T extends EtheriumGeneratorTileEntity> extends Block {
    private final Supplier<TileEntityType<T>> tileEntityTypeSupplier;

    public EtheriumGeneratorBlock(Block.Properties properties, Supplier<TileEntityType<T>> tileEntityTypeSupplier) {
        super(properties);
        this.tileEntityTypeSupplier = tileEntityTypeSupplier;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileEntityTypeSupplier.get().create();
    }

    @Override
    public boolean canEntitySpawn(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull EntityType<?> type) {
        return false;
    }

    @Override
    public boolean causesSuffocation(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return false;
    }

    @Override
    public boolean isNormalCube(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return false;
    }

    public abstract float getMultiplier(World world, BlockState state, BlockPos pos);
}
