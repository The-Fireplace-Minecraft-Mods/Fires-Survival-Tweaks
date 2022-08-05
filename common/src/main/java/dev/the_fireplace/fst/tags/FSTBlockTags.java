package dev.the_fireplace.fst.tags;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.tags.TagRegistryHelper;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public final class FSTBlockTags
{
    public static final Tag.Named<Block> FALLING_ROCKS = register("falling_rocks");
    public static final Tag.Named<Block> SLIME_ABSORBABLES = register("slime_absorbables");
    public static final Tag.Named<Block> MAGMA_ABSORBABLES = register("magma_absorbables");

    private static Tag.Named<Block> register(String id) {
        return FSTConstants.getInjector().getInstance(TagRegistryHelper.class).register(id);
    }
}
