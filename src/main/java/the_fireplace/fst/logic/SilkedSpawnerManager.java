package the_fireplace.fst.logic;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.Set;

public class SilkedSpawnerManager {
    private static final Map<ServerWorld, Set<BlockPos>> silkedSpawners = Maps.newHashMap();

    public static void addSilkedSpawner(ServerWorld world, BlockPos pos) {
        silkedSpawners.putIfAbsent(world, Sets.newConcurrentHashSet());
        silkedSpawners.get(world).add(pos);
    }

    public static boolean isSilkedSpawner(ServerWorld world, BlockPos pos) {
        silkedSpawners.putIfAbsent(world, Sets.newConcurrentHashSet());
        return silkedSpawners.get(world).remove(pos);
    }
}
