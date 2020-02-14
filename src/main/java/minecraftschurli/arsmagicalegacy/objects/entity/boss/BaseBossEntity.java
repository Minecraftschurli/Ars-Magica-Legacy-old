package minecraftschurli.arsmagicalegacy.objects.entity.boss;

import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.world.*;

/**
 * @author Minecraftschurli
 * @version 2020-02-10
 */
@SuppressWarnings("EntityConstructor")
public class BaseBossEntity extends MobEntity implements IMob {
    protected BaseBossEntity(EntityType<? extends MobEntity> type, World world) {
        super(type, world);
    }
}
