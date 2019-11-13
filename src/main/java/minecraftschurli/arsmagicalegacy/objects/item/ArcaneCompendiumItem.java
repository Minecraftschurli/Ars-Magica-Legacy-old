package minecraftschurli.arsmagicalegacy.objects.item;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.item.Item;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class ArcaneCompendiumItem extends Item {
    public ArcaneCompendiumItem() {
        super(new Properties().group(ArsMagicaLegacy.ITEM_GROUP).maxStackSize(1));
    }
}
