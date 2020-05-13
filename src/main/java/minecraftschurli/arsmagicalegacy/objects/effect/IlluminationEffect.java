package minecraftschurli.arsmagicalegacy.objects.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

/**
 * @author Minecraftschurli
 * @version 2019-12-07
 */
public class IlluminationEffect extends AMEffect {
    public IlluminationEffect() {
        super(EffectType.BENEFICIAL, 0xffffbe);
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
//        if (!livingEntity.world.isRemote && livingEntity.ticksExisted % 10 == 0) {
//            if (livingEntity.world.isAirBlock(livingEntity.getPosition()) && livingEntity.world.getLight(livingEntity.getPosition()) < 7){
//                livingEntity.world.setBlockState(livingEntity.getPosition(), ModBlocks.LIGHT.get().getDefaultState());
//            }
//        }
    }
}
