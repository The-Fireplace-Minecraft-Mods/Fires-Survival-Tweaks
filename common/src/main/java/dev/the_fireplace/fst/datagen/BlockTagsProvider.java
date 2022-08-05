package dev.the_fireplace.fst.datagen;

import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.nio.file.Path;

public class BlockTagsProvider extends TagsProvider<Block>
{
    public BlockTagsProvider(DataGenerator dataGenerator) {
        super(dataGenerator, Registry.BLOCK);
    }

    @Override
    protected void addTags() {
        this.tag(FSTBlockTags.FALLING_ROCKS).add(
            Blocks.STONE, Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE,
            Blocks.BASALT, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.COAL_ORE, Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE, Blocks.REDSTONE_ORE
        );
        this.tag(FSTBlockTags.SLIME_ABSORBABLES).add(Blocks.SLIME_BLOCK, Blocks.HONEY_BLOCK);
        this.tag(FSTBlockTags.MAGMA_ABSORBABLES).add(Blocks.MAGMA_BLOCK);
    }

    //@Override
    protected Path getPath(ResourceLocation identifier) {
        return this.generator.getOutputFolder()
            .resolve("data")
            .resolve(identifier.getNamespace())
            .resolve("tags").resolve("blocks")
            .resolve(identifier.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "FST Block Tags";
    }
}
