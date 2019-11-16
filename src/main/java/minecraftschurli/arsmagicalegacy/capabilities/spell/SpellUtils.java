package minecraftschurli.arsmagicalegacy.capabilities.spell;

import minecraftschurli.arsmagicalegacy.capabilities.burnout.CapabilityBurnout;
import minecraftschurli.arsmagicalegacy.capabilities.burnout.IBurnoutStorage;
import minecraftschurli.arsmagicalegacy.capabilities.mana.CapabilityMana;
import minecraftschurli.arsmagicalegacy.capabilities.mana.IManaStorage;
import minecraftschurli.arsmagicalegacy.network.NetworkHandler;
import minecraftschurli.arsmagicalegacy.network.SyncBurnout;
import minecraftschurli.arsmagicalegacy.network.SyncMana;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;

/**
 * @author Minecraftschurli
 * @version 2019-11-14
 */
public final class SpellUtils {

    public static boolean use(PlayerEntity player, int manaCost, int burnoutCost) {
        if (player instanceof ServerPlayerEntity){
            LazyOptional<IManaStorage> manaStorage = player.getCapability(CapabilityMana.MANA);
            LazyOptional<IBurnoutStorage> burnoutStorage = player.getCapability(CapabilityBurnout.BURNOUT);
            if (manaStorage.map(IManaStorage::getMana).orElse(0.0f) >= manaCost && burnoutStorage.map(iBurnoutStorage -> iBurnoutStorage.getMaxBurnout() - iBurnoutStorage.getBurnout()).orElse(0.0f) >= burnoutCost) {
                manaStorage.ifPresent(iManaStorage -> iManaStorage.decrease(manaCost));
                burnoutStorage.ifPresent(iBurnoutStorage -> iBurnoutStorage.increase(burnoutCost));
                syncMana((ServerPlayerEntity)player);
                syncBurnout((ServerPlayerEntity) player);
                return true;
            }
        }
        return false;
    }

    public static void syncMana(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncMana(
                    iManaStorage.getMana(),
                    iManaStorage.getMaxMana()
                )
        ));
    }

    public static void syncBurnout(ServerPlayerEntity player) {
        Objects.requireNonNull(player);
        player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player),
                new SyncBurnout(
                        iBurnoutStorage.getBurnout(),
                        iBurnoutStorage.getMaxBurnout()
                )
        ));
    }

    public static void regenMana(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityMana.MANA).ifPresent(iManaStorage -> {
                iManaStorage.increase(amount);
            });
            syncMana((ServerPlayerEntity) player);
        }
    }

    public static void regenBurnout(PlayerEntity player, float amount) {
        if (player instanceof ServerPlayerEntity) {
            player.getCapability(CapabilityBurnout.BURNOUT).ifPresent(iBurnoutStorage -> {
                iBurnoutStorage.decrease(amount);
            });
            syncBurnout((ServerPlayerEntity) player);
        }
    }

    public static BlockPos rayTrace(World world, PlayerEntity player, double rayTraceRange) {
        Vec3d look = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d end = new Vec3d(player.posX + look.x * rayTraceRange, player.posY + player.getEyeHeight() + look.y * rayTraceRange, player.posZ + look.z * rayTraceRange);
        RayTraceContext ctx = new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player);
        BlockRayTraceResult result = world.rayTraceBlocks(ctx);
        return result.getPos().offset(result.getFace());
    }

    public static LazyOptional<ISpell> getSpell(ItemStack stack) {
        //TODO Make Spell system
        return LazyOptional.of(TestSpell::new).cast();
    }
}
