package the_fireplace.fst;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
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

@Mod(modid= FiresSurvivalTweaks.MODID, name= FiresSurvivalTweaks.MODNAME, acceptedMinecraftVersions="[1.11,)", updateJSON = "http://thefireplace.bitnamiapp.com/jsons/fst.json", dependencies="after:*;after:fluidity", guiFactory = "the_fireplace.fst.config.FSTCfgGuiFactory")
public class FiresSurvivalTweaks {
	public static final String MODID = "fst";
	public static final String MODNAME = "Fire's Survival Tweaks";

	public static Configuration config;
	public static Property ENABLE_RST_PROPERTY;

	@SidedProxy(clientSide = "the_fireplace."+MODID+".network.ClientProxy", serverSide = "the_fireplace."+MODID+".network.CommonProxy")
	public static CommonProxy proxy;

	public static void syncConfig() {
		ConfigValues.ENABLE_RST = ENABLE_RST_PROPERTY.getBoolean();
		if (config.hasChanged())
			config.save();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		ENABLE_RST_PROPERTY = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.ENABLE_RST_NAME, ConfigValues.ENABLE_RST_DEFAULT, proxy.translateToLocal(ConfigValues.ENABLE_RST_NAME + ".tooltip"));
		syncConfig();
	}

	@EventHandler
	public void init(FMLInitializationEvent event){
		if(ConfigValues.ENABLE_RST)
			initRst();
	}
	private void removeRecipes(ItemStack resultItem){
		ItemStack recipeResult;
		ArrayList recipes = (ArrayList) CraftingManager.getInstance().getRecipeList();
		for(int scan = 0;scan < recipes.size();scan++){
			IRecipe tmpRecipe = (IRecipe) recipes.get(scan);
			recipeResult = tmpRecipe.getRecipeOutput();
			if(!recipeResult.isEmpty()){
				if(recipeResult.getItem() == resultItem.getItem() && recipeResult.getItemDamage() == resultItem.getItemDamage()){
					recipes.remove(scan);
					scan--;
				}
			}
		}
	}
	public void initRst(){
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
}
