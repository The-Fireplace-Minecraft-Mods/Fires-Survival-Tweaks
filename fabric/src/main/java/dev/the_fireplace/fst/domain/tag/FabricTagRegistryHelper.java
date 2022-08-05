package dev.the_fireplace.fst.domain.tag;

import dev.the_fireplace.annotateddi.api.di.Implementation;
import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.tags.TagRegistryHelper;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

@Implementation
public final class FabricTagRegistryHelper implements TagRegistryHelper
{
    @Override
    public Tag<Block> register(String id) {
        return TagRegistry.block(new ResourceLocation(FSTConstants.MODID, id));
    }
}
