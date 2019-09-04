package the_fireplace.fst;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import the_fireplace.fst.config.ConfigValues;
import the_fireplace.fst.network.CommonProxy;
import the_fireplace.fst.worldgen.WorldGeneratorSilverfish;

@Mod(modid = FiresSurvivalTweaks.MODID, name = FiresSurvivalTweaks.MODNAME, acceptedMinecraftVersions = "[1.12,1.13)", updateJSON = "https://bitbucket.org/The_Fireplace/minecraft-mod-updates/raw/master/fst.json", dependencies = "after:*;after:fluidity", guiFactory = "the_fireplace.fst.config.FSTCfgGuiFactory", acceptableRemoteVersions = "*")
public class FiresSurvivalTweaks {
	public static final String MODID = "fst";
	public static final String MODNAME = "Fire's Survival Tweaks";

	public static Configuration config;
	public static Property ENABLE_RST_PROPERTY;
	public static Property ENABLE_CHA_PROPERTY;
	public static Property ENABLE_BPB_PROPERTY;
	public static Property ENABLE_F2S_PROPERTY;
	public static Property ENABLE_SES_PROPERTY;
	public static Property ENABLE_SEB_PROPERTY;
	public static Property ENABLE_RRS_PROPERTY;
	public static Property ENABLE_RFB_PROPERTY;
	public static Property ENABLE_S2M_PROPERTY;
	public static Property ENABLE_MCG_PROPERTY;
	public static Property MCG_LIMIT_PROPERTY;
	public static Property ENABLE_STAIR_RECIPE_REPLACEMENT_PROPERTY;
	public static Property ENABLE_SILK_SPAWNERS_PROPERTY;

	@SidedProxy(clientSide = "the_fireplace." + MODID + ".network.ClientProxy", serverSide = "the_fireplace." + MODID + ".network.CommonProxy")
	public static CommonProxy proxy;

	public static void syncConfig() {
		ConfigValues.ENABLE_RST = ENABLE_RST_PROPERTY.getBoolean();
		ConfigValues.ENABLE_CHA = ENABLE_CHA_PROPERTY.getBoolean();
		ConfigValues.ENABLE_BPB = ENABLE_BPB_PROPERTY.getBoolean();
		ConfigValues.ENABLE_F2S = ENABLE_F2S_PROPERTY.getBoolean();
		ConfigValues.ENABLE_SES = ENABLE_SES_PROPERTY.getBoolean();
		ConfigValues.ENABLE_SEB = ENABLE_SEB_PROPERTY.getBoolean();
		ConfigValues.ENABLE_RRS = ENABLE_RRS_PROPERTY.getBoolean();
		ConfigValues.ENABLE_RFB = ENABLE_RFB_PROPERTY.getBoolean();
		ConfigValues.ENABLE_S2M = ENABLE_S2M_PROPERTY.getBoolean();
		ConfigValues.ENABLE_MCG = ENABLE_MCG_PROPERTY.getBoolean();
		ConfigValues.MCG_LIMIT = MCG_LIMIT_PROPERTY.getInt();
		ConfigValues.ENABLE_STAIR_RECIPE_REPLACEMENT = ENABLE_STAIR_RECIPE_REPLACEMENT_PROPERTY.getBoolean();
		ConfigValues.ENABLE_SILK_SPAWNERS = ENABLE_SILK_SPAWNERS_PROPERTY.getBoolean();
		if (config.hasChanged())
			config.save();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ENABLE_RST_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_RST_NAME, ConfigValues.ENABLE_RST_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_RST_NAME + ".tooltip")).setRequiresMcRestart(true).setRequiresWorldRestart(true);
		ENABLE_CHA_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_CHA_NAME, ConfigValues.ENABLE_CHA_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_CHA_NAME + ".tooltip")).setRequiresMcRestart(true).setRequiresWorldRestart(true);
		ENABLE_BPB_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_BPB_NAME, ConfigValues.ENABLE_BPB_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_BPB_NAME + ".tooltip"));
		ENABLE_F2S_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_F2S_NAME, ConfigValues.ENABLE_F2S_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_F2S_NAME + ".tooltip"));
		ENABLE_SES_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_SES_NAME, ConfigValues.ENABLE_SES_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_SES_NAME + ".tooltip")).setRequiresMcRestart(true).setRequiresWorldRestart(true);
		ENABLE_SEB_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_SEB_NAME, ConfigValues.ENABLE_SEB_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_SEB_NAME + ".tooltip"));
		ENABLE_RRS_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_RRS_NAME, ConfigValues.ENABLE_RRS_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_RRS_NAME + ".tooltip"));
		ENABLE_RFB_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_RFB_NAME, ConfigValues.ENABLE_RFB_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_RFB_NAME + ".tooltip"));
		ENABLE_S2M_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_S2M_NAME, ConfigValues.ENABLE_S2M_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_S2M_NAME + ".tooltip"));
		ENABLE_MCG_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_MCG_NAME, ConfigValues.ENABLE_MCG_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_MCG_NAME + ".tooltip"));
		MCG_LIMIT_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.MCG_LIMIT_NAME, ConfigValues.MCG_LIMIT_DEFAULT, proxy.translateToLocal(ConfigValues.MCG_LIMIT_NAME + ".tooltip"));
		MCG_LIMIT_PROPERTY.setMinValue(0);
		ENABLE_STAIR_RECIPE_REPLACEMENT_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_STAIR_RECIPE_REPLACEMENT_NAME, ConfigValues.ENABLE_STAIR_RECIPE_REPLACEMENT_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_STAIR_RECIPE_REPLACEMENT_NAME + ".tooltip"));
		ENABLE_SILK_SPAWNERS_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_SILK_SPAWNERS_NAME, ConfigValues.ENABLE_SILK_SPAWNERS_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_SILK_SPAWNERS_NAME + ".tooltip"));
		syncConfig();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (ConfigValues.ENABLE_SES)
			GameRegistry.registerWorldGenerator(new WorldGeneratorSilverfish(), 50);
	}

	public static void replaceCobbleWithStoneInRecipesFor(ItemStack stack) {
		for(IRecipe obj : CraftingManager.REGISTRY)
		{
			if(obj.getRecipeOutput().isItemEqual(stack))
			{
				NonNullList<Ingredient> lst = obj.getIngredients();
				for (int x = 0; x < lst.size(); x++)
				{
					for(ItemStack stack2:lst.get(x).getMatchingStacks())
						if(stack2.getItem().equals(Item.getItemFromBlock(Blocks.COBBLESTONE)))
							lst.set(x, new OreIngredient("stone"));
				}
			}
		}
	}

	public static void initRealStoneTools() {
		ItemStack axe = new ItemStack(Items.STONE_AXE);
		ItemStack hoe = new ItemStack(Items.STONE_HOE);
		ItemStack sword = new ItemStack(Items.STONE_SWORD);
		ItemStack shovel = new ItemStack(Items.STONE_SHOVEL);
		ItemStack pickaxe = new ItemStack(Items.STONE_PICKAXE);
		replaceCobbleWithStoneInRecipesFor(axe);
		replaceCobbleWithStoneInRecipesFor(hoe);
		replaceCobbleWithStoneInRecipesFor(sword);
		replaceCobbleWithStoneInRecipesFor(shovel);
		replaceCobbleWithStoneInRecipesFor(pickaxe);
	}

	public static void initCraftableHorseArmor() {
		ItemStack iron = new ItemStack(Items.IRON_HORSE_ARMOR);
		ItemStack gold = new ItemStack(Items.GOLDEN_HORSE_ARMOR);
		ItemStack diamond = new ItemStack(Items.DIAMOND_HORSE_ARMOR);
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation("minecraft", "iron_horse_armor"), iron, "l l", "ili", "i i", 'l', "leather", 'i', "ingotIron").setRegistryName("iron_horse_armor"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation("minecraft", "gold_horse_armor"), gold, "l l", "ili", "i i", 'l', "leather", 'i', "ingotGold").setRegistryName("gold_horse_armor"));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation("minecraft", "diamond_horse_armor"), diamond, "l l", "ili", "i i", 'l', "leather", 'i', "gemDiamond").setRegistryName("diamond_horse_armor"));
	}

	public static void replaceStairRecipes() {
		for(IRecipe obj : CraftingManager.REGISTRY)
			if(obj.getRecipeOutput().getCount() == 4 && obj.getRecipeOutput().getItem().getRegistryName().getPath().contains("stairs"))
				obj.getRecipeOutput().setCount(8);
	}
}
