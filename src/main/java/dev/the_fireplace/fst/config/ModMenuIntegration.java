package dev.the_fireplace.fst.config;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.client.injectables.ConfigScreenBuilderFactory;
import dev.the_fireplace.lib.api.client.interfaces.ConfigScreenBuilder;
import dev.the_fireplace.lib.api.lazyio.injectables.ConfigStateManager;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Environment(EnvType.CLIENT)
@Singleton
public final class ModMenuIntegration implements ModMenuApi {
    private static final String TRANSLATION_BASE = "text.config." + FiresSurvivalTweaks.MODID + ".";
    private static final String OPTION_TRANSLATION_BASE = "text.config." + FiresSurvivalTweaks.MODID + ".option.";

    private final Translator translator;
    private final ConfigStateManager configStateManager;
    private final ModConfig config;
    private final ConfigValues defaultConfigValues;
    private final ConfigScreenBuilderFactory configScreenBuilderFactory;

    private ConfigScreenBuilder configScreenBuilder;

    @Inject
    public ModMenuIntegration(
        TranslatorFactory translatorFactory,
        ConfigStateManager configStateManager,
        ModConfig config,
        @Named("default") ConfigValues defaultConfigValues,
        ConfigScreenBuilderFactory configScreenBuilderFactory
    ) {
        this.translator = translatorFactory.getTranslator(FiresSurvivalTweaks.MODID);
        this.configStateManager = configStateManager;
        this.config = config;
        this.defaultConfigValues = defaultConfigValues;
        this.configScreenBuilderFactory = configScreenBuilderFactory;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getModId() {
        return FiresSurvivalTweaks.MODID;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            this.configScreenBuilder = configScreenBuilderFactory.create(
                translator,
                TRANSLATION_BASE + "title",
                TRANSLATION_BASE + "general",
                parent,
                () -> configStateManager.save(config)
            );
            addGeneralCategoryEntries();

            return this.configScreenBuilder.build();
        };
    }

    private void addGeneralCategoryEntries() {
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableBlazePowderNetherCropGrowth",
            config.isEnableBlazePowderNetherCropGrowth(),
            defaultConfigValues.isEnableBlazePowderNetherCropGrowth(),
            config::setEnableBlazePowderNetherCropGrowth
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableInfestedBlockBlend",
            config.isEnableInfestedBlockBlend(),
            defaultConfigValues.isEnableInfestedBlockBlend(),
            config::setEnableInfestedBlockBlend
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableCaveins",
            config.isEnableCaveins(),
            defaultConfigValues.isEnableCaveins(),
            config::setEnableCaveins,
            (byte) 2
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableFallingBlockTriggering",
            config.isEnableFallingBlockTriggering(),
            defaultConfigValues.isEnableFallingBlockTriggering(),
            config::setEnableFallingBlockTriggering
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableSlimeToMagmaCube",
            config.isEnableSlimeToMagmaCube(),
            defaultConfigValues.isEnableSlimeToMagmaCube(),
            config::setEnableSlimeToMagmaCube
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableMagmaCubeToSlime",
            config.isEnableMagmaCubeToSlime(),
            defaultConfigValues.isEnableMagmaCubeToSlime(),
            config::setEnableMagmaCubeToSlime
        );
        String slimeGrowth = OPTION_TRANSLATION_BASE + "enableSlimeGrowth";
        configScreenBuilder.addBoolToggle(
            slimeGrowth,
            config.isEnableSlimeGrowth(),
            defaultConfigValues.isEnableSlimeGrowth(),
            config::setEnableSlimeGrowth
        );
        String slimeSizeLimit = OPTION_TRANSLATION_BASE + "slimeSizeLimit";
        configScreenBuilder.addShortField(
            slimeSizeLimit,
            config.getSlimeSizeLimit(),
            defaultConfigValues.getSlimeSizeLimit(),
            config::setSlimeSizeLimit,
            (short) 0,
            Short.MAX_VALUE
        );
        String magmaCubeGrowth = OPTION_TRANSLATION_BASE + "enableMagmaCubeGrowth";
        configScreenBuilder.addBoolToggle(
            magmaCubeGrowth,
            config.isEnableMagmaCubeGrowth(),
            defaultConfigValues.isEnableMagmaCubeGrowth(),
            config::setEnableMagmaCubeGrowth
        );
        String magmaCubeSizeLimit = OPTION_TRANSLATION_BASE + "magmaCubeSizeLimit";
        configScreenBuilder.addShortField(
            magmaCubeSizeLimit,
            config.getMagmaCubeSizeLimit(),
            defaultConfigValues.getMagmaCubeSizeLimit(),
            config::setMagmaCubeSizeLimit,
            (short) 0,
            Short.MAX_VALUE
        );
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableSilkSpawners",
            config.isEnableSilkSpawners(),
            defaultConfigValues.isEnableSilkSpawners(),
            config::setEnableSilkSpawners
        );

        configScreenBuilder.addOptionDependency(slimeGrowth, slimeSizeLimit, (value) -> (boolean)value);
        configScreenBuilder.addOptionDependency(magmaCubeGrowth, magmaCubeSizeLimit, (value) -> (boolean)value);
    }
}
