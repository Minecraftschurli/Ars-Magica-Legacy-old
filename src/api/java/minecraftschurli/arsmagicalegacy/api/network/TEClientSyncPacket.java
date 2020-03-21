package minecraftschurli.arsmagicalegacy.api.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2019-12-27
 */
public class TEClientSyncPacket implements IPacket {
    private BlockPos pos;
    private CompoundNBT data;

    public TEClientSyncPacket(TileEntity te) {
        this.pos = te.getPos();
        this.data = te.write(new CompoundNBT());
    }

    public TEClientSyncPacket() {}

    @Override
    public void serialize(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeCompoundTag(data);
    }

    @Override
    public void deserialize(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.data = buf.readCompoundTag();
    }

    @Override
    public boolean handle(NetworkEvent.Context ctx) {
        //noinspection ConstantConditions
        ctx.enqueueWork(() -> Minecraft.getInstance().world.getTileEntity(pos).read(data));
        return true;
    }
}
