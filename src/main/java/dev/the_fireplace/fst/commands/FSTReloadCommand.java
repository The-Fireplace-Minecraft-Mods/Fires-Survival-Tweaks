package dev.the_fireplace.fst.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.lib.api.command.injectables.Requirements;
import dev.the_fireplace.lib.api.command.interfaces.FeedbackSender;
import dev.the_fireplace.lib.api.command.interfaces.RegisterableCommand;
import dev.the_fireplace.lib.api.lazyio.injectables.ReloadableManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public final class FSTReloadCommand implements RegisterableCommand {

    private final Requirements requirements;
    private final ReloadableManager reloadableManager;
    private final FeedbackSender feedbackSender;

    FSTReloadCommand(Requirements requirements, FeedbackSender feedbackSender, ReloadableManager reloadableManager) {
        this.requirements = requirements;
        this.feedbackSender = feedbackSender;
        this.reloadableManager = reloadableManager;
    }

    @Override
    public CommandNode<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        return commandDispatcher.register(CommandManager.literal("fstreload")
            .requires(requirements::manageServer)
            .executes(this::execute)
        );
    }

    private int execute(CommandContext<ServerCommandSource> commandContext) {
        reloadableManager.reload(FiresSurvivalTweaks.MODID);
        feedbackSender.basic(commandContext, "fst.command.reload.reloaded");
        return 1;
    }
}
