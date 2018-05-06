package the_fireplace.fst;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import the_fireplace.fst.config.ConfigValues;

@Mod.EventBusSubscriber(modid=FiresSurvivalTweaks.MODID)
public class RegistryEvents {
	@SubscribeEvent
	public static void recipeRegister(RegistryEvent<IRecipe> event){
		if (ConfigValues.ENABLE_RST)
			FiresSurvivalTweaks.initRst();
		if (ConfigValues.ENABLE_CHA)
			FiresSurvivalTweaks.initCha();
	}
}
