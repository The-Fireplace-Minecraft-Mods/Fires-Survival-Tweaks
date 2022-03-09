package dev.the_fireplace.fst.tags;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FSTBlockTags
{
    public static final TagKey<Block> FALLING_ROCKS = register("falling_rocks");
    public static final TagKey<Block> SLIME_ABSORBABLES = register("slime_absorbables");
    public static final TagKey<Block> MAGMA_ABSORBABLES = register("magma_absorbables");

    private static TagKey<Block> register(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(FiresSurvivalTweaks.MODID, id));
    }
}
