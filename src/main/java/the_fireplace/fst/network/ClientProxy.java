package the_fireplace.fst.network;

import net.minecraft.client.resources.I18n;

/**
 * @author The_Fireplace
 */
public class ClientProxy extends CommonProxy {
	@Override
	public String translateToLocal(String key, Object... args) {
		return I18n.format(key, args);
	}
}
