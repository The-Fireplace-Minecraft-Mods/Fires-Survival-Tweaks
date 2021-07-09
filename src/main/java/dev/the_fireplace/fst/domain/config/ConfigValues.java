package dev.the_fireplace.fst.domain.config;

public interface ConfigValues {
    boolean isEnableBlazePowderNetherCropGrowth();

    void setEnableBlazePowderNetherCropGrowth(boolean enableBlazePowderNetherCropGrowth);

    boolean isEnableInfestedBlockBlend();

    void setEnableInfestedBlockBlend(boolean enableInfestedBlockBlend);

    boolean isEnableCaveins();

    void setEnableCaveins(boolean enableCaveins);

    boolean isEnableFallingBlockTriggering();

    void setEnableFallingBlockTriggering(boolean enableFallingBlockTriggering);

    boolean isEnableSlimeToMagmaCube();

    void setEnableSlimeToMagmaCube(boolean enableSlimeToMagmaCube);

    boolean isEnableMagmaCubeGrowth();

    void setEnableMagmaCubeGrowth(boolean enableMagmaCubeGrowth);

    boolean isEnableSlimeGrowth();

    void setEnableSlimeGrowth(boolean enableSlimeGrowth);

    boolean isEnableMagmaCubeToSlime();

    void setEnableMagmaCubeToSlime(boolean enableMagmaCubeToSlime);

    short getMagmaCubeSizeLimit();

    void setMagmaCubeSizeLimit(short magmaCubeSizeLimit);

    short getSlimeSizeLimit();

    void setSlimeSizeLimit(short slimeSizeLimit);

    boolean isEnableSilkSpawners();

    void setEnableSilkSpawners(boolean enableSilkSpawners);
}
