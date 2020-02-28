package minecraftschurli.arsmagicalegacy.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.advancements.SilverSkillTrigger;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
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
                .withDisplay(InfinityOrbItem.getWithSkillPoint(ModSpellParts.SILVER_POINT.get()),
                        new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.silver.title"),
                        new TranslationTextComponent(ArsMagicaAPI.MODID+".advancements.silver.description"),
                        new ResourceLocation(ArsMagicaAPI.MODID,"textures/block/vinteum_ore.png"),
                        FrameType.CHALLENGE, true, false, true)
                .withCriterion("got_silver_skill", SilverSkillTrigger.Instance.create())
                .register(consumer, ArsMagicaAPI.MODID+":silver");

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
