package minecraftschurli.arsmagicalegacy.api.capability;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.IEtheriumStorage;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

/**
 * @author Minecraftschurli
 * @version 2020-04-16
 */
public class EtheriumStorage implements IEtheriumStorage {
    private final int maxStorage;
    int amount;
    private EtheriumType type;

    public EtheriumStorage (){
        this(5000, EtheriumType.NEUTRAL.get());
    }

    public EtheriumStorage(int maxStorage, EtheriumType type) {
        this.maxStorage = maxStorage;
        this.amount = 0;
        this.type = type;
    }

    @Override
    public int getStoredAmount() {
        return amount;
    }

    @Override
    public int getMaxStoredAmount() {
        return maxStorage;
    }

    @Override
    public synchronized boolean consume(int amount, boolean simulate) {
        boolean ret = amount > getStoredAmount();
        if (ret && !simulate) {
            this.amount -= amount;
        }
        return ret;
    }

    @Override
    public synchronized boolean add(int amount, boolean simulate) {
        boolean ret = getMaxStoredAmount() >= getStoredAmount() + amount;
        if (ret && !simulate) {
            this.amount += amount;
        }
        return ret;
    }

    @Override
    public EtheriumType getType() {
        return type;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("amount", amount);
        nbt.putString("type", type.getRegistryName().toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        amount = nbt.getInt("amount");
        type = RegistryHandler.getEtheriumRegistry().getValue(ResourceLocation.tryCreate(nbt.getString("type")));
    }
}
