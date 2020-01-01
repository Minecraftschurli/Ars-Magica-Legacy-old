package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.spell.crafting.IngredientTypes;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.*;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.*;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class ModTileEntities implements IInit {
    public static final RegistryObject<TileEntityType<InscriptionTableTileEntity>> INSCRIPTION_TABLE = TILE_ENTITIES.register("inscription_table", () -> TileEntityType.Builder.create(InscriptionTableTileEntity::new, ModBlocks.INSCRIPTION_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<CraftingAltarTileEntity>> ALTAR_CORE = TILE_ENTITIES.register("altar_core", () -> TileEntityType.Builder.create(CraftingAltarTileEntity::new, ModBlocks.ALTAR_CORE.get()).build(null));
    public static final RegistryObject<TileEntityType<CraftingAltarViewTileEntity>> ALTAR_VIEW = TILE_ENTITIES.register("altar_view", () -> TileEntityType.Builder.create(CraftingAltarViewTileEntity::new, ModBlocks.ALTAR_VIEW.get()).build(null));

    public static void register() {
    }
}
