package dev.the_fireplace.fst.entrypoints;

import com.google.inject.Injector;
import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.eventhandlers.ConfigGuiRegistrationHandler;
import dev.the_fireplace.fst.eventhandlers.ServerStarting;
import dev.the_fireplace.fst.eventhandlers.UseBlock;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.events.FLEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod("fst")
public final class Forge
{
    public Forge() {
        Injector injector = FSTConstants.getInjector();
        injector.getInstance(TranslatorFactory.class).addTranslator(FSTConstants.MODID);

        MinecraftForge.EVENT_BUS.register(injector.getInstance(UseBlock.class));
        MinecraftForge.EVENT_BUS.register(injector.getInstance(ServerStarting.class));

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            FLEventBus.BUS.register(injector.getInstance(ConfigGuiRegistrationHandler.class));
            return null;
        });

        // Register as optional on both sides
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }
}
