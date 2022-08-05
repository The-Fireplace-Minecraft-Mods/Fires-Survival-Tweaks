package dev.the_fireplace.fst.logic;

import com.google.common.collect.Lists;
import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public final class CaveinLogic
{
    public static final int MAX_TREMOR_RANGE = 32;

    public static void cavein(Level world, BlockPos fieldCenter, int tremorRange, int tremorChanceCount) {
        for (int remainingTremors = tremorChanceCount; remainingTremors > 0; remainingTremors--) {
            tremor(world, fieldCenter, tremorRange, remainingTremors);
            tremorRange = (int) Math.ceil(tremorRange * 0.95);
        }
    }

    private static void tremor(Level world, BlockPos fieldCenter, int tremorRange, int remainingIterations) {
        int xOff = (world.random.nextBoolean() ? 1 : -1) * world.random.nextInt(tremorRange) + 1;
        int yRange = Math.min(world.getHeight() - fieldCenter.getY() - 1, fieldCenter.getY() + 1) - world.dimensionType().minY();
        if (yRange < 1) {
            yRange = 1;
        }
        int yOff = (world.random.nextInt(tremorRange) + 1) % yRange;
        int zOff = (world.random.nextBoolean() ? 1 : -1) * world.random.nextInt(tremorRange) + 1;
        BlockPos targetPos = fieldCenter.offset(xOff, yOff, zOff);
        //TODO Stability checking based on how many rocks have supports?
        for (BlockPos pos : Lists.newArrayList(
            targetPos, targetPos.north(), targetPos.north(2), targetPos.south(), targetPos.south(2),
            targetPos.east(), targetPos.east(2), targetPos.west(), targetPos.west(2),
            targetPos.north().west(), targetPos.north().east(), targetPos.south().west(), targetPos.south().east(),
            targetPos.below(), targetPos.below(2), targetPos.above(), targetPos.above(2),
            targetPos.above().north(), targetPos.above().east(), targetPos.above().south(), targetPos.above().west(),
            targetPos.below().north(), targetPos.below().south(), targetPos.below().east(), targetPos.below().west())
        ) {
            BlockState state = world.getBlockState(pos);
            if (state.is(FSTBlockTags.FALLING_ROCKS) && FallingBlock.isFree(world.getBlockState(pos.below()))) {
                makeBlockFall(world, pos, state);
                if (remainingIterations > 1 && world.random.nextInt(tremorRange * tremorRange / ((remainingIterations / 3) + 1) + 1) == 0) {
                    tremor(world, pos, (int) Math.ceil(tremorRange * 0.6), 1);
                }
            }
        }
    }

    public static void makeBlockFall(Level world, BlockPos pos, @Nullable BlockState state) {
        if (state == null) {
            state = world.getBlockState(pos);
        }
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, pos, state);
        fallingBlockEntity.setHurtsEntities(2, 40);
    }
}
