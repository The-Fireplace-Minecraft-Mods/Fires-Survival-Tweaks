package dev.the_fireplace.fst;

import com.google.inject.Injector;
import dev.the_fireplace.annotateddi.api.entrypoints.DIModInitializer;
import dev.the_fireplace.fst.commands.FSTCommands;
import dev.the_fireplace.fst.datagen.BlockTagsProvider;
import dev.the_fireplace.fst.logic.NetherCropGrowthHandler;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.datagen.injectables.DataGeneratorFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.data.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Paths;

public final class FiresSurvivalTweaks implements DIModInitializer {
	public static final String MODID = "fst";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize(Injector diContainer) {
		diContainer.getInstance(TranslatorFactory.class).addTranslator(MODID);
		FSTCommands commands = diContainer.getInstance(FSTCommands.class);

		ServerLifecycleEvents.SERVER_STARTING.register(commands::register);

		UseBlockCallback.EVENT.register(diContainer.getInstance(NetherCropGrowthHandler.class));

		//noinspection ConstantConditions
		if (false) {
			LOGGER.debug("Generating data...");
			DataGenerator gen = diContainer.getInstance(DataGeneratorFactory.class).createAdditive(Paths.get("..", "src", "main", "resources"));
			gen.install(new BlockTagsProvider(gen));
			try {
				gen.run();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

}
