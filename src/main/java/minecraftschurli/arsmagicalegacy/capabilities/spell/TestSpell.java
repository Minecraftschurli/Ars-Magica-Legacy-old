package minecraftschurli.arsmagicalegacy.capabilities.spell;

import minecraftschurli.arsmagicalegacy.objects.item.SpellItem;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
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
        Vec3d look = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d end = new Vec3d(player.posX + look.x * rayTraceRange, player.posY + player.getEyeHeight() + look.y * rayTraceRange, player.posZ + look.z * rayTraceRange);
        RayTraceContext ctx = new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player);
        BlockRayTraceResult result = world.rayTraceBlocks(ctx);
        BlockPos blockpos = result.getPos().offset(result.getFace());
        LightningBoltEntity lightningboltentity = new LightningBoltEntity(world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, false);
        lightningboltentity.setCaster(player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null);
        ((ServerWorld)world).addLightningBolt(lightningboltentity);
        return true;
    }

    @Override
    public int getCooldown() {
        return 10;
    }
}
