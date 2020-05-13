package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import java.util.Comparator;
import java.util.List;
import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * @author Minecraftschurli
 * @version 2020-05-02
 */
public class BlackAuremTileEntity extends EtheriumGeneratorTileEntity {
    private int timer;

    public BlackAuremTileEntity() {
        super(ModTileEntities.BLACK_AUREM.get(), EtheriumType.DARK.get());
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) return;
        int tier = getTier();
        if (timer <= 0) {
            int r = 5 + tier;
            BlockPos pos = getPos();
            List<LivingEntity> entitiesWithinAABB = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).expand(r, r, r));
            entitiesWithinAABB.sort(Comparator.comparingDouble(o -> o.getDistanceSq(pos.getX(), pos.getY(), pos.getZ())));
            int c = Math.max(1, tier / 2);
            for (LivingEntity livingEntity : entitiesWithinAABB)
                if (livingEntity.isAlive() && livingEntity.attackable() && !livingEntity.isEntityUndead() && livingEntity.attackEntityFrom(DamageSource.MAGIC, 1)) {
                    addEtherium(1);
                    if (c <= 0) break;
                    c--;
                }
            timer = 5 / (tier + 1);
        } else timer--;
    }
}
