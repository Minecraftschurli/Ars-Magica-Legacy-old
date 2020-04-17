package minecraftschurli.arsmagicalegacy.compat.patchouli;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

/**
 * @author Minecraftschurli
 * @version 2020-04-17
 */
public class PatchouliMultiblocks {
    public static void register() {
        IMultiblock multiblock = PatchouliAPI.instance.makeMultiblock(new String[][]{
                        {" CEC ", " SMN ", " SAN ", " SMN ", " CWC "},
                        {" MZM ", " I I ", "     ", " Y Y ", " MZM "},
                        {" MZMV", "     ", "     ", "     ", " MZM "},
                        {" MZM ", "     ", "  0  ", "     ", " MZML"},
                        {"MMMMM", "MMMMM", "MMCMM", "MMMMM", "MMMMM"}},
                'C', new CapMatcher(),
                'M', new MainMatcher(),
                'V', PatchouliAPI.instance.stateMatcher(Blocks.LEVER.getDefaultState().with(LeverBlock.HORIZONTAL_FACING, Direction.SOUTH)),
                'L', PatchouliAPI.instance.stateMatcher(Blocks.LECTERN.getDefaultState().with(LecternBlock.FACING, Direction.SOUTH)),
                'A', PatchouliAPI.instance.stateMatcher(ModBlocks.ALTAR_CORE.get().getDefaultState()),
                'Z', PatchouliAPI.instance.stateMatcher(ModBlocks.MAGIC_WALL.get().getDefaultState()),
                'S', new StairMatcher(Direction.SOUTH, Half.BOTTOM),
                'N', new StairMatcher(Direction.NORTH, Half.BOTTOM),
                'W', new StairMatcher(Direction.WEST, Half.BOTTOM),
                'E', new StairMatcher(Direction.EAST, Half.BOTTOM),
                'I', new StairMatcher(Direction.WEST, Half.TOP),
                'Y', new StairMatcher(Direction.EAST, Half.TOP));
        multiblock.setSymmetrical(false);
        PatchouliAPI.instance.registerMultiblock(new ResourceLocation(ArsMagicaAPI.MODID, "altar"), multiblock);
    }
}
