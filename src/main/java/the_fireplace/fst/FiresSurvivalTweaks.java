package the_fireplace.fst;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
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

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			if(!config.enableBlazePowderNetherCropGrowth)
				return ActionResult.PASS;
			BlockState state = world.getBlockState(hitResult.getBlockPos());
			ItemStack stack = player.getStackInHand(hand);
			if(!player.world.isClient()
				&& state.isOf(Blocks.NETHER_WART)
				&& stack.getItem().equals(Items.BLAZE_POWDER)) {
				int age = state.get(NetherWartBlock.AGE);
				if(age < 3) {
					state = state.getBlock().getDefaultState().with(NetherWartBlock.AGE, Math.min(3, world.random.nextInt(3 - age) + age + 1));
					world.setBlockState(hitResult.getBlockPos(), state);
					if (!player.isCreative()) {
						if(stack.getCount() > 1)
							stack.decrement(1);
						else
							player.setStackInHand(hand, ItemStack.EMPTY);
					}
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		});

		//noinspection ConstantConditions
		if(false) {
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
