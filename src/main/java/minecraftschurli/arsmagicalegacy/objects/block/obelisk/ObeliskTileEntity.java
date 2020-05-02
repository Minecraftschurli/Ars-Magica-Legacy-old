package minecraftschurli.arsmagicalegacy.objects.block.obelisk;

import minecraftschurli.arsmagicalegacy.api.EtheriumGeneratorManager;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.capabilities.EtheriumStorage;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class ObeliskTileEntity extends EtheriumGeneratorTileEntity implements INamedContainerProvider {
    private final LazyOptional<ObeliskInventory> obeliskInventory = LazyOptional.of(ObeliskInventory::new);
    private int time;
    private int maxTime;

    public ObeliskTileEntity() {
        super(ModTileEntities.OBELISK.get(), () -> new EtheriumStorage(5000, EtheriumType.NEUTRAL.get()));
    }

    @Override
    public void tick() {
        if (time <= 0) {
            obeliskInventory.ifPresent(inv -> {
                if (EtheriumGeneratorManager.isEtheriumGeneratorFuel(inv.getStackInSlot(0))) {
                    ItemStack stack = inv.decrStackSize(0, 1);
                    if (stack.isEmpty()) return;
                    int amount = Math.round(EtheriumGeneratorManager.getEtheriumValue(stack) * getMultiplier());
                    if (amount <= 0) return;
                    this.time = this.maxTime = amount;
                }
            });
        }
        if (time <= 0) return;
        if (addEtherium(1)) time--;
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        compound.putInt("time", time);
        obeliskInventory.ifPresent(inv -> compound.put("inventory", inv.serializeNBT()));
        return compound;
    }


    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        time = compound.getInt("time");
        obeliskInventory.ifPresent(inv -> inv.deserializeNBT(compound.getCompound("inventory")));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return obeliskInventory.cast();
        return super.getCapability(cap, side);
    }

    public IInventory getInv() {
        return this.obeliskInventory.orElse(null);
    }

    public int getCookProgressScaled(int i) {
        if (maxTime == 0) return 0;
        return time * i / maxTime;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ObeliskContainer(p_createMenu_1_, p_createMenu_2_, this);
    }
}
