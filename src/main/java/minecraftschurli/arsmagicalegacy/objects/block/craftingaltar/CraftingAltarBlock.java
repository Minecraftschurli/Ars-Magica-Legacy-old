package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import minecraftschurli.arsmagicalegacy.compat.patchouli.CapMatcher;
import minecraftschurli.arsmagicalegacy.compat.patchouli.MainMatcher;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.compat.patchouli.StairMatcher;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * @author Minecraftschurli
 * @version 2019-12-13
 */
public class CraftingAltarBlock extends Block {
    public static final Supplier<IMultiblock> ALTAR = PatchouliCompat.registerMultiblock("altar", patchouli ->
            patchouli.makeMultiblock(new String[][]
                            {{" CEC ", " SMN ", " SAN ", " SMN ", " CWC "},
                                    {" MZM ", " I I ", "     ", " Y Y ", " MZM "},
                                    {" MZMV", "     ", "     ", "     ", " MZM "},
                                    {" MZM ", "     ", "  0  ", "     ", " MZML"},
                                    {"MMMMM", "MMMMM", "MMCMM", "MMMMM", "MMMMM"}},
                    'C', new CapMatcher(),
                    'M', new MainMatcher(),
                    'V', PatchouliAPI.instance.propertyMatcher(Blocks.LEVER.getDefaultState().with(LeverBlock.HORIZONTAL_FACING, Direction.SOUTH), LeverBlock.HORIZONTAL_FACING),
                    'L', PatchouliAPI.instance.propertyMatcher(Blocks.LECTERN.getDefaultState().with(LecternBlock.FACING, Direction.SOUTH), LecternBlock.FACING),
                    'A', PatchouliAPI.instance.looseBlockMatcher(ModBlocks.ALTAR_CORE.get()),
                    'Z', PatchouliAPI.instance.strictBlockMatcher(ModBlocks.MAGIC_WALL.get()),
                    'S', new StairMatcher(Direction.SOUTH, Half.BOTTOM),
                    'N', new StairMatcher(Direction.NORTH, Half.BOTTOM),
                    'W', new StairMatcher(Direction.WEST, Half.BOTTOM),
                    'E', new StairMatcher(Direction.EAST, Half.BOTTOM),
                    'I', new StairMatcher(Direction.WEST, Half.TOP),
                    'Y', new StairMatcher(Direction.EAST, Half.TOP)));

    public static final BooleanProperty FORMED = BooleanProperty.create("formed");

    public CraftingAltarBlock() {
        super(Block.Properties.create(Material.IRON).harvestLevel(0).hardnessAndResistance(3).harvestTool(ToolType.PICKAXE));
        setDefaultState(getDefaultState().with(FORMED, false));
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isCreative() && player.getHeldItem(handIn).getItem() == Items.NETHER_STAR)
            ((CraftingAltarTileEntity) worldIn.getTileEntity(pos)).placeStructure(worldIn, player.getHorizontalFacing());
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CraftingAltarTileEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FORMED);
    }
}
