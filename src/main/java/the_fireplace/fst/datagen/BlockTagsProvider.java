package the_fireplace.fst.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import the_fireplace.fst.tags.FSTBlockTags;

import java.nio.file.Path;

public class BlockTagsProvider extends AbstractTagProvider<Block> {
    public BlockTagsProvider(DataGenerator dataGenerator) {
        super(dataGenerator, Registry.BLOCK);
    }

    @Override
    protected void configure() {
        this.getOrCreateTagBuilder(FSTBlockTags.FALLING_ROCKS).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(BlockTags.BASE_STONE_NETHER).add(Blocks.END_STONE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.COAL_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE, Blocks.REDSTONE_ORE);
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
