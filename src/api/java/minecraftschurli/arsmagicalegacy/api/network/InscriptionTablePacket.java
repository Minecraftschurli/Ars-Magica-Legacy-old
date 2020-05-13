package minecraftschurli.arsmagicalegacy.api.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-12-12
 */
public class InscriptionTablePacket implements IPacket {
    protected BlockPos pos;
    private CompoundNBT data;

    public InscriptionTablePacket(BlockPos pos, CompoundNBT data) {
        this.pos = pos;
        this.data = data;
    }

    public InscriptionTablePacket() {}

    public void deserialize(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.data = buf.readCompoundTag();
    }

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeCompoundTag(data);
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            TileEntity te = ctx.getSender().world.getTileEntity(pos);
            if (te != null) te.read(data);
        });
        return true;
    }
}
