package minecraftschurli.arsmagicalegacy.init;

import com.google.common.collect.Lists;
import minecraftschurli.arsmagicalegacy.api.registry.RitualRegistry;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2020-05-17
 */
public final class ModRituals implements IInit {
    public static RegistryObject<RitualRegistry.DefaultRitual> CORRUPTION = RitualRegistry.registerRitual("corruption",
            () -> Lists.newArrayList(new ItemStack(ModItems.SUNSTONE.get()), new ItemStack(ModItems.MONSTER_FOCUS.get())),
            PatchouliCompat.registerMultiblock("corruption_ritual", iPatchouliAPI -> iPatchouliAPI.makeMultiblock(new String[][]{
                            {"     ", "     ", "     ", "  U  ", "     ", "     ", "     "},
                            {"     ", "     ", "     ", "  O  ", "     ", "     ", "     "},
                            {" C C ", "CICIC", "C C C", " C0C ", "C C C", "CICIC", " C C "}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'I', PatchouliCompat.CANDLE_MATCHER.get(),
                    '0', iPatchouliAPI.propertyMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.LOWER), ObeliskBlock.PART),
                    'O', iPatchouliAPI.propertyMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE), ObeliskBlock.PART),
                    'U', iPatchouliAPI.propertyMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.UPPER), ObeliskBlock.PART))),
            ModItems.BLACK_AUREM.lazyMap(ItemStack::new));

    public static RegistryObject<RitualRegistry.DefaultRitual> PURIFICATION = RitualRegistry.registerRitual("purification",
            () -> Lists.newArrayList(new ItemStack(ModItems.MOONSTONE.get()), new ItemStack(ModItems.MANA_FOCUS.get())),
            PatchouliCompat.registerMultiblock("purification_ritual", iPatchouliAPI -> iPatchouliAPI.makeMultiblock(new String[][]{
                            {"       ", "       ", "       ", "   U   ", "       ", "       ", "       "},
                            {"       ", "       ", "       ", "   O   ", "       ", "       ", "       "},
                            {"  CCC  ", " IC CI ", "CCC CCC", "C  0  C", "CCC CCC", " IC CI ", "  CCC  "}},
                    'C', PatchouliCompat.CHALK_MATCHER.get(),
                    'I', PatchouliCompat.CANDLE_MATCHER.get(),
                    '0', iPatchouliAPI.propertyMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.LOWER), ObeliskBlock.PART),
                    'O', iPatchouliAPI.propertyMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.MIDDLE), ObeliskBlock.PART),
                    'U', iPatchouliAPI.propertyMatcher(ModBlocks.OBELISK.get().getDefaultState().with(ObeliskBlock.PART, ObeliskBlock.Part.UPPER), ObeliskBlock.PART))),
            ModItems.CELESTIAL_PRISM.lazyMap(ItemStack::new));

    public static void register() {
    }
}
