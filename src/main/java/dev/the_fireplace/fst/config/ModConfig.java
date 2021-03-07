package dev.the_fireplace.fst.config;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.lib.api.storage.access.intermediary.StorageReadBuffer;
import dev.the_fireplace.lib.api.storage.access.intermediary.StorageWriteBuffer;
import dev.the_fireplace.lib.api.storage.lazy.LazyConfig;
import dev.the_fireplace.lib.api.storage.lazy.LazyConfigInitializer;

public final class ModConfig extends LazyConfig {
    private static final ModConfig INSTANCE = LazyConfigInitializer.lazyInitialize(new ModConfig());
    private static final ModConfig DEFAULT_INSTANCE = new ModConfig();
    private final Access access = new Access();

    public static ModConfig getInstance() {
        return INSTANCE;
    }
    public static Access getData() {
        return INSTANCE.access;
    }
    static Access getDefaultData() {
        return DEFAULT_INSTANCE.access;
    }

    private ModConfig() {}

    private boolean enableBlazePowderNetherCropGrowth = true;
    private boolean enableInfestedBlockBlend = true;
    private boolean enableCaveins = true;
    private boolean enableFallingBlockTriggering = true;
    private boolean enableSlimeToMagmaCube = true;
    private boolean enableMagmaCubeGrowth = true;
    private boolean enableSlimeGrowth = true;
    private boolean enableMagmaCubeToSlime = true;
    private short magmaCubeSizeLimit = 8;
    private short slimeSizeLimit = 8;
    private boolean enableSilkSpawners = true;

    @Override
    public String getId() {
        return FiresSurvivalTweaks.MODID;
    }

    @Override
    public void readFrom(StorageReadBuffer buffer) {
        enableBlazePowderNetherCropGrowth = buffer.readBool("enableBlazePowderNetherCropGrowth", enableBlazePowderNetherCropGrowth);
        enableInfestedBlockBlend = buffer.readBool("enableInfestedBlockBlend", enableInfestedBlockBlend);
        enableCaveins = buffer.readBool("enableCaveins", enableCaveins);
        enableFallingBlockTriggering = buffer.readBool("enableFallingBlockTriggering", enableFallingBlockTriggering);
        enableSlimeToMagmaCube = buffer.readBool("enableSlimeToMagmaCube", enableSlimeToMagmaCube);
        enableMagmaCubeGrowth = buffer.readBool("enableMagmaCubeGrowth", enableMagmaCubeGrowth);
        enableSlimeGrowth = buffer.readBool("enableSlimeGrowth", enableSlimeGrowth);
        enableMagmaCubeToSlime = buffer.readBool("enableMagmaCubeToSlime", enableMagmaCubeToSlime);
        magmaCubeSizeLimit = buffer.readShort("magmaCubeSizeLimit", magmaCubeSizeLimit);
        slimeSizeLimit = buffer.readShort("slimeSizeLimit", slimeSizeLimit);
        enableSilkSpawners = buffer.readBool("enableSilkSpawners", enableSilkSpawners);
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

    public final class Access {
        public boolean isEnableBlazePowderNetherCropGrowth() {
            return enableBlazePowderNetherCropGrowth;
        }

        public void setEnableBlazePowderNetherCropGrowth(boolean enableBlazePowderNetherCropGrowth) {
            ModConfig.this.enableBlazePowderNetherCropGrowth = enableBlazePowderNetherCropGrowth;
        }

        public boolean isEnableInfestedBlockBlend() {
            return enableInfestedBlockBlend;
        }

        public void setEnableInfestedBlockBlend(boolean enableInfestedBlockBlend) {
            ModConfig.this.enableInfestedBlockBlend = enableInfestedBlockBlend;
        }

        public boolean isEnableCaveins() {
            return enableCaveins;
        }

        public void setEnableCaveins(boolean enableCaveins) {
            ModConfig.this.enableCaveins = enableCaveins;
        }

        public boolean isEnableFallingBlockTriggering() {
            return enableFallingBlockTriggering;
        }

        public void setEnableFallingBlockTriggering(boolean enableFallingBlockTriggering) {
            ModConfig.this.enableFallingBlockTriggering = enableFallingBlockTriggering;
        }

        public boolean isEnableSlimeToMagmaCube() {
            return enableSlimeToMagmaCube;
        }

        public void setEnableSlimeToMagmaCube(boolean enableSlimeToMagmaCube) {
            ModConfig.this.enableSlimeToMagmaCube = enableSlimeToMagmaCube;
        }

        public boolean isEnableMagmaCubeGrowth() {
            return enableMagmaCubeGrowth;
        }

        public void setEnableMagmaCubeGrowth(boolean enableMagmaCubeGrowth) {
            ModConfig.this.enableMagmaCubeGrowth = enableMagmaCubeGrowth;
        }

        public boolean isEnableSlimeGrowth() {
            return enableSlimeGrowth;
        }

        public void setEnableSlimeGrowth(boolean enableSlimeGrowth) {
            ModConfig.this.enableSlimeGrowth = enableSlimeGrowth;
        }

        public boolean isEnableMagmaCubeToSlime() {
            return enableMagmaCubeToSlime;
        }

        public void setEnableMagmaCubeToSlime(boolean enableMagmaCubeToSlime) {
            ModConfig.this.enableMagmaCubeToSlime = enableMagmaCubeToSlime;
        }

        public short getMagmaCubeSizeLimit() {
            return magmaCubeSizeLimit;
        }

        public void setMagmaCubeSizeLimit(short magmaCubeSizeLimit) {
            ModConfig.this.magmaCubeSizeLimit = magmaCubeSizeLimit;
        }

        public short getSlimeSizeLimit() {
            return slimeSizeLimit;
        }

        public void setSlimeSizeLimit(short slimeSizeLimit) {
            ModConfig.this.slimeSizeLimit = slimeSizeLimit;
        }

        public boolean isEnableSilkSpawners() {
            return enableSilkSpawners;
        }

        public void setEnableSilkSpawners(boolean enableSilkSpawners) {
            ModConfig.this.enableSilkSpawners = enableSilkSpawners;
        }
    }
}
