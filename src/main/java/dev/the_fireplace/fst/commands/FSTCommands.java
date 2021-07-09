package dev.the_fireplace.fst.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.command.injectables.FeedbackSenderFactory;
import dev.the_fireplace.lib.api.command.injectables.Requirements;
import dev.the_fireplace.lib.api.command.interfaces.FeedbackSender;
import dev.the_fireplace.lib.api.lazyio.injectables.ReloadableManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class FSTCommands {

    private final Requirements requirements;
    private final FeedbackSender feedbackSender;
    private final ReloadableManager reloadableManager;

    @Inject
    public FSTCommands(
        Requirements requirements,
        TranslatorFactory translatorFactory,
        FeedbackSenderFactory feedbackSenderFactory,
        ReloadableManager reloadableManager
    ) {
        this.requirements = requirements;
        Translator translator = translatorFactory.getTranslator(FiresSurvivalTweaks.MODID);
        this.feedbackSender = feedbackSenderFactory.get(translator);
        this.reloadableManager = reloadableManager;
    }

    public void register(MinecraftServer server) {
        CommandDispatcher<ServerCommandSource> dispatcher = server.getCommandManager().getDispatcher();

        new FSTReloadCommand(requirements, feedbackSender, reloadableManager).register(dispatcher);
    }
}
