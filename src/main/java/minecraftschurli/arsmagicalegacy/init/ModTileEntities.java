package minecraftschurli.arsmagicalegacy.init;

import minecraftschurli.arsmagicalegacy.objects.block.occulus.TileEntityOcculus;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Minecraftschurli
 * @version 2019-12-02
 */
public class ModTileEntities implements IInit {
    public static final RegistryObject<TileEntityType<TileEntityOcculus>> OCCULUS = TILE_ENTITIES.register("occulus", ()-> TileEntityType.Builder.create(TileEntityOcculus::new, ModBlocks.OCCULUS.get()).build(null));

}
