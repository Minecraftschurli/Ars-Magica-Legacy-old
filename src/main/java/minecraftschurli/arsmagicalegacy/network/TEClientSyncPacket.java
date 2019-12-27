package minecraftschurli.arsmagicalegacy.network;

import minecraftschurli.arsmagicalegacy.api.spell.crafting.SpellIngredientList;
import minecraftschurli.arsmagicalegacy.objects.block.craftingaltar.CraftingAltarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

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
        ctx.get().enqueueWork(() -> Minecraft.getInstance().world.getTileEntity(pos).read(data));
        ctx.get().setPacketHandled(true);
    }
}
