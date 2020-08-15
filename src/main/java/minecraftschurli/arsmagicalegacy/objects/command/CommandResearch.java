package minecraftschurli.arsmagicalegacy.objects.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Objects;
import java.util.stream.Collectors;
import minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import minecraftschurli.arsmagicalegacy.api.capability.CapabilityHelper;
import minecraftschurli.arsmagicalegacy.api.registry.RegistryHandler;
import minecraftschurli.arsmagicalegacy.api.registry.SpellRegistry;
import minecraftschurli.arsmagicalegacy.api.skill.Skill;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-12-04
 */
public class CommandResearch {
    private static final SuggestionProvider<CommandSource> SUGGEST_RESEARCH = (ctx, builder) -> ISuggestionProvider.func_212476_a(RegistryHandler.getSkillRegistry().getValues().stream().map(Skill::getRegistryName), builder);
    private static final String TARGET = "target";

    public static ArgumentBuilder<CommandSource, ?> register(CommandDispatcher<CommandSource> dispatcher) {
        return Commands.literal("research").requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.literal("learn").then(Commands.argument(TARGET, EntityArgument.player()).then(Commands.argument("id", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_RESEARCH).executes(context -> CommandResearch.learn(context, RegistryHandler.getSkillRegistry().getValue(ResourceLocationArgument.getResourceLocation(context, "id"))))).then(Commands.literal("*").executes(CommandResearch::learnAll))))
                .then(Commands.literal("forget").then(Commands.argument(TARGET, EntityArgument.player()).then(Commands.argument("id", ResourceLocationArgument.resourceLocation()).suggests(SUGGEST_RESEARCH).executes(CommandResearch::forget)).then(Commands.literal("*").executes(CommandResearch::forgetAll))))
                .then(Commands.literal("list").then(Commands.argument(TARGET, EntityArgument.player()).executes(CommandResearch::list)));
    }

    private static int list(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        ctx.getSource().sendFeedback(new StringTextComponent(CapabilityHelper.getLearnedSkills(EntityArgument.getPlayer(ctx, TARGET)).stream().map(Skill::getDisplayName).map(ITextComponent::getFormattedText).collect(Collectors.joining("\n"))), false);
        return 0;
    }

    private static int forget(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        Skill skill = RegistryHandler.getSkillRegistry().getValue(ResourceLocationArgument.getResourceLocation(ctx, "id"));
        if (skill == null) {
            ctx.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaAPI.MODID + ".command.arsmagica.skillNotFound", skill.getDisplayName()), false);
            return 1;
        }
        CapabilityHelper.forget(EntityArgument.getPlayer(ctx, TARGET), skill);
        ctx.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaAPI.MODID + ".command.arsmagica.forgot", skill.getDisplayName()), false);
        return 0;
    }

    private static int forgetAll(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        CapabilityHelper.forgetAll(EntityArgument.getPlayer(ctx, TARGET));
        ctx.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaAPI.MODID + ".command.arsmagica.forgotAll"), false);
        return 0;
    }

    private static int learn(CommandContext<CommandSource> ctx, Skill skill) throws CommandSyntaxException {
        if (skill == null) {
            ctx.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaAPI.MODID + ".command.arsmagica.skillNotFound", ResourceLocationArgument.getResourceLocation(ctx, "id")), false);
            return 1;
        }
        if (Objects.equals(skill.getRegistryName(), SpellRegistry.MISSING_SHAPE))
            return 0;
        CapabilityHelper.learn(EntityArgument.getPlayer(ctx, TARGET), skill);
        ctx.getSource().sendFeedback(new TranslationTextComponent(ArsMagicaAPI.MODID + ".command.arsmagica.learned", skill.getDisplayName()), false);
        return 0;
    }

    private static int learnAll(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
        for (Skill skill : RegistryHandler.getSkillRegistry()) CommandResearch.learn(ctx, skill);
        return 0;
    }
}
