package the_fireplace.fst;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import the_fireplace.fst.config.ConfigValues;
import the_fireplace.fst.network.CommonProxy;

import java.util.ArrayList;

@Mod(modid = FiresSurvivalTweaks.MODID, name = FiresSurvivalTweaks.MODNAME, acceptedMinecraftVersions = "[1.11,)", updateJSON = "http://thefireplace.bitnamiapp.com/jsons/fst.json", dependencies = "after:*;after:fluidity", guiFactory = "the_fireplace.fst.config.FSTCfgGuiFactory")
public class FiresSurvivalTweaks {
    public static final String MODID = "fst";
    public static final String MODNAME = "Fire's Survival Tweaks";

    public static Configuration config;
    public static Property ENABLE_RST_PROPERTY;
    public static Property ENABLE_CHA_PROPERTY;
    public static Property ENABLE_BPB_PROPERTY;
    public static Property ENABLE_F2S_PROPERTY;

    @SidedProxy(clientSide = "the_fireplace." + MODID + ".network.ClientProxy", serverSide = "the_fireplace." + MODID + ".network.CommonProxy")
    public static CommonProxy proxy;

    public static void syncConfig() {
        ConfigValues.ENABLE_RST = ENABLE_RST_PROPERTY.getBoolean();
        ConfigValues.ENABLE_CHA = ENABLE_CHA_PROPERTY.getBoolean();
        ConfigValues.ENABLE_BPB = ENABLE_BPB_PROPERTY.getBoolean();
        ConfigValues.ENABLE_F2S = ENABLE_F2S_PROPERTY.getBoolean();
        if (config.hasChanged())
            config.save();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ENABLE_RST_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_RST_NAME, ConfigValues.ENABLE_RST_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_RST_NAME + ".tooltip"));
        ENABLE_CHA_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_CHA_NAME, ConfigValues.ENABLE_CHA_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_CHA_NAME + ".tooltip"));
        ENABLE_BPB_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_BPB_NAME, ConfigValues.ENABLE_BPB_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_BPB_NAME + ".tooltip"));
        ENABLE_F2S_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_F2S_NAME, ConfigValues.ENABLE_F2S_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_F2S_NAME + ".tooltip"));
        ENABLE_BPB_PROPERTY.setRequiresMcRestart(false);
        ENABLE_BPB_PROPERTY.setRequiresWorldRestart(false);
        syncConfig();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (ConfigValues.ENABLE_RST)
            initRst();
        if (ConfigValues.ENABLE_CHA)
            initCha();
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
    }

    private void removeRecipes(ItemStack resultItem) {
        ItemStack recipeResult;
        ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
        for (int scan = 0; scan < recipes.size(); scan++) {
            IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
            recipeResult = tmpRecipe.getRecipeOutput();
            if (!recipeResult.isEmpty()) {
                if (recipeResult.getItem() == resultItem.getItem() && recipeResult.getItemDamage() == resultItem.getItemDamage()) {
                    recipes.remove(scan);
                    scan--;
                }
            }
        }
    }

    public void initRst() {
        ItemStack axe = new ItemStack(Items.STONE_AXE);
        ItemStack hoe = new ItemStack(Items.STONE_HOE);
        ItemStack sword = new ItemStack(Items.STONE_SWORD);
        ItemStack shovel = new ItemStack(Items.STONE_SHOVEL);
        ItemStack pickaxe = new ItemStack(Items.STONE_PICKAXE);
        removeRecipes(axe);
        removeRecipes(hoe);
        removeRecipes(sword);
        removeRecipes(shovel);
        removeRecipes(pickaxe);
        ItemStack stone = new ItemStack(Blocks.STONE);
        GameRegistry.addRecipe(new ShapedOreRecipe(axe, "rr", "rs", " s", 'r', stone, 's', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(axe, "rr", "sr", "s ", 'r', stone, 's', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(hoe, "rr", " s", " s", 'r', stone, 's', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(hoe, "rr", "s ", "s ", 'r', stone, 's', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(sword, "r", "r", "s", 'r', stone, 's', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(shovel, "r", "s", "s", 'r', stone, 's', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(pickaxe, "rrr", " s ", " s ", 'r', stone, 's', "stickWood"));
    }

    public void initCha() {
        ItemStack iron = new ItemStack(Items.IRON_HORSE_ARMOR);
        ItemStack gold = new ItemStack(Items.GOLDEN_HORSE_ARMOR);
        ItemStack diamond = new ItemStack(Items.DIAMOND_HORSE_ARMOR);
        GameRegistry.addRecipe(new ShapedOreRecipe(iron, "l l", "ili", "i i", 'l', "leather", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(gold, "l l", "ili", "i i", 'l', "leather", 'i', "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(diamond, "l l", "ili", "i i", 'l', "leather", 'i', "gemDiamond"));
    }
}
