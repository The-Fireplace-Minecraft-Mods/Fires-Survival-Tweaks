package the_fireplace.fst.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import the_fireplace.fst.FiresSurvivalTweaks;

/**
 * @author The_Fireplace
 */
@SideOnly(Side.CLIENT)
public class FSTConfigGui extends GuiConfig {

	public FSTConfigGui(GuiScreen parentScreen) {
		super(parentScreen, new ConfigElement(FiresSurvivalTweaks.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), FiresSurvivalTweaks.MODID, true,
				true, GuiConfig.getAbridgedConfigPath(FiresSurvivalTweaks.config.toString()));
	}
}