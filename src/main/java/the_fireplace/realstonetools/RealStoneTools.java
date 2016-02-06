package the_fireplace.realstonetools;

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

@Mod(modid="realstonetools", name="Real Stone Tools")
public class RealStoneTools {
	@EventHandler
	public void init(FMLInitializationEvent event){
		ItemStack axe = new ItemStack(Items.stone_axe);
		ItemStack hoe = new ItemStack(Items.stone_hoe);
		ItemStack sword = new ItemStack(Items.stone_sword);
		ItemStack shovel = new ItemStack(Items.stone_shovel);
		ItemStack pickaxe = new ItemStack(Items.stone_pickaxe);
		removeRecipe(axe);
		removeRecipe(hoe);
		removeRecipe(sword);
		removeRecipe(shovel);
		removeRecipe(pickaxe);
		ItemStack stone = new ItemStack(Blocks.stone);
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
			if(recipeResult != null){
				if(recipeResult.getItem() == resultItem.getItem() && recipeResult.getItemDamage() == resultItem.getItemDamage()){
					recipes.remove(scan);
					scan--;
				}
			}
		}
	}
}
