package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

/**
 * @author Minecraftschurli
 * @version 2019-12-29
 */
public class CraftingAltarViewTileEntity extends TileEntity {
    public int itemRotation;
    public Item itemCache;
    BlockPos altar;

    public CraftingAltarViewTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public CraftingAltarViewTileEntity() {
        this(ModTileEntities.ALTAR_VIEW.get());
    }

    void setAltarPos(BlockPos pos) {
        altar = pos;
    }

    CraftingAltarTileEntity getAltar() {
        if (world == null || altar == null) return null;
        TileEntity te = world.getTileEntity(altar);
        if (te instanceof CraftingAltarTileEntity)
            return (CraftingAltarTileEntity) te;
        return null;
    }

    @Override
    public void remove() {
        ArsMagicaLegacy.LOGGER.debug("removed");
        super.remove();
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putBoolean("altar", altar != null);
        if (altar != null) {
            compound.putInt("X", altar.getX());
            compound.putInt("Y", altar.getY());
            compound.putInt("Z", altar.getZ());
        }
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.getBoolean("altar"))
            setAltarPos(new BlockPos(compound.getInt("X"), compound.getInt("Y"), compound.getInt("Z")));
    }
}
