package dev.the_fireplace.fst.entrypoints;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.config.FSTConfigScreenFactory;
import dev.the_fireplace.lib.api.client.entrypoints.ConfigGuiEntrypoint;
import dev.the_fireplace.lib.api.client.interfaces.ConfigGuiRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ConfigGui implements ConfigGuiEntrypoint
{
    private final FSTConfigScreenFactory fstConfigScreenFactory = FSTConstants.getInjector().getInstance(FSTConfigScreenFactory.class);

    @Override
    public void registerConfigGuis(ConfigGuiRegistry configGuiRegistry) {
        configGuiRegistry.register(FSTConstants.MODID, fstConfigScreenFactory::getConfigScreen);
    }
}
