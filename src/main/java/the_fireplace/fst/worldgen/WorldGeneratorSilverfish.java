package the_fireplace.fst.worldgen;

import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorSilverfish implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0)
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateSurface(World world, Random random, int BlockX, int BlockZ) {
		int maxY = 255;
		int minY = 1;
		for (int i = minY; i < maxY; i++) {
			if (random.nextInt(i+2) <= 1) {
				int Xcoord = BlockX + random.nextInt(16);
				int Zcoord = BlockZ + random.nextInt(16);
				int Ycoord = random.nextInt(maxY - minY) + minY;
				//BlockMatcher.forBlock(Blocks.STONE)
				(new WorldGenMinable(Blocks.MONSTER_EGG.getDefaultState(), 2, p_apply_1_ -> {
					if (p_apply_1_ != null && p_apply_1_.getBlock() == Blocks.STONE)
					{
						BlockStone.EnumType blockstone$enumtype = p_apply_1_.getValue(BlockStone.VARIANT);
						return blockstone$enumtype.isNatural();
					}
					else
					{
						return false;
					}
				})).generate(world, random, new BlockPos(Xcoord, Ycoord, Zcoord));
			}
		}
	}
}
