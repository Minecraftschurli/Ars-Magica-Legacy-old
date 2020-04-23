package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarViewTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.etheriumgenerator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.InscriptionTableTileEntity;
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
    public static final RegistryObject<TileEntityType<EtheriumGeneratorTileEntity>> OBELISK = TILE_ENTITIES.register("obelisk", () -> TileEntityType.Builder.create(() -> new EtheriumGeneratorTileEntity(ModTileEntities.OBELISK.get(), EtheriumType.NEUTRAL.get()), ModBlocks.OBELISK.get()).build(null));
    public static final RegistryObject<TileEntityType<EtheriumGeneratorTileEntity>> CELESTIAL_PRISM = TILE_ENTITIES.register("celestial_prism", () -> TileEntityType.Builder.create(() -> new EtheriumGeneratorTileEntity(ModTileEntities.CELESTIAL_PRISM.get(), EtheriumType.LIGHT.get()), ModBlocks.CELESTIAL_PRISM.get()).build(null));
    public static final RegistryObject<TileEntityType<EtheriumGeneratorTileEntity>> BLACK_AUREM = TILE_ENTITIES.register("black_aurem", () -> TileEntityType.Builder.create(() -> new EtheriumGeneratorTileEntity(ModTileEntities.BLACK_AUREM.get(), EtheriumType.DARK.get()), ModBlocks.BLACK_AUREM.get()).build(null));

    public static void register() {
    }
}
