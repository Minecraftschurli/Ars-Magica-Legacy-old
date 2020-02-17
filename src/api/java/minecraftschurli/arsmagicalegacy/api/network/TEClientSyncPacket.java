package minecraftschurli.arsmagicalegacy.api.network;

import net.minecraft.client.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.network.*;

import java.util.function.*;

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

    public TEClientSyncPacket(PacketBuffer buf) {
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
        //noinspection ConstantConditions
        ctx.get().enqueueWork(() -> Minecraft.getInstance().world.getTileEntity(pos).read(data));
        ctx.get().setPacketHandled(true);
    }
}
