package minecraftschurli.arsmagicalegacy.objects.fluid;

import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.init.ModFluids;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction8;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public abstract class LiquidEssenceFluid extends ForgeFlowingFluid {
    protected LiquidEssenceFluid() {
        super(ModFluids.LIQUID_ESSENCE_PROPERTIES);
    }

    @Override
    protected void randomTick(World worldIn, BlockPos pos, IFluidState state, Random random) {
        if (!worldIn.isRemote && state.isSource()) {
            getLecternInRange(worldIn, pos, 2)
                    .filter(blockPos -> worldIn.getBlockState(blockPos).get(LecternBlock.HAS_BOOK))
                    .ifPresent(blockPos -> {
                        TileEntity te = worldIn.getTileEntity(blockPos);
                        if (te instanceof LecternTileEntity) {
                            LecternTileEntity lectern = (LecternTileEntity) te;
                            if (lectern.getBook().getItem() == net.minecraft.item.Items.WRITABLE_BOOK) {
                                lectern.clear();
                                LecternBlock.setHasBook(worldIn, blockPos, worldIn.getBlockState(blockPos), false);
                                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                                InventoryHelper.spawnItemStack(
                                        worldIn,
                                        blockPos.getX(),
                                        blockPos.getY() + 1.5,
                                        blockPos.getZ(),
                                        PatchouliCompat.getCompendiumStack()
                                );
                            }
                        }
                    });
        }
    }

    @Override
    protected boolean ticksRandomly() {
        return true;
    }

    @Override
    public int getTickRate(IWorldReader world) {
        return 6;
    }

    private Optional<BlockPos> getLecternInRange(final World worldIn, final BlockPos pos, int YRange) {
        return IntStream.range(0, YRange)
                .boxed()
                .map(y -> {
                    Set<BlockPos> pos1 = Arrays.stream(Direction8.values())
                            .map(Direction8::getDirections)
                            .map((Function<? super Set<Direction>, BlockPos>) directions -> {
                                AtomicReference<BlockPos> newPos = new AtomicReference<>(pos.offset(Direction.UP, y));
                                directions.forEach(direction -> newPos.updateAndGet(blockPos -> blockPos.offset(direction)));
                                return newPos.get();
                            })
                            .collect(Collectors.toSet());
                    if (y > 0) {
                        pos1.add(pos.offset(Direction.UP, y));
                    }
                    return pos1;
                })
                .map(Collection::stream)
                .reduce(Stream::concat)
                .orElseGet(Stream::empty)
                .filter(blockPos -> worldIn.getBlockState(blockPos).getBlock() == Blocks.LECTERN)
                .findFirst();
    }

    public static class Source extends LiquidEssenceFluid {
        public Source() {
            super();
        }

        @Override
        public boolean isSource(IFluidState state) {
            return true;
        }

        @Override
        public int getLevel(IFluidState p_207192_1_) {
            return 8;
        }
    }

    public static class Flowing extends LiquidEssenceFluid {
        public Flowing() {
            super();
            setDefaultState(getStateContainer().getBaseState().with(LEVEL_1_8, 7));
        }

        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(IFluidState state) {
            return state.get(LEVEL_1_8);
        }

        public boolean isSource(IFluidState state) {
            return false;
        }
    }
}
