package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.capabilities.EtheriumStorage;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class BlackAuremTileEntity extends EtheriumGeneratorTileEntity {
    public BlackAuremTileEntity() {
        super(ModTileEntities.BLACK_AUREM.get(), () -> new EtheriumStorage(5000, EtheriumType.DARK.get()));
    }

    @Override
    public void tick() {
        if (world == null) return;
        int r = getEntityRadius();
        boolean success = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).expand(r, r, r))
                .stream()
                .filter(LivingEntity::isAlive)
                .filter(LivingEntity::attackable)
                .filter(entity -> !entity.isEntityUndead())
                .findFirst()
                .map(entity -> entity.attackEntityFrom(DamageSource.MAGIC, 1))
                .orElse(false);
        if (success) {
            addEtherium(1);
        }
    }

    private int getEntityRadius() {
        return 5;
    }
}
