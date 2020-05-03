package minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.google.common.base.Strings;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import minecraftschurli.arsmagicalegacy.objects.block.celestialprism.CelestialPrismBlock;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
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

    public static final Supplier<IStateMatcher> CHALK_MATCHER = () -> PatchouliAPI.instance.strictBlockMatcher(ModBlocks.CHALK.get());

    public static final Supplier<IMultiblock> ALTAR = registerMultiblock("altar", patchouli ->
            patchouli.makeMultiblock(new String[][]{
                    {" CEC ", " SMN ", " SAN ", " SMN ", " CWC "},
                    {" MZM ", " I I ", "     ", " Y Y ", " MZM "},
                    {" MZMV", "     ", "     ", "     ", " MZM "},
                    {" MZM ", "     ", "  0  ", "     ", " MZML"},
                    {"MMMMM", "MMMMM", "MMCMM", "MMMMM", "MMMMM"}},
            'C', new CapMatcher(),
                    'M', new MainMatcher(),
                    'V', propertyMatcher(PatchouliAPI.instance, Blocks.LEVER.getDefaultState().with(LeverBlock.HORIZONTAL_FACING, Direction.SOUTH), LeverBlock.HORIZONTAL_FACING),
                    'L', propertyMatcher(PatchouliAPI.instance, Blocks.LECTERN.getDefaultState().with(LecternBlock.FACING, Direction.SOUTH), LecternBlock.FACING),
                    'A', PatchouliAPI.instance.looseBlockMatcher(ModBlocks.ALTAR_CORE.get()),
                    'Z', PatchouliAPI.instance.strictBlockMatcher(ModBlocks.MAGIC_WALL.get()),
                    'S', new StairMatcher(Direction.SOUTH, Half.BOTTOM),
                    'N', new StairMatcher(Direction.NORTH, Half.BOTTOM),
                    'W', new StairMatcher(Direction.WEST, Half.BOTTOM),
                    'E', new StairMatcher(Direction.EAST, Half.BOTTOM),
                    'I', new StairMatcher(Direction.WEST, Half.TOP),
                    'Y', new StairMatcher(Direction.EAST, Half.TOP)));

    public static final Supplier<IMultiblock> OBELISK_CHALK = registerMultiblock("obelisk_chalk", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"   ", " U ", "   "},
                    {"   ", " M ", "   "},
                    {"CCC", "C0C", "CCC"}},
            '0', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState()),
                    'M', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE)),
                    'U', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.UPPER)),
                    'C', CHALK_MATCHER.get())
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> OBELISK_PILLARS = registerMultiblock("obelisk_pillars", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"C   C", "     ", "  U  ", "     ", "C   C"},
                    {"B   B", "     ", "  M  ", "     ", "B   B"},
                    {"B   B", " CCC ", " C0C ", " CCC ", "B   B"}},
            '0', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState()),
                    'M', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE)),
                    'U', iPatchouliAPI.stateMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.UPPER)),
                    'C', iPatchouliAPI.looseBlockMatcher(Blocks.CHISELED_STONE_BRICKS),
                    'B', iPatchouliAPI.looseBlockMatcher(Blocks.STONE_BRICKS),
                    'C', CHALK_MATCHER.get())
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> CELESTIAL_PRISM_CHALK = registerMultiblock("celestial_prism_chalk", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"   ", " P ", "   "},
                    {"CCC", "C0C", "CCC"}},
            'C', CHALK_MATCHER.get(),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_1 = registerMultiblock("celestial_prism_pillar1", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"G   G", "     ", "     ", "     ", "G   G"},
                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
            'C', CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'G', iPatchouliAPI.strictBlockMatcher(Blocks.GLASS),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_2 = registerMultiblock("celestial_prism_pillar2", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"G   G", "     ", "     ", "     ", "G   G"},
                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
            'C', CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'G', iPatchouliAPI.strictBlockMatcher(Blocks.GOLD_BLOCK),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState())));

    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_3 = registerMultiblock("celestial_prism_pillar3", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"D   D", "     ", "     ", "     ", "D   D"},
                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
            'C', CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'D', iPatchouliAPI.strictBlockMatcher(Blocks.DIAMOND_BLOCK),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> CELESTIAL_PRISM_PILLAR_4 = registerMultiblock("celestial_prism_pillar4", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"M   M", "     ", "     ", "     ", "M   M"},
                    {"Q   Q", "     ", "  P  ", "     ", "Q   Q"},
                    {"Q   Q", " CCC ", " C0C ", " CCC ", "Q   Q"}},
            'C', CHALK_MATCHER.get(),
                    'Q', iPatchouliAPI.strictBlockMatcher(Blocks.QUARTZ_PILLAR),
                    'M', iPatchouliAPI.strictBlockMatcher(ModBlocks.MOONSTONE_BLOCK.get()),
                    'P', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState().with(CelestialPrismBlock.HALF, DoubleBlockHalf.UPPER)),
                    '0', iPatchouliAPI.stateMatcher(ModBlocks.CELESTIAL_PRISM.get().getDefaultState()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> BLACK_AUREM_CHALK = registerMultiblock("black_aurem_chalk", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"   ", " B ", "   "},
                    {"CCC", "C0C", "CCC"}},
            'C', CHALK_MATCHER.get(),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> BLACK_AUREM_PILLAR_1 = registerMultiblock("black_aurem_pillar1", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"G   G", "     ", "     ", "     ", "G   G"},
                    {"N   N", "     ", "  B  ", "     ", "N   N"},
                    {"N   N", " CCC ", " C0C ", " CCC ", "N   N"}},
            'C', CHALK_MATCHER.get(),
                    'N', iPatchouliAPI.strictBlockMatcher(Blocks.NETHER_BRICKS),
                    'G', iPatchouliAPI.strictBlockMatcher(Blocks.GOLD_BLOCK),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> BLACK_AUREM_PILLAR_2 = registerMultiblock("black_aurem_pillar2", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"D   D", "     ", "     ", "     ", "D   D"},
                    {"N   N", "     ", "  B  ", "     ", "N   N"},
                    {"N   N", " CCC ", " C0C ", " CCC ", "N   N"}},
            'C', CHALK_MATCHER.get(),
                    'N', iPatchouliAPI.strictBlockMatcher(Blocks.NETHER_BRICKS),
                    'D', iPatchouliAPI.strictBlockMatcher(Blocks.DIAMOND_BLOCK),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));

    public static final Supplier<IMultiblock> BLACK_AUREM_PILLAR_3 = registerMultiblock("black_aurem_pillar3", iPatchouliAPI ->
            iPatchouliAPI.makeMultiblock(new String[][]{
                    {"H   H", "     ", "     ", "     ", "H   H"},
                    {"N   N", "     ", "  B  ", "     ", "N   N"},
                    {"N   N", " CCC ", " C0C ", " CCC ", "N   N"}},
            'C', CHALK_MATCHER.get(),
                    'N', iPatchouliAPI.strictBlockMatcher(Blocks.NETHER_BRICKS),
                    'H', iPatchouliAPI.strictBlockMatcher(ModBlocks.CHIMERITE_BLOCK.get()),
                    'B', iPatchouliAPI.strictBlockMatcher(ModBlocks.BLACK_AUREM.get()))
                    .setSymmetrical(true));

    public static Supplier<IMultiblock> registerMultiblock(
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

    public static IStateMatcher propertyMatcher(PatchouliAPI.IPatchouliAPI api, BlockState match, IProperty<?>... properties) {
        if (match.getProperties().containsAll(Arrays.asList(properties))) {
            return api.predicateMatcher(match, (BlockState state) -> {
                if (state.getBlock() != match.getBlock()) return false;
                for (IProperty<?> property : properties) {
                    if (!state.has(property) || !Objects.equals(state.get(property), match.get(property)))
                        return false;
                }
                return true;
            });
        }
        return null;
    }
}
