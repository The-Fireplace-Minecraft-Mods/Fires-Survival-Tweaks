package the_fireplace.fst;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
                    if (applyBlazePowder(event.getItemStack(), event.getWorld(), event.getPos(), event.getEntityPlayer())) {
                        if (!event.getWorld().isRemote) {
                            event.getWorld().playEvent(2005, event.getPos(), 0);
                        }
                    }
                }
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
