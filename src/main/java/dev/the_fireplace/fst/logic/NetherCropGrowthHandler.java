package dev.the_fireplace.fst.logic;

import dev.the_fireplace.fst.domain.config.ConfigValues;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class NetherCropGrowthHandler implements UseBlockCallback {
    private final ConfigValues configValues;

    @Inject
    public NetherCropGrowthHandler(ConfigValues configValues) {
        this.configValues = configValues;
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (!configValues.isEnableBlazePowderNetherCropGrowth()) {
            return ActionResult.PASS;
        }
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        ItemStack stack = player.getStackInHand(hand);
        if (!player.world.isClient()
            && state.isOf(Blocks.NETHER_WART)
            && stack.getItem().equals(Items.BLAZE_POWDER)
        ) {
            int age = state.get(NetherWartBlock.AGE);
            if (age < 3) {
                state = state.getBlock().getDefaultState().with(NetherWartBlock.AGE, Math.min(3, world.random.nextInt(3 - age) + age + 1));
                world.setBlockState(hitResult.getBlockPos(), state);
                if (!player.isCreative()) {
                    if (stack.getCount() > 1) {
                        stack.decrement(1);
                    } else {
                        player.setStackInHand(hand, ItemStack.EMPTY);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
