package minecraftschurli.arsmagicalegacy.capabilities.spell;

import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/**
 * @author Minecraftschurli
 * @version 2019-11-12
 */
public class TestSpell implements ISpell {

    @Override
    public boolean execute(World world, ItemStack stack, PlayerEntity player) {
        double rayTraceRange = 15;
        SpellUtils.use(player, getManaCost(), getBurnoutCost());
        BlockPos blockpos = SpellUtils.rayTrace(world, player, rayTraceRange);
        LightningBoltEntity lightningboltentity = new LightningBoltEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, false);
        lightningboltentity.setCaster(player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null);
        ((ServerWorld)world).addLightningBolt(lightningboltentity);
        return true;
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public int getManaCost() {
        return 1;
    }

    @Override
    public int getBurnoutCost() {
        return 0;
    }
}
