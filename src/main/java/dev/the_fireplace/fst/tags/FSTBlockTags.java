package dev.the_fireplace.fst.tags;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class FSTBlockTags {
    public static final Tag.Identified<Block> FALLING_ROCKS = register("falling_rocks");
    public static final Tag.Identified<Block> SLIME_ABSORBABLES = register("slime_absorbables");
    public static final Tag.Identified<Block> MAGMA_ABSORBABLES = register("magma_absorbables");

    private static Tag.Identified<Block> register(String id) {
        return (Tag.Identified<Block>) TagRegistry.block(new Identifier(FiresSurvivalTweaks.MODID, id));
    }
}
