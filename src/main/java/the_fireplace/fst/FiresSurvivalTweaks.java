package the_fireplace.fst;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;

@Mod(modid= FiresSurvivalTweaks.MODID, name= FiresSurvivalTweaks.MODNAME, acceptedMinecraftVersions="[1.11,)", updateJSON = "http://thefireplace.bitnamiapp.com/jsons/fst.json", dependencies="after:*")
public class FiresSurvivalTweaks {
	public static final String MODID = "firessurvivaltweaks";
	public static final String MODNAME = "Fire's Survival Tweaks";
	@EventHandler
	public void init(FMLInitializationEvent event){
		ItemStack axe = new ItemStack(Items.STONE_AXE);
		ItemStack hoe = new ItemStack(Items.STONE_HOE);
		ItemStack sword = new ItemStack(Items.STONE_SWORD);
		ItemStack shovel = new ItemStack(Items.STONE_SHOVEL);
		ItemStack pickaxe = new ItemStack(Items.STONE_PICKAXE);
		removeRecipe(axe);
		removeRecipe(hoe);
		removeRecipe(sword);
		removeRecipe(shovel);
		removeRecipe(pickaxe);
		ItemStack stone = new ItemStack(Blocks.STONE);
		GameRegistry.addRecipe(new ShapedOreRecipe(axe, "rr", "rs", " s", 'r', stone, 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(axe, "rr", "sr", "s ", 'r', stone, 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(hoe, "rr", " s", " s", 'r', stone, 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(hoe, "rr", "s ", "s ", 'r', stone, 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(sword, "r", "r", "s", 'r', stone, 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(shovel, "r", "s", "s", 'r', stone, 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(pickaxe, "rrr", " s ", " s ", 'r', stone, 's', "stickWood"));
	}
	private void removeRecipe(ItemStack resultItem){
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
}
