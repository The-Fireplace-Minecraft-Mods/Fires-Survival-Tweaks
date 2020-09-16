package the_fireplace.fst;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.data.DataGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import the_fireplace.fst.datagen.AdditiveDataGenerator;
import the_fireplace.fst.datagen.BlockTagsProvider;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

public class FiresSurvivalTweaks implements ModInitializer {
	public static final String MODID = "fst";
	public static ModConfig config;
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Override
	public void onInitialize() {
		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		//noinspection ConstantConditions
		if(true) {
			LOGGER.debug("Generating data...");
			DataGenerator gen = new AdditiveDataGenerator(Paths.get("..", "src", "main", "resources"), Collections.emptySet());
			gen.install(new BlockTagsProvider(gen));
			try {
				gen.run();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
