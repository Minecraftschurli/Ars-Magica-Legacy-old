package minecraftschurli.arsmagicalegacy.objects.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class CommandResearch implements Command<CommandSource> {

    private static final CommandResearch CMD = new CommandResearch();
    private static final String TARGET = "target";

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("knowledge")
                .then(Commands.argument(TARGET, EntityArgument.players()))
                .requires(cs -> cs.hasPermissionLevel(0))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgument.getPlayer(context, TARGET);
        context.getSource().sendFeedback(
                MagicHelper.getResearchCapability(target)
                        .getLearned()
                        .stream()
                        .map(Skill::getName)
                        .map(iTextComponent -> iTextComponent.appendSibling(new StringTextComponent("\n")))
                        .reduce(ITextComponent::appendSibling)
                        .orElse(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".brain.empty"))
                , false);
        return 0;
    }
}
