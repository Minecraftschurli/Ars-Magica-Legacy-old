package minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.google.common.base.Strings;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.init.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.IStateMatcher;
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

    public static final Supplier<IStateMatcher> CHALK_MATCHER = () -> PatchouliAPI.instance.strictBlockMatcher(ModBlocks.CHALK.get());

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
}
