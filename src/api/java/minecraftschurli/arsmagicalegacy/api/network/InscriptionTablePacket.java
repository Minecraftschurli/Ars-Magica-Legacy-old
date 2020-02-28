package minecraftschurli.arsmagicalegacy.api.network;

import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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
            ArsMagicaAPI.LOGGER.debug("handle update {}", te);
            if (te != null) {
                te.read(data);
            }
        });
    }
}
