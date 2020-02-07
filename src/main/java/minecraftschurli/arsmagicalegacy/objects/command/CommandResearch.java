package minecraftschurli.arsmagicalegacy.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class CommandResearch {
    private static final SuggestionProvider<CommandSource> SUGGEST_RESEARCH = (context, builder) -> ISuggestionProvider.func_212476_a(ArsMagicaAPI.getSkillRegistry().getValues().stream().map(Skill::getRegistryName), builder);
    private static final String TARGET = "target";

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("research")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.literal("learn")
                        .then(Commands.argument(TARGET, EntityArgument.player())
                                .then(Commands.argument("id", ResourceLocationArgument.resourceLocation())
                                        .suggests(SUGGEST_RESEARCH)
                                        .executes(context -> CommandResearch.learn(context, ArsMagicaAPI.getSkillRegistry().getValue(ResourceLocationArgument.getResourceLocation(context, "id"))))
                                ).then(Commands.literal("*").executes(CommandResearch::learnAll))
                        )
                ).then(Commands.literal("forget")
                        .then(Commands.argument(TARGET, EntityArgument.player())
                                .then(Commands.argument("id", ResourceLocationArgument.resourceLocation())
                                        .suggests(SUGGEST_RESEARCH)
                                        .executes(CommandResearch::forget)
                                ).then(Commands.literal("*").executes(CommandResearch::forgetAll))
                        )
                ).then(Commands.literal("list")
                        .then(Commands.argument(TARGET, EntityArgument.player())
                                .executes(CommandResearch::list)
                        )
                );
    }

    private static int list(CommandContext<CommandSource> context) throws CommandSyntaxException {
        context.getSource().sendFeedback(new StringTextComponent(CapabilityHelper.getLearnedSkills(EntityArgument.getPlayer(context, TARGET)).stream().map(Skill::getName).map(ITextComponent::getFormattedText).collect(Collectors.joining("\n"))), false);
        return 0;
    }

    private static int forget(CommandContext<CommandSource> context) throws CommandSyntaxException {
        Skill skill = ArsMagicaAPI.getSkillRegistry().getValue(ResourceLocationArgument.getResourceLocation(context, "id"));
        if (skill == null) {
            context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".command.skillnotfound", skill.getName()), false);
            return 1;
        }
        CapabilityHelper.forget(EntityArgument.getPlayer(context, TARGET), skill);
        context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".command.forgot", skill.getName()), false);
        return 0;
    }

    private static int forgetAll(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CapabilityHelper.forgetAll(EntityArgument.getPlayer(context, TARGET));
        context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".command.forgotall"), false);
        return 0;
    }

    private static int learn(CommandContext<CommandSource> context, Skill skill) throws CommandSyntaxException {
        if (skill == null) {
            context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".command.skillnotfound", ResourceLocationArgument.getResourceLocation(context, "id")), false);
            return 1;
        }
        if (Objects.equals(skill.getRegistryName(), ArsMagicaAPI.MISSING_SHAPE))
            return 0;
        CapabilityHelper.learn(EntityArgument.getPlayer(context, TARGET), skill);
        context.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaLegacy.MODID + ".command.learned", skill.getName()), false);
        return 0;
    }

    private static int learnAll(CommandContext<CommandSource> context) throws CommandSyntaxException {
        for (Skill skill : ArsMagicaAPI.getSkillRegistry()) {
            CommandResearch.learn(context, skill);
        }
        return 0;
    }
}
