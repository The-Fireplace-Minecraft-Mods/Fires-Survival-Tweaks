package the_fireplace.fst;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@Config(name = FiresSurvivalTweaks.MODID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean enableBlazePowderNetherCropGrowth = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableSilverfishEggGeneration = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableSilverfishEggBlend = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableCaveins = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableFallingBlockTriggering = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableSlimeToMagmaCube = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableMagmaCubeGrowth = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableSlimeGrowth = true;
    @ConfigEntry.Gui.Tooltip
    public boolean enableMagmaCubeToSlime = true;
    @ConfigEntry.Gui.Tooltip
    public int magmaCubeSizeLimit = 64;
    @ConfigEntry.Gui.Tooltip
    public int slimeSizeLimit = 64;
    @ConfigEntry.Gui.Tooltip
    public boolean enableSilkSpawners = true;
}
