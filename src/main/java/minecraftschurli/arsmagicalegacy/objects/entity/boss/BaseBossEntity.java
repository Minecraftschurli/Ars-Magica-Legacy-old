package minecraftschurli.arsmagicalegacy.objects.entity.boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
public class BaseBossEntity extends MobEntity implements IMob {
    protected BaseBossEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }
}
