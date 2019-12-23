package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.objects.block.inscriptiontable.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-12
 */
public class InscriptionTablePacket implements IPacket {
    protected final BlockPos pos;
    private final CompoundNBT data;

    public InscriptionTablePacket(BlockPos pos, CompoundNBT data) {
        this.pos = pos;
        this.data = data;
    }

    public InscriptionTablePacket(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.data = buf.readCompoundTag();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeCompoundTag(data);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntity te = ctx.get().getSender().world.getTileEntity(pos);
            ArsMagicaLegacy.LOGGER.debug("handle update {}", te);
            if (te instanceof InscriptionTableTileEntity)
                te.read(data);
        });
    }
}
