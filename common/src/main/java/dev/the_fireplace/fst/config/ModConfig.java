package dev.the_fireplace.fst.config;

import dev.the_fireplace.annotateddi.api.di.Implementation;
import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.lib.api.io.interfaces.access.StorageReadBuffer;
import dev.the_fireplace.lib.api.io.interfaces.access.StorageWriteBuffer;
import dev.the_fireplace.lib.api.lazyio.injectables.ConfigStateManager;
import dev.the_fireplace.lib.api.lazyio.interfaces.Config;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Implementation("dev.the_fireplace.fst.domain.config.ConfigValues")
@Singleton
public final class ModConfig implements Config, ConfigValues {
    private final ConfigValues defaultConfig;

    @Inject
    public ModConfig(ConfigStateManager configStateManager, @Named("default") ConfigValues defaultConfig) {
        this.defaultConfig = defaultConfig;
        configStateManager.initialize(this);
    }

    private boolean enableBlazePowderNetherCropGrowth;
    private boolean enableInfestedBlockBlend;
    private boolean enableCaveins;
    private boolean enableFallingBlockTriggering;
    private boolean enableSlimeToMagmaCube;
    private boolean enableMagmaCubeGrowth;
    private boolean enableSlimeGrowth;
    private boolean enableMagmaCubeToSlime;
    private short magmaCubeSizeLimit;
    private short slimeSizeLimit;
    private boolean enableSilkSpawners;

    @Override
    public String getId() {
        return FSTConstants.MODID;
    }

    @Override
    public void readFrom(StorageReadBuffer buffer) {
        enableBlazePowderNetherCropGrowth = buffer.readBool("enableBlazePowderNetherCropGrowth", defaultConfig.isEnableBlazePowderNetherCropGrowth());
        enableInfestedBlockBlend = buffer.readBool("enableInfestedBlockBlend", defaultConfig.isEnableInfestedBlockBlend());
        enableCaveins = buffer.readBool("enableCaveins", defaultConfig.isEnableCaveins());
        enableFallingBlockTriggering = buffer.readBool("enableFallingBlockTriggering", defaultConfig.isEnableFallingBlockTriggering());
        enableSlimeToMagmaCube = buffer.readBool("enableSlimeToMagmaCube", defaultConfig.isEnableSlimeToMagmaCube());
        enableMagmaCubeGrowth = buffer.readBool("enableMagmaCubeGrowth", defaultConfig.isEnableMagmaCubeGrowth());
        enableSlimeGrowth = buffer.readBool("enableSlimeGrowth", defaultConfig.isEnableSlimeGrowth());
        enableMagmaCubeToSlime = buffer.readBool("enableMagmaCubeToSlime", defaultConfig.isEnableMagmaCubeToSlime());
        magmaCubeSizeLimit = buffer.readShort("magmaCubeSizeLimit", defaultConfig.getMagmaCubeSizeLimit());
        slimeSizeLimit = buffer.readShort("slimeSizeLimit", defaultConfig.getSlimeSizeLimit());
        enableSilkSpawners = buffer.readBool("enableSilkSpawners", defaultConfig.isEnableSilkSpawners());
    }

    @Override
    public void writeTo(StorageWriteBuffer buffer) {
        buffer.writeBool("enableBlazePowderNetherCropGrowth", enableBlazePowderNetherCropGrowth);
        buffer.writeBool("enableInfestedBlockBlend", enableInfestedBlockBlend);
        buffer.writeBool("enableCaveins", enableCaveins);
        buffer.writeBool("enableFallingBlockTriggering", enableFallingBlockTriggering);
        buffer.writeBool("enableSlimeToMagmaCube", enableSlimeToMagmaCube);
        buffer.writeBool("enableMagmaCubeGrowth", enableMagmaCubeGrowth);
        buffer.writeBool("enableSlimeGrowth", enableSlimeGrowth);
        buffer.writeBool("enableMagmaCubeToSlime", enableMagmaCubeToSlime);
        buffer.writeShort("magmaCubeSizeLimit", magmaCubeSizeLimit);
        buffer.writeShort("slimeSizeLimit", slimeSizeLimit);
        buffer.writeBool("enableSilkSpawners", enableSilkSpawners);
    }

    @Override
    public boolean isEnableBlazePowderNetherCropGrowth() {
        return enableBlazePowderNetherCropGrowth;
    }

    @Override
    public void setEnableBlazePowderNetherCropGrowth(boolean enableBlazePowderNetherCropGrowth) {
        this.enableBlazePowderNetherCropGrowth = enableBlazePowderNetherCropGrowth;
    }

    @Override
    public boolean isEnableInfestedBlockBlend() {
        return enableInfestedBlockBlend;
    }

    @Override
    public void setEnableInfestedBlockBlend(boolean enableInfestedBlockBlend) {
        this.enableInfestedBlockBlend = enableInfestedBlockBlend;
    }

    @Override
    public boolean isEnableCaveins() {
        return enableCaveins;
    }

    @Override
    public void setEnableCaveins(boolean enableCaveins) {
        this.enableCaveins = enableCaveins;
    }

    @Override
    public boolean isEnableFallingBlockTriggering() {
        return enableFallingBlockTriggering;
    }

    @Override
    public void setEnableFallingBlockTriggering(boolean enableFallingBlockTriggering) {
        this.enableFallingBlockTriggering = enableFallingBlockTriggering;
    }

    @Override
    public boolean isEnableSlimeToMagmaCube() {
        return enableSlimeToMagmaCube;
    }

    @Override
    public void setEnableSlimeToMagmaCube(boolean enableSlimeToMagmaCube) {
        this.enableSlimeToMagmaCube = enableSlimeToMagmaCube;
    }

    @Override
    public boolean isEnableMagmaCubeGrowth() {
        return enableMagmaCubeGrowth;
    }

    @Override
    public void setEnableMagmaCubeGrowth(boolean enableMagmaCubeGrowth) {
        this.enableMagmaCubeGrowth = enableMagmaCubeGrowth;
    }

    @Override
    public boolean isEnableSlimeGrowth() {
        return enableSlimeGrowth;
    }

    @Override
    public void setEnableSlimeGrowth(boolean enableSlimeGrowth) {
        this.enableSlimeGrowth = enableSlimeGrowth;
    }

    @Override
    public boolean isEnableMagmaCubeToSlime() {
        return enableMagmaCubeToSlime;
    }

    @Override
    public void setEnableMagmaCubeToSlime(boolean enableMagmaCubeToSlime) {
        this.enableMagmaCubeToSlime = enableMagmaCubeToSlime;
    }

    @Override
    public short getMagmaCubeSizeLimit() {
        return magmaCubeSizeLimit;
    }

    @Override
    public void setMagmaCubeSizeLimit(short magmaCubeSizeLimit) {
        this.magmaCubeSizeLimit = magmaCubeSizeLimit;
    }

    @Override
    public short getSlimeSizeLimit() {
        return slimeSizeLimit;
    }

    @Override
    public void setSlimeSizeLimit(short slimeSizeLimit) {
        this.slimeSizeLimit = slimeSizeLimit;
    }

    @Override
    public boolean isEnableSilkSpawners() {
        return enableSilkSpawners;
    }

    @Override
    public void setEnableSilkSpawners(boolean enableSilkSpawners) {
        this.enableSilkSpawners = enableSilkSpawners;
    }
}
