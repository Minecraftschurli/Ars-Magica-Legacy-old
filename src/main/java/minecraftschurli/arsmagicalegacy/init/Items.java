package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.item.spellbook.SpellBookItem;
import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Georg Burkl
 * @version 2019-11-07
 */
public class Items implements Registries {
    public static final RegistryObject<Item> SPELL_ITEM = ITEMS.register("spell_item", SpellItem::new);
    public static final RegistryObject<Item> SPELL_BOOK_ITEM = ITEMS.register("spell_book_item", SpellBookItem::new);

    public static void register() {}
}
