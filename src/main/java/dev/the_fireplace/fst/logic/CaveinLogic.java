package dev.the_fireplace.fst.logic;

import com.google.common.collect.Lists;
import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CaveinLogic {
    public static final int MAX_TREMOR_RANGE = 32;

    public static void cavein(World world, BlockPos fieldCenter, int tremorRange, int tremorChanceCount) {
        for (int remainingTremors = tremorChanceCount; remainingTremors > 0; remainingTremors--) {
            tremor(world, fieldCenter, tremorRange, remainingTremors);
            tremorRange = (int)Math.ceil(tremorRange*0.95);
        }
    }

    private static void tremor(World world, BlockPos fieldCenter, int tremorRange, int remainingIterations) {
        int xOff = (world.random.nextBoolean() ? 1 : -1) * world.random.nextInt(tremorRange) + 1;
        int yRange = Math.min(world.getHeight() - fieldCenter.getY() - 1, fieldCenter.getY() + 1) - world.getDimension().getMinimumY();
        if (yRange < 1) {
            yRange = 1;
        }
        int yOff = (world.random.nextInt(tremorRange) + 1) % yRange;
        int zOff = (world.random.nextBoolean() ? 1 : -1) * world.random.nextInt(tremorRange) + 1;
        BlockPos targetPos = fieldCenter.add(xOff, yOff, zOff);
        //TODO Stability checking based on how many rocks have supports?
        for (BlockPos pos: Lists.newArrayList(
            targetPos, targetPos.north(), targetPos.north(2), targetPos.south(), targetPos.south(2),
            targetPos.east(), targetPos.east(2), targetPos.west(), targetPos.west(2),
            targetPos.north().west(), targetPos.north().east(), targetPos.south().west(), targetPos.south().east(),
            targetPos.down(), targetPos.down(2), targetPos.up(), targetPos.up(2),
            targetPos.up().north(), targetPos.up().east(), targetPos.up().south(), targetPos.up().west(),
            targetPos.down().north(), targetPos.down().south(), targetPos.down().east(), targetPos.down().west())
        ) {
            BlockState state = world.getBlockState(pos);
            if (state.isIn(FSTBlockTags.FALLING_ROCKS) && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
                makeBlockFall(world, pos, state);
                if (remainingIterations > 1 && world.random.nextInt(tremorRange * tremorRange /((remainingIterations /3)+1)+1) == 0) {
                    tremor(world, pos, (int) Math.ceil(tremorRange * 0.6), 1);
                }
            }
        }
    }

    public static void makeBlockFall(World world, BlockPos pos, @Nullable BlockState state) {
        if (state == null) {
            state = world.getBlockState(pos);
        }
        FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, state);
        fallingBlockEntity.setHurtEntities(2, 40);
    }
}
