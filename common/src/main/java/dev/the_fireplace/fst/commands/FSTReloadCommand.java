package dev.the_fireplace.fst.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;
import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.lib.api.command.injectables.Requirements;
import dev.the_fireplace.lib.api.command.interfaces.FeedbackSender;
import dev.the_fireplace.lib.api.command.interfaces.RegisterableCommand;
import dev.the_fireplace.lib.api.lazyio.injectables.ReloadableManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

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
    public CommandNode<CommandSourceStack> register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        return commandDispatcher.register(Commands.literal("fstreload")
            .requires(requirements::manageServer)
            .executes(this::execute)
        );
    }

    private int execute(CommandContext<CommandSourceStack> commandContext) {
        reloadableManager.reload(FSTConstants.MODID);
        feedbackSender.basic(commandContext, "fst.command.reload.reloaded");
        return 1;
    }
}
