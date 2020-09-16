package the_fireplace.fst.tags;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import the_fireplace.fst.FiresSurvivalTweaks;

public class BlockTags {
    public static final Tag.Identified<Block> FALLING_ROCKS = register("falling_rocks");

    private static Tag.Identified<Block> register(String id) {
        return (Tag.Identified<Block>) TagRegistry.block(new Identifier(FiresSurvivalTweaks.MODID, id));
    }
}
