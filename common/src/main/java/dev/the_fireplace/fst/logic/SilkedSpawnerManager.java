package dev.the_fireplace.fst.logic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Set;

public final class SilkedSpawnerManager
{
    private static final Map<Level, Set<BlockPos>> silkedSpawners = Maps.newHashMap();

    public static void addSilkedSpawner(Level world, BlockPos pos) {
        silkedSpawners.putIfAbsent(world, Sets.newConcurrentHashSet());
        silkedSpawners.get(world).add(pos);
    }

    public static boolean isSilkedSpawner(Level world, BlockPos pos) {
        silkedSpawners.putIfAbsent(world, Sets.newConcurrentHashSet());
        return silkedSpawners.get(world).remove(pos);
    }
}
