package the_fireplace.fst.logic;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import the_fireplace.fst.tags.FSTBlockTags;

import javax.annotation.Nullable;

public class CaveinLogic {
    public static final int MAX_TREMOR_RANGE = 32;

    public static void cavein(World world, BlockPos fieldCenter, int tremorRange, int tremorChanceCount) {
        if(tremorChanceCount <= 0)
            return;
        int xOff = (world.random.nextBoolean() ? 1 : -1)*world.random.nextInt(tremorRange)+1;
        int yOff = (world.random.nextInt(tremorRange)+1) % Math.min(world.getDimensionHeight()-fieldCenter.getY()-1, fieldCenter.getY()+1);
        int zOff = (world.random.nextBoolean() ? 1 : -1)*world.random.nextInt(tremorRange)+1;
        BlockPos targetPos = fieldCenter.add(xOff, yOff, zOff);
        //TODO Stability checking based on how many rocks have supports?
        for(BlockPos pos: Lists.newArrayList(targetPos, targetPos.north(), targetPos.north(2), targetPos.south(), targetPos.south(2), targetPos.east(), targetPos.east(2), targetPos.west(), targetPos.west(2), targetPos.north().west(), targetPos.north().east(), targetPos.south().west(), targetPos.south().east(), targetPos.down(), targetPos.down(2), targetPos.up(), targetPos.up(2), targetPos.up().north(), targetPos.up().east(), targetPos.up().south(), targetPos.up().west(), targetPos.down().north(), targetPos.down().south(), targetPos.down().east(), targetPos.down().west())) {
            BlockState state = world.getBlockState(pos);
            if(state.getBlock().isIn(FSTBlockTags.FALLING_ROCKS) && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
                makeBlockFall(world, pos, state);
                if(world.random.nextInt(tremorRange*tremorRange/((tremorChanceCount/3)+1)+1) == 0)
                    cavein(world, pos, (int)Math.ceil(tremorRange*0.6), 1);
            }
        }
        cavein(world, fieldCenter, (int)Math.ceil(tremorRange*0.95), tremorChanceCount - 1);
    }

    public static void makeBlockFall(World world, BlockPos pos, @Nullable BlockState state) {
        if(state == null)
            state = world.getBlockState(pos);
        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, state);
        fallingBlockEntity.setHurtEntities(true);
        world.spawnEntity(fallingBlockEntity);
    }
}
