package minecraftschurli.arsmagicalegacy.objects.block.blackaurem;

import minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import minecraftschurli.arsmagicalegacy.api.etherium.generator.EtheriumGeneratorTileEntity;
import minecraftschurli.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import minecraftschurli.arsmagicalegacy.init.ModTileEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;
import java.util.List;

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
            int r = getEntityRadius(tier);
            BlockPos pos = getPos();
            List<LivingEntity> entitiesWithinAABB = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos).expand(r, r, r));
            entitiesWithinAABB.sort(Comparator.comparingDouble(o -> o.getDistanceSq(pos.getX(), pos.getY(), pos.getZ())));
            int c = getEntityCount(tier);
            for (LivingEntity livingEntity : entitiesWithinAABB) {
                if (livingEntity.isAlive()) {
                    if (livingEntity.attackable()) {
                        if (!livingEntity.isEntityUndead()) {
                            if (livingEntity.attackEntityFrom(DamageSource.MAGIC, 1)) {
                                addEtherium(1);
                                if (c <= 0) break;
                                c--;
                            }
                        }
                    }
                }
            }
            timer = getTimer(tier);
        } else {
            timer--;
        }
    }

    private int getEntityCount(int tier) {
        return Math.max(1, tier/2);
    }

    private int getTimer(int tier) {
        return 5 / (tier+1);
    }

    private int getTier() {
        int tier = 0;
        if (PatchouliCompat.BLACK_AUREM_CHALK.get().validate(world, pos) != null) {
            if (PatchouliCompat.BLACK_AUREM_PILLAR_1.get().validate(world, pos) != null) {
                tier = 2;
            } else if (PatchouliCompat.BLACK_AUREM_PILLAR_2.get().validate(world, pos) != null) {
                tier = 3;
            } else if (PatchouliCompat.BLACK_AUREM_PILLAR_3.get().validate(world, pos) != null) {
                tier = 4;
            } else {
                tier = 1;
            }
        }
        return tier;
    }

    private int getEntityRadius(int tier) {
        return 5+tier;
    }
}
