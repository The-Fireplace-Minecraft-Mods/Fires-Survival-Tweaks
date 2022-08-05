package dev.the_fireplace.fst.logic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;
import java.util.Set;

public final class SilkedSpawnerManager
{
    private static final Map<ServerLevel, Set<BlockPos>> silkedSpawners = Maps.newHashMap();

    public static void addSilkedSpawner(ServerLevel world, BlockPos pos) {
        silkedSpawners.putIfAbsent(world, Sets.newConcurrentHashSet());
        silkedSpawners.get(world).add(pos);
    }

    public static boolean isSilkedSpawner(ServerLevel world, BlockPos pos) {
        silkedSpawners.putIfAbsent(world, Sets.newConcurrentHashSet());
        return silkedSpawners.get(world).remove(pos);
    }
}
