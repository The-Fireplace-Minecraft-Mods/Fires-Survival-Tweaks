package dev.the_fireplace.fst.logic;

import dev.the_fireplace.fst.domain.config.ConfigValues;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class NetherCropGrowthHandler
{
    private final ConfigValues configValues;

    @Inject
    public NetherCropGrowthHandler(ConfigValues configValues) {
        this.configValues = configValues;
    }

    public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        if (!configValues.isEnableBlazePowderNetherCropGrowth()) {
            return InteractionResult.PASS;
        }
        BlockState state = world.getBlockState(hitResult.getBlockPos());
        ItemStack stack = player.getItemInHand(hand);
        if (!player.level.isClientSide()
            && state.getBlock().equals(Blocks.NETHER_WART)
            && stack.getItem().equals(Items.BLAZE_POWDER)
        ) {
            int age = state.getValue(NetherWartBlock.AGE);
            if (age < 3) {
                state = state.getBlock().defaultBlockState().setValue(NetherWartBlock.AGE, Math.min(3, world.random.nextInt(3 - age) + age + 1));
                world.setBlockAndUpdate(hitResult.getBlockPos(), state);
                if (!player.isCreative()) {
                    if (stack.getCount() > 1) {
                        stack.shrink(1);
                    } else {
                        player.setItemInHand(hand, ItemStack.EMPTY);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
