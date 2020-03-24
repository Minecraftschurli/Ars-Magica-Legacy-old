package minecraftschurli.arsmagicalegacy.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.advancements.MagicLevelTrigger;
import minecraftschurli.arsmagicalegacy.api.advancements.SilverSkillTrigger;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Minecraftschurli
 * @version 2020-02-28
 */
public class AMLAdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public AMLAdvancementProvider(DataGenerator generatorIn) {
        this.generator = generatorIn;
    }

    public void act(DirectoryCache cache) throws IOException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (p_204017_3_) -> {
            if (!set.add(p_204017_3_.getId())) {
                throw new IllegalStateException("Duplicate advancement " + p_204017_3_.getId());
            } else {
                Path path1 = getPath(path, p_204017_3_);

                try {
                    IDataProvider.save(GSON, cache, p_204017_3_.copy().serialize(), path1);
                } catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }

            }
        };
        addAdvancements(consumer);
    }

    private void addAdvancements(Consumer<Advancement> consumer) {
        Advancement root = registerAdvancement(ArsMagicaLegacy.getCompendium(), "compendium", new ResourceLocation(ArsMagicaAPI.MODID, "textures/block/vinteum_ore.png"), FrameType.TASK, false, false, true)
                .withCriterion("got_compendium", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ArsMagicaLegacy.getCompendium().getItem()).nbt(ArsMagicaLegacy.getCompendium().getTag()).build()))
                .register(consumer, ArsMagicaAPI.MODID+":compendium");
        Advancement silver = registerAdvancement(root, InfinityOrbItem.getWithSkillPoint(ModSpellParts.SILVER_POINT.get()), "silver", FrameType.CHALLENGE, false, false, true)
                .withCriterion("got_silver_skill", SilverSkillTrigger.Instance.create())
                .register(consumer, ArsMagicaAPI.MODID+":silver");
        Advancement lvl5 = registerAdvancement(root, ModItems.VINTEUM.get(), "level5", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(5))
                .register(consumer, ArsMagicaAPI.MODID+":level5");
        Advancement lvl10 = registerAdvancement(lvl5, ModItems.VINTEUM.get(), "level10", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(10))
                .register(consumer, ArsMagicaAPI.MODID+":level10");
        Advancement lvl15 = registerAdvancement(lvl10, ModItems.VINTEUM.get(), "level15", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(15))
                .register(consumer, ArsMagicaAPI.MODID+":level15");
        Advancement lvl20 = registerAdvancement(lvl15, ModItems.VINTEUM.get(), "level20", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(20))
                .register(consumer, ArsMagicaAPI.MODID+":level20");
        Advancement lvl25 = registerAdvancement(lvl20, ModItems.VINTEUM.get(), "level25", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(25))
                .register(consumer, ArsMagicaAPI.MODID+":level25");
        Advancement lvl30 = registerAdvancement(lvl25, ModItems.VINTEUM.get(), "level30", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(30))
                .register(consumer, ArsMagicaAPI.MODID+":level30");
        Advancement lvl35 = registerAdvancement(lvl30, ModItems.VINTEUM.get(), "level35", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(35))
                .register(consumer, ArsMagicaAPI.MODID+":level35");
        Advancement lvl40 = registerAdvancement(lvl35, ModItems.VINTEUM.get(), "level40", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(40))
                .register(consumer, ArsMagicaAPI.MODID+":level40");
        Advancement lvl45 = registerAdvancement(lvl40, ModItems.VINTEUM.get(), "level45", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(45))
                .register(consumer, ArsMagicaAPI.MODID+":level45");
        Advancement lvl50 = registerAdvancement(lvl45, ModItems.VINTEUM.get(), "level50", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(50))
                .register(consumer, ArsMagicaAPI.MODID+":level50");
        Advancement lvl55 = registerAdvancement(lvl50, ModItems.VINTEUM.get(), "level55", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(55))
                .register(consumer, ArsMagicaAPI.MODID+":level55");
        Advancement lvl60 = registerAdvancement(lvl55, ModItems.VINTEUM.get(), "level60", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(60))
                .register(consumer, ArsMagicaAPI.MODID+":level60");
        Advancement lvl65 = registerAdvancement(lvl60, ModItems.VINTEUM.get(), "level65", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(65))
                .register(consumer, ArsMagicaAPI.MODID+":level65");
        Advancement lvl70 = registerAdvancement(lvl65, ModItems.VINTEUM.get(), "level70", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(70))
                .register(consumer, ArsMagicaAPI.MODID+":level70");
        Advancement lvl75 = registerAdvancement(lvl70, ModItems.VINTEUM.get(), "level75", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(75))
                .register(consumer, ArsMagicaAPI.MODID+":level75");
        Advancement lvl80 = registerAdvancement(lvl75, ModItems.VINTEUM.get(), "level80", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(80))
                .register(consumer, ArsMagicaAPI.MODID+":level80");
        Advancement lvl85 = registerAdvancement(lvl80, ModItems.VINTEUM.get(), "level85", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(85))
                .register(consumer, ArsMagicaAPI.MODID+":level85");
        Advancement lvl90 = registerAdvancement(lvl85, ModItems.VINTEUM.get(), "level90", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(90))
                .register(consumer, ArsMagicaAPI.MODID+":level90");
        Advancement lvl95 = registerAdvancement(lvl90, ModItems.VINTEUM.get(), "level95", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(95))
                .register(consumer, ArsMagicaAPI.MODID+":level95");
        Advancement lvl100 = registerAdvancement(lvl95, ModItems.VINTEUM.get(), "level100", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(100))
                .register(consumer, ArsMagicaAPI.MODID+":level100");
    }

    private Advancement.Builder registerAdvancement(ItemStack display, String name, ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        String key = ArsMagicaAPI.MODID+".advancements."+name;
        return Advancement.Builder.builder().withDisplay(display, new TranslationTextComponent(key+".title"), new TranslationTextComponent(key+".description"), background, frame, showToast, announceToChat, hidden);
    }

    private Advancement.Builder registerAdvancement(IItemProvider display, String name, ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return registerAdvancement(new ItemStack(display), name, background, frame, showToast, announceToChat, hidden);
    }

    private Advancement.Builder registerAdvancement(Advancement parent, ItemStack display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        String key = ArsMagicaAPI.MODID+".advancements."+name;
        return Advancement.Builder.builder().withParent(parent).withDisplay(display, new TranslationTextComponent(key+".title"), new TranslationTextComponent(key+".description"), null, frame, showToast, announceToChat, hidden);
    }

    private Advancement.Builder registerAdvancement(Advancement parent, IItemProvider display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return registerAdvancement(parent, new ItemStack(display), name, frame, showToast, announceToChat, hidden);
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Nonnull
    public String getName() {
        return "AMLAdvancements";
    }
}
