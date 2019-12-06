package minecraftschurli.arsmagicalegacy.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaLegacyAPI;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import minecraftschurli.arsmagicalegacy.util.MagicHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class CommandResearch {
    private static final SuggestionProvider<CommandSource> SUGGEST_RESEARCH = (context, builder) -> ISuggestionProvider.func_212476_a(ArsMagicaLegacyAPI.SKILL_REGISTRY.getValues().stream().map(Skill::getRegistryName), builder);
    private static final String TARGET = "target";

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("research")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.literal("learn")
                        .then(Commands.argument(TARGET, EntityArgument.player())
                                .then(Commands.argument("id", ResourceLocationArgument.resourceLocation())
                                        .suggests(SUGGEST_RESEARCH)
                                        .executes(context -> CommandResearch.learn(context, ArsMagicaLegacyAPI.SKILL_REGISTRY.getValue(ResourceLocationArgument.getResourceLocation(context, "id"))))
                                ).then(Commands.literal("*").executes(CommandResearch::learnAll))
                        )
                ).then(Commands.literal("forget")
                        .then(Commands.argument(TARGET, EntityArgument.player())
                                .then(Commands.argument("id", ResourceLocationArgument.resourceLocation())
                                        .suggests(SUGGEST_RESEARCH)
                                        .executes(CommandResearch::forget)
                                ).then(Commands.literal("*").executes(CommandResearch::forgetAll))
                        )
                );
    }

    private static int forget(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Skill skill = ArsMagicaLegacyAPI.SKILL_REGISTRY.getValue(ResourceLocationArgument.getResourceLocation(context, "id"));
        if (skill == null) {
            context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID+".command.skillnotfound", skill.getName()), false);
            return 1;
        }
        MagicHelper.getResearchCapability(EntityArgument.getPlayer(context, TARGET)).forget(skill);
        context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID+".command.forgot", skill.getName()), false);
        return 0;
    }

    private static int forgetAll(CommandContext<CommandSource> context) throws CommandSyntaxException {
        MagicHelper.getResearchCapability(EntityArgument.getPlayer(context, TARGET)).forgetAll();
        context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID+".command.forgotall"), false);
        return 0;
    }

    private static int learn(CommandContext<CommandSource> context, Skill skill) throws CommandSyntaxException {
        if (skill == null) {
            context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID+".command.skillnotfound", ResourceLocationArgument.getResourceLocation(context, "id")), false);
            return 1;
        }
        MagicHelper.getResearchCapability(EntityArgument.getPlayer(context, TARGET)).learn(skill);
        context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID+".command.learned", skill.getName()), false);
        return 0;
    }

    private static int learnAll(CommandContext<CommandSource> context) throws CommandSyntaxException {
        for (Skill skill : ArsMagicaLegacyAPI.SKILL_REGISTRY) {
            CommandResearch.learn(context, skill);
        }
        return 0;
    }
}