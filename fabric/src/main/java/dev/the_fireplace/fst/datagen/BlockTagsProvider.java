package dev.the_fireplace.fst.datagen;

import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.world.level.block.Blocks;

public final class BlockTagsProvider extends FabricTagProvider.BlockTagProvider
{
    public BlockTagsProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        this.tag(FSTBlockTags.FALLING_ROCKS).add(
            Blocks.STONE, Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE,
            Blocks.BASALT, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.COAL_ORE, Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE, Blocks.REDSTONE_ORE
        );
        this.tag(FSTBlockTags.SLIME_ABSORBABLES).add(Blocks.SLIME_BLOCK, Blocks.HONEY_BLOCK);
        this.tag(FSTBlockTags.MAGMA_ABSORBABLES).add(Blocks.MAGMA_BLOCK);
    }

    @Override
    public String getName() {
        return "FST Block Tags";
    }
}
