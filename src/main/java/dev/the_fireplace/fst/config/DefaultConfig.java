package dev.the_fireplace.fst.config;

import dev.the_fireplace.annotateddi.api.di.Implementation;
import dev.the_fireplace.fst.domain.config.ConfigValues;

import javax.inject.Singleton;

@Implementation(name="default")
@Singleton
public final class DefaultConfig implements ConfigValues {
    @Override
    public boolean isEnableBlazePowderNetherCropGrowth() {
        return true;
    }

    @Override
    public void setEnableBlazePowderNetherCropGrowth(boolean enableBlazePowderNetherCropGrowth) {

    }

    @Override
    public boolean isEnableInfestedBlockBlend() {
        return true;
    }

    @Override
    public void setEnableInfestedBlockBlend(boolean enableInfestedBlockBlend) {

    }

    @Override
    public boolean isEnableCaveins() {
        return true;
    }

    @Override
    public void setEnableCaveins(boolean enableCaveins) {

    }

    @Override
    public boolean isEnableFallingBlockTriggering() {
        return true;
    }

    @Override
    public void setEnableFallingBlockTriggering(boolean enableFallingBlockTriggering) {

    }

    @Override
    public boolean isEnableSlimeToMagmaCube() {
        return true;
    }

    @Override
    public void setEnableSlimeToMagmaCube(boolean enableSlimeToMagmaCube) {

    }

    @Override
    public boolean isEnableMagmaCubeGrowth() {
        return true;
    }

    @Override
    public void setEnableMagmaCubeGrowth(boolean enableMagmaCubeGrowth) {

    }

    @Override
    public boolean isEnableSlimeGrowth() {
        return true;
    }

    @Override
    public void setEnableSlimeGrowth(boolean enableSlimeGrowth) {

    }

    @Override
    public boolean isEnableMagmaCubeToSlime() {
        return true;
    }

    @Override
    public void setEnableMagmaCubeToSlime(boolean enableMagmaCubeToSlime) {

    }

    @Override
    public short getMagmaCubeSizeLimit() {
        return 8;
    }

    @Override
    public void setMagmaCubeSizeLimit(short magmaCubeSizeLimit) {

    }

    @Override
    public short getSlimeSizeLimit() {
        return 8;
    }

    @Override
    public void setSlimeSizeLimit(short slimeSizeLimit) {

    }

    @Override
    public boolean isEnableSilkSpawners() {
        return true;
    }

    @Override
    public void setEnableSilkSpawners(boolean enableSilkSpawners) {

    }
}
