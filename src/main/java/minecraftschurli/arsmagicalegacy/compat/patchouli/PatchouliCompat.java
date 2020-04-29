package minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.google.common.base.Strings;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Minecraftschurli
 * @version 2020-04-17
 */
public class PatchouliCompat {
    private static final Map<ResourceLocation, IMultiblock> MULTIBLOCKS = new HashMap<>();
    private static final Map<ResourceLocation, Function<PatchouliAPI.IPatchouliAPI, IMultiblock>> MULTIBLOCK_FACTORIES = new HashMap<>();

    private static final String SPELL_COMPONENT_TEMPLATE = "{\"components\": [{\"type\": \"custom\",\"class\": \"minecraftschurli.arsmagicalegacy.compat.patchouli.SpellComponentPage\",\"component\": \"#component\"}]}";

    private static final ResourceLocation COMPENDIUM = new ResourceLocation(ArsMagicaAPI.MODID, "arcane_compendium");

    public static final Supplier<IMultiblock> ALTAR = registerMultiblock("altar", patchouli ->
            patchouli.makeMultiblock(new String[][]{
                    {" CEC ", " SMN ", " SAN ", " SMN ", " CWC "},
                    {" MZM ", " I I ", "     ", " Y Y ", " MZM "},
                    {" MZMV", "     ", "     ", "     ", " MZM "},
                    {" MZM ", "     ", "  0  ", "     ", " MZML"},
                    {"MMMMM", "MMMMM", "MMCMM", "MMMMM", "MMMMM"}},
            'C', new CapMatcher(),
                    'M', new MainMatcher(),
                    'V', patchouli.stateMatcher(Blocks.LEVER.getDefaultState().with(LeverBlock.HORIZONTAL_FACING, Direction.SOUTH)),
                    'L', patchouli.stateMatcher(Blocks.LECTERN.getDefaultState().with(LecternBlock.FACING, Direction.SOUTH)),
                    'A', patchouli.stateMatcher(ModBlocks.ALTAR_CORE.get().getDefaultState()),
                    'Z', patchouli.stateMatcher(ModBlocks.MAGIC_WALL.get().getDefaultState()),
                    'S', new StairMatcher(Direction.SOUTH, Half.BOTTOM),
                    'N', new StairMatcher(Direction.NORTH, Half.BOTTOM),
                    'W', new StairMatcher(Direction.WEST, Half.BOTTOM),
                    'E', new StairMatcher(Direction.EAST, Half.BOTTOM),
                    'I', new StairMatcher(Direction.WEST, Half.TOP),
                    'Y', new StairMatcher(Direction.EAST, Half.TOP))
                    .setSymmetrical(false)
    );

    public static final Supplier<IMultiblock> OBELISK = null;
    public static final Supplier<IMultiblock> CELESTIAL_PRISM = null;
    public static final Supplier<IMultiblock> BLACK_AUREM = null;

    private static Supplier<IMultiblock> registerMultiblock(
            final String name,
            final Function<PatchouliAPI.IPatchouliAPI, IMultiblock> factory
    ) {
        Objects.requireNonNull(factory);
        Objects.requireNonNull(Strings.emptyToNull(name));
        ResourceLocation key = new ResourceLocation(ArsMagicaAPI.MODID, name);
        MULTIBLOCK_FACTORIES.put(key, factory);
        return () -> MULTIBLOCKS.get(key);
    }

    public static ItemStack getCompendiumStack() {
        return PatchouliAPI.instance.getBookStack(COMPENDIUM);
    }

    public static void init() {
        PatchouliAPI.IPatchouliAPI api = PatchouliAPI.instance;
        MULTIBLOCK_FACTORIES.forEach((name, factory) ->
                MULTIBLOCKS.put(name, api.registerMultiblock(name, factory.apply(api))));
    }

    public static void clientInit() {
        PatchouliAPI.instance.registerTemplateAsBuiltin(
                new ResourceLocation(ArsMagicaAPI.MODID, "spell_component"),
                () -> new ByteArrayInputStream(SPELL_COMPONENT_TEMPLATE.getBytes()));
    }

    public static boolean isCompendium(ItemStack stack) {
        return getCompendiumStack().getItem() == stack.getItem() &&
                COMPENDIUM.equals(ResourceLocation.tryCreate(stack.getOrCreateTag().getString("patchouli:book")));
    }
}
