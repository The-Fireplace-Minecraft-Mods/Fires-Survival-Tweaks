package dev.the_fireplace.fst.entrypoints;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.the_fireplace.annotateddi.api.DIContainer;
import dev.the_fireplace.fst.config.ModMenuIntegration;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class ModMenuEntrypoint implements ModMenuApi {
    private final ModMenuIntegration flModMenuIntegration = DIContainer.get().getInstance(ModMenuIntegration.class);

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return flModMenuIntegration.getModConfigScreenFactory();
    }
}
