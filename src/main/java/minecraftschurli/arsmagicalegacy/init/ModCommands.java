package minecraftschurli.arsmagicalegacy.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collections;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.config.Config;
import minecraftschurli.arsmagicalegacy.objects.command.CommandResearch;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public final class ModCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> command = dispatcher.register(Commands.literal("arsmagica").then(CommandResearch.register(dispatcher)));
        LiteralCommandNode<CommandSource> reset = dispatcher.register(Commands.literal("arsmagica").then(Commands.literal("reset").requires(commandSource -> commandSource.hasPermissionLevel(2)).executes(context -> {
            PlayerEntity player = (PlayerEntity) context.getSource().getEntity();
            if (player == null) return 0;
            player.getCapability(CapabilityHelper.getMagicCapability()).ifPresent(iMagicStorage -> {
                iMagicStorage.setXp(0);
                iMagicStorage.setLevel(-1);
            });
            player.getCapability(CapabilityHelper.getManaCapability()).ifPresent(iManaStorage -> {
                iManaStorage.setMaxMana(0);
                iManaStorage.setMana(0);
            });
            player.getCapability(CapabilityHelper.getBurnoutCapability()).ifPresent(iBurnoutStorage -> {
                iBurnoutStorage.setMaxBurnout(Config.SERVER.DEFAULT_MAX_BURNOUT.get().floatValue());
                iBurnoutStorage.setBurnout(0);
            });
            player.getCapability(CapabilityHelper.getResearchCapability()).ifPresent(iResearchStorage -> {
                iResearchStorage.set(0, 2);
                iResearchStorage.set(1, 0);
                iResearchStorage.set(2, 0);
            });
            CapabilityHelper.addXP(player, 0);
            return 0;
        })));
        dispatcher.register(Commands.literal("am").forward(command, context -> Collections.emptyList(), false));
    }
}
