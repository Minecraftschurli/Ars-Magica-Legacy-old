package minecraftschurli.arsmagicalegacy.objects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2019-11-17
 */
public class SpellProjectileEntity extends Entity {
    public SpellProjectileEntity(World worldIn) {
        super(null, worldIn);
    }

    @Override
    protected void registerData() {

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param compound
     */
    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return null;
    }
}
