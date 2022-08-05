package dev.the_fireplace.fst.config;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.lib.api.chat.injectables.TranslatorFactory;
import dev.the_fireplace.lib.api.chat.interfaces.Translator;
import dev.the_fireplace.lib.api.client.injectables.ConfigScreenBuilderFactory;
import dev.the_fireplace.lib.api.client.interfaces.ConfigScreenBuilder;
import dev.the_fireplace.lib.api.client.interfaces.OptionBuilder;
import dev.the_fireplace.lib.api.lazyio.injectables.ConfigStateManager;
import net.minecraft.client.gui.screens.Screen;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public final class FSTConfigScreenFactory
{
    private static final String TRANSLATION_BASE = "text.config." + FSTConstants.MODID + ".";
    private static final String OPTION_TRANSLATION_BASE = "text.config." + FSTConstants.MODID + ".option.";

    private final Translator translator;
    private final ConfigStateManager configStateManager;
    private final ModConfig config;
    private final ConfigValues defaultConfigValues;
    private final ConfigScreenBuilderFactory configScreenBuilderFactory;

    private ConfigScreenBuilder configScreenBuilder;

    @Inject
    public FSTConfigScreenFactory(
        TranslatorFactory translatorFactory,
        ConfigStateManager configStateManager,
        ModConfig config,
        @Named("default") ConfigValues defaultConfigValues,
        ConfigScreenBuilderFactory configScreenBuilderFactory
    ) {
        this.translator = translatorFactory.getTranslator(FSTConstants.MODID);
        this.configStateManager = configStateManager;
        this.config = config;
        this.defaultConfigValues = defaultConfigValues;
        this.configScreenBuilderFactory = configScreenBuilderFactory;
    }

    public Screen getConfigScreen(Screen parent) {
        this.configScreenBuilder = configScreenBuilderFactory.create(
            translator,
            TRANSLATION_BASE + "title",
            TRANSLATION_BASE + "general",
            parent,
            () -> configStateManager.save(config)
        ).orElseThrow();
        addGeneralCategoryEntries();

        return this.configScreenBuilder.build();
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
            config::setEnableCaveins
        ).setDescriptionRowCount((byte) 2);
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
        OptionBuilder<Boolean> enableSlimeGrowth = configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableSlimeGrowth",
            config.isEnableSlimeGrowth(),
            defaultConfigValues.isEnableSlimeGrowth(),
            config::setEnableSlimeGrowth
        );
        configScreenBuilder.addShortField(
            OPTION_TRANSLATION_BASE + "slimeSizeLimit",
            config.getSlimeSizeLimit(),
            defaultConfigValues.getSlimeSizeLimit(),
            config::setSlimeSizeLimit
        ).setMinimum((short) 0).addDependency(enableSlimeGrowth);
        OptionBuilder<Boolean> enableMagmaCubeGrowth = configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableMagmaCubeGrowth",
            config.isEnableMagmaCubeGrowth(),
            defaultConfigValues.isEnableMagmaCubeGrowth(),
            config::setEnableMagmaCubeGrowth
        );
        configScreenBuilder.addShortField(
            OPTION_TRANSLATION_BASE + "magmaCubeSizeLimit",
            config.getMagmaCubeSizeLimit(),
            defaultConfigValues.getMagmaCubeSizeLimit(),
            config::setMagmaCubeSizeLimit
        ).setMinimum((short) 0).addDependency(enableMagmaCubeGrowth);
        configScreenBuilder.addBoolToggle(
            OPTION_TRANSLATION_BASE + "enableSilkSpawners",
            config.isEnableSilkSpawners(),
            defaultConfigValues.isEnableSilkSpawners(),
            config::setEnableSilkSpawners
        );
    }
}
