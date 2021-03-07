package dev.the_fireplace.fst.config;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.lib.api.chat.TranslatorManager;
import dev.the_fireplace.lib.api.client.ConfigScreenBuilder;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration extends ConfigScreenBuilder implements ModMenuApi {
    private static final String TRANSLATION_BASE = "text.config." + FiresSurvivalTweaks.MODID + ".";
    private static final String OPTION_TRANSLATION_BASE = "text.config." + FiresSurvivalTweaks.MODID + ".option.";
    private static final ModConfig.Access DEFAULT_CONFIG = ModConfig.getDefaultData();
    private final ModConfig.Access configAccess = ModConfig.getData();

    public ModMenuIntegration() {
        super(TranslatorManager.getInstance().getTranslator(FiresSurvivalTweaks.MODID));
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getModId() {
        return FiresSurvivalTweaks.MODID;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(translator.getTranslatedString(TRANSLATION_BASE + "title"));

            buildConfigCategories(builder);

            builder.setSavingRunnable(() -> ModConfig.getInstance().save());
            return builder.build();
        };
    }

    private void buildConfigCategories(ConfigBuilder builder) {
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(translator.getTranslatedString(TRANSLATION_BASE + "general"));
        addGeneralCategoryEntries(entryBuilder, general);
    }

    private void addGeneralCategoryEntries(ConfigEntryBuilder entryBuilder, ConfigCategory global) {
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableBlazePowderNetherCropGrowth",
            configAccess.isEnableBlazePowderNetherCropGrowth(),
            DEFAULT_CONFIG.isEnableBlazePowderNetherCropGrowth(),
            configAccess::setEnableBlazePowderNetherCropGrowth
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableInfestedBlockBlend",
            configAccess.isEnableInfestedBlockBlend(),
            DEFAULT_CONFIG.isEnableInfestedBlockBlend(),
            configAccess::setEnableInfestedBlockBlend
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableCaveins",
            configAccess.isEnableCaveins(),
            DEFAULT_CONFIG.isEnableCaveins(),
            configAccess::setEnableCaveins,
            (byte)2
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableFallingBlockTriggering",
            configAccess.isEnableFallingBlockTriggering(),
            DEFAULT_CONFIG.isEnableFallingBlockTriggering(),
            configAccess::setEnableFallingBlockTriggering
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableSlimeToMagmaCube",
            configAccess.isEnableSlimeToMagmaCube(),
            DEFAULT_CONFIG.isEnableSlimeToMagmaCube(),
            configAccess::setEnableSlimeToMagmaCube
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableMagmaCubeToSlime",
            configAccess.isEnableMagmaCubeToSlime(),
            DEFAULT_CONFIG.isEnableMagmaCubeToSlime(),
            configAccess::setEnableMagmaCubeToSlime
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableSlimeGrowth",
            configAccess.isEnableSlimeGrowth(),
            DEFAULT_CONFIG.isEnableSlimeGrowth(),
            configAccess::setEnableSlimeGrowth
        );
        addShortField(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "slimeSizeLimit",
            configAccess.getSlimeSizeLimit(),
            DEFAULT_CONFIG.getSlimeSizeLimit(),
            configAccess::setSlimeSizeLimit,
            (short) 0,
            Short.MAX_VALUE
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableMagmaCubeGrowth",
            configAccess.isEnableMagmaCubeGrowth(),
            DEFAULT_CONFIG.isEnableMagmaCubeGrowth(),
            configAccess::setEnableMagmaCubeGrowth
        );
        addShortField(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "magmaCubeSizeLimit",
            configAccess.getMagmaCubeSizeLimit(),
            DEFAULT_CONFIG.getMagmaCubeSizeLimit(),
            configAccess::setMagmaCubeSizeLimit,
            (short) 0,
            Short.MAX_VALUE
        );
        addBoolToggle(
            entryBuilder,
            global,
            OPTION_TRANSLATION_BASE + "enableSilkSpawners",
            configAccess.isEnableSilkSpawners(),
            DEFAULT_CONFIG.isEnableSilkSpawners(),
            configAccess::setEnableSilkSpawners
        );
    }
}
