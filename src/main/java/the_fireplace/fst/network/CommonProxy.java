package the_fireplace.fst.network;

import net.minecraft.util.text.translation.I18n;

/**
 * @author The_Fireplace
 */
public class CommonProxy {
	public String translateToLocal(String key, Object... args) {
		return I18n.translateToLocal(key);
	}
}
