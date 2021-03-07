package dev.the_fireplace.fst.tags;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;

public class FSTBlockTags {
    public static final Tag<Block> FALLING_ROCKS = register("falling_rocks");
    public static final Tag<Block> SLIME_ABSORBABLES = register("slime_absorbables");
    public static final Tag<Block> MAGMA_ABSORBABLES = register("magma_absorbables");

    private static Tag<Block> register(String id) {
        return TagRegistry.block(new Identifier(FiresSurvivalTweaks.MODID, id));
    }

    public static void setContainer(TagContainer<Block> tagContainer) {

    }
}
