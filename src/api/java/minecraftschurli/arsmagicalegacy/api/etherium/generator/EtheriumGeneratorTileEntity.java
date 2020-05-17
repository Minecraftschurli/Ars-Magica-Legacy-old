package minecraftschurli.arsmagicalegacy.api.etherium.generator;

import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.capability.EtheriumStorage;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.IEtheriumStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Minecraftschurli
 * @version 2020-04-21
 */
public abstract class EtheriumGeneratorTileEntity extends TileEntity implements ITickableTileEntity {
    private final LazyOptional<IEtheriumStorage> etheriumStorage;

    public EtheriumGeneratorTileEntity(TileEntityType<? extends EtheriumGeneratorTileEntity> tileType, EtheriumType type) {
        super(tileType);
        etheriumStorage = LazyOptional.of(() -> new EtheriumStorage(5000, type));
    }

    @Override
    public abstract void tick();

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        etheriumStorage.ifPresent(etherium -> compound.put("etherium", etherium.serializeNBT()));
        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        etheriumStorage.ifPresent(etherium -> etherium.deserializeNBT(compound.getCompound("etherium")));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), 300, write(new CompoundNBT()));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityHelper.getEtheriumCapability()) return etheriumStorage.cast();
        return super.getCapability(cap, side);
    }

    protected boolean addEtherium(final int amount) {
        return etheriumStorage.map(iEtheriumStorage -> iEtheriumStorage.add(amount, false)).orElse(false);
    }

    protected int getTier() {
        BlockState state = getBlockState();
        Block block = state.getBlock();
        if (world != null && block instanceof EtheriumGeneratorBlock<?>) return ((EtheriumGeneratorBlock<?>)block).getTier(state, world, pos);
        return 0;
    }
}
