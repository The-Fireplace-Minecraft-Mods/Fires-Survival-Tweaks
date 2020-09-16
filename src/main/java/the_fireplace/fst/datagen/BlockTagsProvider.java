package the_fireplace.fst.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import the_fireplace.fst.tags.BlockTags;

import java.nio.file.Path;

public class BlockTagsProvider extends AbstractTagProvider<Block> {
    public BlockTagsProvider(DataGenerator dataGenerator) {
        super(dataGenerator, Registry.BLOCK);
    }

    @Override
    protected void configure() {
        this.getOrCreateTagBuilder(BlockTags.FALLING_ROCKS).add(Blocks.STONE, Blocks.DIORITE, Blocks.GRANITE, Blocks.ANDESITE, Blocks.NETHERRACK, Blocks.BASALT, Blocks.END_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE);
    }

    @Override
    protected Path getOutput(Identifier identifier) {
        return this.root.getOutput().resolve("data/" + identifier.getNamespace() + "/tags/blocks/" + identifier.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "FST Block Tags";
    }
}
