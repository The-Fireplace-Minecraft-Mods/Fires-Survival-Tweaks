package dev.the_fireplace.fst.eventhandlers;

import dev.the_fireplace.fst.commands.FSTCommands;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;

public final class ServerStarting
{
    private final FSTCommands commands;

    @Inject
    public ServerStarting(FSTCommands commands) {
        this.commands = commands;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        commands.register(event.getServer());
    }
}
