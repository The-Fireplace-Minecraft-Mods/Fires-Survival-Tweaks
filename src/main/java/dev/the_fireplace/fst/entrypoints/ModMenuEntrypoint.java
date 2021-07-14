package dev.the_fireplace.fst.entrypoints;

import dev.the_fireplace.annotateddi.api.DIContainer;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.config.ModMenuIntegration;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public final class ModMenuEntrypoint implements ModMenuApi {

    private final ModMenuIntegration flModMenuIntegration = DIContainer.get().getInstance(ModMenuIntegration.class);

    @Override
    public String getModId() {
        return FiresSurvivalTweaks.MODID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return flModMenuIntegration.getConfigScreenFactory();
    }
}
