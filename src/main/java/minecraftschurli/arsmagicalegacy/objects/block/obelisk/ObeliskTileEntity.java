package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.capabilities.EtheriumStorage;
import minecraftschurli.arsmagicalegacy.init.ModItems;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public class ObeliskTileEntity extends TileEntity implements ITickableTileEntity {
    private final LazyOptional<EtheriumStorage> etheriumStorage = LazyOptional.of(EtheriumStorage::new);
    private final LazyOptional<ObeliskInventory> obeliskInventory = LazyOptional.of(ObeliskInventory::new);
    private int time;
    private int maxTime;

    public ObeliskTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ObeliskTileEntity() {
        this(ModTileEntities.OBELISK.get());
    }

    @Override
    public void tick() {
        if (time <= 0) {
            obeliskInventory.ifPresent(inv -> {
                if (isObeliskFuel(inv.getStackInSlot(0))) {
                    ItemStack stack = inv.decrStackSize(0, 1);
                    if (stack.isEmpty()) return;
                    int amount = getEtheriumValue(stack);
                    if (amount <= 0) return;
                    this.time = this.maxTime = amount;
                }
            });
        }
        if (time <= 0) return;
        etheriumStorage.ifPresent(etherium -> etherium.add(1, false));
        time--;
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putInt("time", time);
        obeliskInventory.ifPresent(inv -> compound.put("inventory", inv.serializeNBT()));
        etheriumStorage.ifPresent(etherium -> compound.put("etherium", etherium.serializeNBT()));
        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        time = compound.getInt("time");
        obeliskInventory.ifPresent(inv -> inv.deserializeNBT(compound.getCompound("inventory")));
        etheriumStorage.ifPresent(etherium -> etherium.deserializeNBT(compound.getCompound("etherium")));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), 300, write(new CompoundNBT()));
    }

    public static int getEtheriumValue(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return 1;
    }

    public static boolean isObeliskFuel(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.getItem() == ModItems.VINTEUM.get();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityHelper.getEtheriumCapability())
            return etheriumStorage.cast();
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return obeliskInventory.cast();
        return LazyOptional.empty();
    }

    public IInventory getInv() {
        return this.obeliskInventory.orElse(null);
    }

    public int getCookProgressScaled(int i) {
        return time * i / maxTime;
    }
}
