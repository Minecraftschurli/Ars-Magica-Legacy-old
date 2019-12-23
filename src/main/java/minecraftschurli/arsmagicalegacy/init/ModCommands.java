package minecraftschurli.arsmagicalegacy.init;

import com.mojang.brigadier.*;
import com.mojang.brigadier.tree.*;
import minecraftschurli.arsmagicalegacy.objects.command.*;
import net.minecraft.command.*;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class ModCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> command = dispatcher.register(
                Commands.literal("arsmagica")
                        .then(CommandResearch.register(dispatcher))
        );
        dispatcher.register(Commands.literal("am").redirect(command));
    }
}
