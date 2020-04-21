package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.obelisk.ObeliskTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public final class ModTileEntities implements IInit {
    public static final RegistryObject<TileEntityType<CraftingAltarTileEntity>> ALTAR_CORE = TILE_ENTITIES.register("altar_core", () -> TileEntityType.Builder.create(CraftingAltarTileEntity::new, ModBlocks.ALTAR_CORE.get()).build(null));
    public static final RegistryObject<TileEntityType<CraftingAltarViewTileEntity>> ALTAR_VIEW = TILE_ENTITIES.register("altar_view", () -> TileEntityType.Builder.create(CraftingAltarViewTileEntity::new, ModBlocks.ALTAR_VIEW.get()).build(null));
    public static final RegistryObject<TileEntityType<InscriptionTableTileEntity>> INSCRIPTION_TABLE = TILE_ENTITIES.register("inscription_table", () -> TileEntityType.Builder.create(InscriptionTableTileEntity::new, ModBlocks.INSCRIPTION_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<ObeliskTileEntity>> OBELISK = TILE_ENTITIES.register("obelisk", () -> TileEntityType.Builder.create(ObeliskTileEntity::new, ModBlocks.OBELISK.get()).build(null));

    public static void register() {
    }
}
