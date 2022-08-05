package dev.the_fireplace.fst.domain.tags;

import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public interface TagRegistryHelper
{
    Tag.Named<Block> register(String id);
}
