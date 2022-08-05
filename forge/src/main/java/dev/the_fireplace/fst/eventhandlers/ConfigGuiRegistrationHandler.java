package dev.the_fireplace.fst.eventhandlers;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.config.FSTConfigScreenFactory;
import dev.the_fireplace.lib.api.events.ConfigScreenRegistration;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;

public final class ConfigGuiRegistrationHandler
{
    private final FSTConfigScreenFactory configScreenFactory;

    @Inject
    public ConfigGuiRegistrationHandler(FSTConfigScreenFactory configScreenFactory) {
        this.configScreenFactory = configScreenFactory;
    }

    @SubscribeEvent
    public void registerConfigGui(ConfigScreenRegistration configScreenRegistration) {
        configScreenRegistration.getConfigGuiRegistry().register(FSTConstants.MODID, configScreenFactory::getConfigScreen);
    }
}