package minecraftschurli.arsmagicalegacy.init;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import minecraftschurli.arsmagicalegacy.objects.command.CommandResearch;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public final class ModCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> command = dispatcher.register(Commands.literal("arsmagica").then(CommandResearch.register(dispatcher)));
        dispatcher.register(Commands.literal("am").redirect(command));
    }
}
