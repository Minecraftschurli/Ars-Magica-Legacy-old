package minecraftschurli.arsmagicalegacy.data;

import java.util.function.Consumer;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.advancements.MagicLevelTrigger;
import minecraftschurli.arsmagicalegacy.api.advancements.SilverSkillTrigger;
import minecraftschurli.arsmagicalegacy.api.data.AdvancementProvider;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModSpellParts;
import minecraftschurli.arsmagicalegacy.objects.item.InfinityOrbItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-04-02
 */
public final class AMLAdvancementProvider extends AdvancementProvider {
    public AMLAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void addAdvancements(Consumer<Advancement> consumer) {
        Advancement root = registerAdvancement(ArsMagicaAPI.getCompendium(), "compendium", new ResourceLocation(ArsMagicaAPI.MODID, "textures/block/vinteum_ore.png"), FrameType.TASK, false, false, true)
                .withCriterion("got_compendium", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().item(ArsMagicaAPI.getCompendium().getItem()).nbt(ArsMagicaAPI.getCompendium().getTag()).build()))
                .register(consumer, ArsMagicaAPI.MODID + ":compendium");
        Advancement silver = registerAdvancement(root, InfinityOrbItem.getWithSkillPoint(ModSpellParts.SILVER_POINT.get()), "silver", FrameType.CHALLENGE, false, false, true)
                .withCriterion("got_silver_skill", SilverSkillTrigger.Instance.create())
                .register(consumer, ArsMagicaAPI.MODID + ":silver");
        Advancement lvl5 = registerAdvancement(root, ModItems.VINTEUM.get(), "level5", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(5))
                .register(consumer, ArsMagicaAPI.MODID + ":level5");
        Advancement lvl10 = registerAdvancement(lvl5, ModItems.VINTEUM.get(), "level10", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(10))
                .register(consumer, ArsMagicaAPI.MODID + ":level10");
        Advancement lvl15 = registerAdvancement(lvl10, ModItems.VINTEUM.get(), "level15", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(15))
                .register(consumer, ArsMagicaAPI.MODID + ":level15");
        Advancement lvl20 = registerAdvancement(lvl15, ModItems.VINTEUM.get(), "level20", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(20))
                .register(consumer, ArsMagicaAPI.MODID + ":level20");
        Advancement lvl25 = registerAdvancement(lvl20, ModItems.VINTEUM.get(), "level25", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(25))
                .register(consumer, ArsMagicaAPI.MODID + ":level25");
        Advancement lvl30 = registerAdvancement(lvl25, ModItems.VINTEUM.get(), "level30", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(30))
                .register(consumer, ArsMagicaAPI.MODID + ":level30");
        Advancement lvl35 = registerAdvancement(lvl30, ModItems.VINTEUM.get(), "level35", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(35))
                .register(consumer, ArsMagicaAPI.MODID + ":level35");
        Advancement lvl40 = registerAdvancement(lvl35, ModItems.VINTEUM.get(), "level40", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(40))
                .register(consumer, ArsMagicaAPI.MODID + ":level40");
        Advancement lvl45 = registerAdvancement(lvl40, ModItems.VINTEUM.get(), "level45", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(45))
                .register(consumer, ArsMagicaAPI.MODID + ":level45");
        Advancement lvl50 = registerAdvancement(lvl45, ModItems.VINTEUM.get(), "level50", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(50))
                .register(consumer, ArsMagicaAPI.MODID + ":level50");
        Advancement lvl55 = registerAdvancement(lvl50, ModItems.VINTEUM.get(), "level55", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(55))
                .register(consumer, ArsMagicaAPI.MODID + ":level55");
        Advancement lvl60 = registerAdvancement(lvl55, ModItems.VINTEUM.get(), "level60", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(60))
                .register(consumer, ArsMagicaAPI.MODID + ":level60");
        Advancement lvl65 = registerAdvancement(lvl60, ModItems.VINTEUM.get(), "level65", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(65))
                .register(consumer, ArsMagicaAPI.MODID + ":level65");
        Advancement lvl70 = registerAdvancement(lvl65, ModItems.VINTEUM.get(), "level70", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(70))
                .register(consumer, ArsMagicaAPI.MODID + ":level70");
        Advancement lvl75 = registerAdvancement(lvl70, ModItems.VINTEUM.get(), "level75", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(75))
                .register(consumer, ArsMagicaAPI.MODID + ":level75");
        Advancement lvl80 = registerAdvancement(lvl75, ModItems.VINTEUM.get(), "level80", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(80))
                .register(consumer, ArsMagicaAPI.MODID + ":level80");
        Advancement lvl85 = registerAdvancement(lvl80, ModItems.VINTEUM.get(), "level85", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(85))
                .register(consumer, ArsMagicaAPI.MODID + ":level85");
        Advancement lvl90 = registerAdvancement(lvl85, ModItems.VINTEUM.get(), "level90", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(90))
                .register(consumer, ArsMagicaAPI.MODID + ":level90");
        Advancement lvl95 = registerAdvancement(lvl90, ModItems.VINTEUM.get(), "level95", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(95))
                .register(consumer, ArsMagicaAPI.MODID + ":level95");
        Advancement lvl100 = registerAdvancement(lvl95, ModItems.VINTEUM.get(), "level100", FrameType.TASK, false, false, true)
                .withCriterion("level", MagicLevelTrigger.Instance.forLevel(100))
                .register(consumer, ArsMagicaAPI.MODID + ":level100");
    }
}
