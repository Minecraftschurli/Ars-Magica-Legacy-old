package minecraftschurli.arsmagicalegacy.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.advancements.*;
import minecraftschurli.arsmagicalegacy.init.*;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

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
        Advancement.Builder.builder()
                .withDisplay(ArsMagicaLegacy.getCompendium(), new TranslationTextComponent(ArsMagicaAPI.MODID+"advancements.compendium.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+"advancements.compendium.description"),
                        new ResourceLocation(ArsMagicaAPI.MODID, "textures/block/vinteum_ore.png"), FrameType.TASK, true, false, true)
                .withCriterion("got_compendium", InventoryChangeTrigger.Instance.forItems(ArsMagicaLegacy.getCompendium().getItem()))
                .register(consumer, ArsMagicaAPI.MODID+":compendium");
        Advancement.Builder.builder()
                .withDisplay(InfinityOrbItem.getWithSkillPoint(ModSpellParts.SILVER_POINT.get()), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.silver.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.silver.description"),
                        null, FrameType.CHALLENGE, true, false, true)
                .withCriterion("got_silver_skill", SilverSkillTrigger.Instance.create())
                .register(consumer, ArsMagicaAPI.MODID+":silver");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level5.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level5.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(5)).register(consumer, ArsMagicaAPI.MODID + ":level5");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level10.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level10.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(10)).register(consumer, ArsMagicaAPI.MODID + ":level10");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level15.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level15.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(15)).register(consumer, ArsMagicaAPI.MODID + ":level15");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level20.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level20.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(20)).register(consumer, ArsMagicaAPI.MODID + ":level20");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level25.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level25.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(25)).register(consumer, ArsMagicaAPI.MODID + ":level25");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level30.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level30.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(30)).register(consumer, ArsMagicaAPI.MODID + ":level30");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level35.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level35.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(35)).register(consumer, ArsMagicaAPI.MODID + ":level35");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level40.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level40.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(40)).register(consumer, ArsMagicaAPI.MODID + ":level40");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level45.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level45.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(45)).register(consumer, ArsMagicaAPI.MODID + ":level45");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level50.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level50.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(50)).register(consumer, ArsMagicaAPI.MODID + ":level50");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level55.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level55.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(55)).register(consumer, ArsMagicaAPI.MODID + ":level55");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level60.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level60.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(60)).register(consumer, ArsMagicaAPI.MODID + ":level60");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level65.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level65.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(65)).register(consumer, ArsMagicaAPI.MODID + ":level65");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level70.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level70.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(70)).register(consumer, ArsMagicaAPI.MODID + ":level70");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level75.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level75.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(75)).register(consumer, ArsMagicaAPI.MODID + ":level75");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level80.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level80.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(80)).register(consumer, ArsMagicaAPI.MODID + ":level80");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level85.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level85.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(85)).register(consumer, ArsMagicaAPI.MODID + ":level85");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level90.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level90.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(90)).register(consumer, ArsMagicaAPI.MODID + ":level90");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level95.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level95.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(95)).register(consumer, ArsMagicaAPI.MODID + ":level95");
        Advancement.Builder.builder().withDisplay(ModItems.VINTEUM.get(), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level100.title"), new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.level100.description"), null, FrameType.TASK, false, false, true).withCriterion("levelup", MagicLevelTrigger.Instance.forLevel(100)).register(consumer, ArsMagicaAPI.MODID + ":level100");
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
