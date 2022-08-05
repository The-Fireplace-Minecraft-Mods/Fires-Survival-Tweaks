package dev.the_fireplace.fst.tags;

import dev.the_fireplace.fst.FSTConstants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class FSTBlockTags
{
    public static final TagKey<Block> FALLING_ROCKS = register("falling_rocks");
    public static final TagKey<Block> SLIME_ABSORBABLES = register("slime_absorbables");
    public static final TagKey<Block> MAGMA_ABSORBABLES = register("magma_absorbables");

    private static TagKey<Block> register(String id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(FSTConstants.MODID, id));
    }
}
