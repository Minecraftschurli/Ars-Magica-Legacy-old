package minecraftschurli.arsmagicalegacy.api.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
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
public abstract class AdvancementProvider implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public AdvancementProvider(DataGenerator generatorIn) {
        this.generator = generatorIn;
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    public void act(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = new HashSet<>();
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

    protected abstract void addAdvancements(Consumer<Advancement> consumer);

    protected Advancement.Builder registerAdvancement(ItemStack display, String name, ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        String key = ArsMagicaAPI.MODID + ".advancements." + name;
        return Advancement.Builder.builder().withDisplay(display, new TranslationTextComponent(key + ".title"), new TranslationTextComponent(key + ".description"), background, frame, showToast, announceToChat, hidden);
    }

    protected Advancement.Builder registerAdvancement(IItemProvider display, String name, ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return registerAdvancement(new ItemStack(display), name, background, frame, showToast, announceToChat, hidden);
    }

    protected Advancement.Builder registerAdvancement(Advancement parent, ItemStack display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        String key = ArsMagicaAPI.MODID + ".advancements." + name;
        return Advancement.Builder.builder().withParent(parent).withDisplay(display, new TranslationTextComponent(key + ".title"), new TranslationTextComponent(key + ".description"), null, frame, showToast, announceToChat, hidden);
    }

    protected Advancement.Builder registerAdvancement(Advancement parent, IItemProvider display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
        return registerAdvancement(parent, new ItemStack(display), name, frame, showToast, announceToChat, hidden);
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Nonnull
    public String getName() {
        return "Advancements";
    }
}
