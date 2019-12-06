package minecraftschurli.arsmagicalegacy.objects.block;

import net.minecraft.block.*;

public class Rail extends RailBlock {
    public Rail(Block.Properties properties) {
        super(properties.doesNotBlockMovement().hardnessAndResistance(0.7f).sound(SoundType.METAL));
    }
}
