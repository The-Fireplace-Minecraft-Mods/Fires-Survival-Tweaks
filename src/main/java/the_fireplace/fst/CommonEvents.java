package the_fireplace.fst;

import com.google.common.eventbus.Subscribe;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import the_fireplace.fst.config.ConfigValues;

/**
 * @author The_Fireplace
 */
public class CommonEvents {
    @SubscribeEvent
    public void blockInteract(PlayerInteractEvent.RightClickBlock event) {
        if (ConfigValues.ENABLE_BPB)
            if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.BLAZE_POWDER) {
                if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockNetherWart && event.getWorld().getBlockState(event.getPos().down()).getBlock() == Blocks.SOUL_SAND) {
                    if (!MinecraftForge.EVENT_BUS.post(new BlockEvent.CropGrowEvent.Pre(event.getWorld(), event.getPos(), event.getWorld().getBlockState(event.getPos())))) {
                        IBlockState preState = event.getWorld().getBlockState(event.getPos());
                        if (applyBlazePowder(event.getItemStack(), event.getWorld(), event.getPos(), event.getEntityPlayer())) {
                            if (!event.getWorld().isRemote) {
                                event.getWorld().playEvent(2005, event.getPos(), 0);
                            }
                            MinecraftForge.EVENT_BUS.post(new BlockEvent.CropGrowEvent.Post(event.getWorld(), event.getPos(), preState, event.getWorld().getBlockState(event.getPos())));
                        }
                    }
                }
            }
    }

    @SubscribeEvent
    public void cropGrow(BlockEvent.CropGrowEvent.Post event) {
        if (ConfigValues.ENABLE_F2S && !event.getWorld().isRemote)
            if (event.getWorld().getBlockState(event.getPos().down()).getBlock() instanceof BlockFarmland) {
                if (event.getWorld().rand.nextInt(50) == 0) {
                    event.getWorld().setBlockState(event.getPos().down(), Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
                }
            }
    }

    @SubscribeEvent
    public void bonemealEvent(BonemealEvent event) {
        if (ConfigValues.ENABLE_F2S && !event.getWorld().isRemote && event.getResult() == Event.Result.DEFAULT)
            if (event.getWorld().getBlockState(event.getPos().down()).getBlock() instanceof BlockFarmland) {
                if (event.getWorld().rand.nextInt(20) == 0) {
                    event.getWorld().setBlockState(event.getPos().down(), Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
                }
            }
    }

    @SubscribeEvent
    public void breakSpeed(PlayerEvent.BreakSpeed event){
    	if(ConfigValues.ENABLE_SEB && event.getState().getBlock() == Blocks.MONSTER_EGG){
    		if(Blocks.MONSTER_EGG.getBlockHardness(null, null, null) != Blocks.STONE.getBlockHardness(null, null, null))
    			Blocks.MONSTER_EGG.setHardness(Blocks.STONE.getBlockHardness(null, null, null));
    		event.setNewSpeed(event.getEntityPlayer().getDigSpeed(Blocks.STONE.getDefaultState(), event.getPos()));
		    System.out.println(event.getNewSpeed());
	    }
    }

    public static boolean applyBlazePowder(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player) {
        IBlockState iblockstate = worldIn.getBlockState(target);

        if (iblockstate.getBlock() instanceof BlockNetherWart) {
            int currage = iblockstate.getValue(BlockNetherWart.AGE);
            if (currage < 3) {
                if (!worldIn.isRemote) {
                    iblockstate = iblockstate.getBlock().getDefaultState().withProperty(BlockNetherWart.AGE, worldIn.rand.nextInt(3 - currage) + currage + 1);
                    worldIn.setBlockState(target, iblockstate);
                    if (!player.capabilities.isCreativeMode)
                        stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }
}
