package the_fireplace.fst;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = FiresSurvivalTweaks.MODID)
public class ClientEvents {
	@SubscribeEvent
	public static void configChanged(ConfigChangedEvent event) {
		if (event.getModID().equals(FiresSurvivalTweaks.MODID))
			FiresSurvivalTweaks.syncConfig();
	}
}
