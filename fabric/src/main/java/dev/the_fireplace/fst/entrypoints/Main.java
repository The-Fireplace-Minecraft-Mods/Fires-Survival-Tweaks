package dev.the_fireplace.fst.entrypoints;

import com.google.inject.Injector;
import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.commands.FSTCommands;
import dev.the_fireplace.fst.logic.NetherCropGrowthHandler;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public final class Main implements ModInitializer
{
    @Override
    public void onInitialize() {
        Injector injector = FSTConstants.getInjector();
        injector.getInstance(TranslatorFactory.class).addTranslator(FSTConstants.MODID);
        FSTCommands commands = injector.getInstance(FSTCommands.class);

        ServerLifecycleEvents.SERVER_STARTING.register(commands::register);

        UseBlockCallback.EVENT.register(injector.getInstance(NetherCropGrowthHandler.class)::interact);

        /*if (false) {
            LOGGER.debug("Generating data...");
            DataGenerator gen = diContainer.getInstance(DataGeneratorFactory.class).createAdditive(Paths.get("..", "src", "main", "resources"));
            gen.addProvider(new BlockTagsProvider(gen));
            try {
                gen.run();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
