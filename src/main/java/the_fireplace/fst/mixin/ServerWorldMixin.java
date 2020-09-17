package the_fireplace.fst.mixin;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import the_fireplace.fst.FiresSurvivalTweaks;
import the_fireplace.fst.logic.CoordMath;
import the_fireplace.fst.logic.RockslideLogic;
import the_fireplace.fst.tags.FSTBlockTags;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
	@Shadow @Final private MinecraftServer server;

	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
	}

	private final ConcurrentHashMap<Vec3i, Set<BlockPos>> tremorZones = new ConcurrentHashMap<>();

	@Inject(at = @At(value="TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		//noinspection ConstantConditions
		if(FiresSurvivalTweaks.config.enableRockslides && getServer().getTicks() % 60 == 0) {
			//clear tremor storage and trigger tremors
			for(Vec3i zonePos: tremorZones.keySet()) {
				Set<BlockPos> tremorPositions = Sets.newHashSet();
				addTremorPositions(tremorPositions, zonePos);
				BlockPos fieldCenter = CoordMath.getFocalPoint(tremorPositions);
				//Iron Pickaxe breaks 7.5 stone in 3 seconds. So every six seconds with an iron pickaxe without stopping, there is one chance of rockslide.
				RockslideLogic.rockslide(this, fieldCenter, CoordMath.getAverageDistanceFromFocus(tremorPositions, fieldCenter), tremorPositions.size()-7);
			}
		}
	}

	@Inject(at = @At(value="TAIL"), method = "onBlockChanged")
	private void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo callbackInfo) {
		if(FiresSurvivalTweaks.config.enableRockslides && oldBlock.isIn(FSTBlockTags.FALLING_ROCKS) && !newBlock.isIn(FSTBlockTags.FALLING_ROCKS)) {
			getTremorZone(pos).add(pos);
		}
	}

	private Set<BlockPos> getTremorZone(BlockPos pos) {
		Vec3i zone = new Vec3i(pos.getX()/(2*RockslideLogic.MAX_TREMOR_RANGE), pos.getY()/(2*RockslideLogic.MAX_TREMOR_RANGE), pos.getZ()/(2*RockslideLogic.MAX_TREMOR_RANGE));
		tremorZones.putIfAbsent(zone, Sets.newHashSet());
		return tremorZones.get(pos);
	}

	private void addTremorPositions(Collection<BlockPos> posCollection, Vec3i startingZone) {
		posCollection.addAll(tremorZones.remove(startingZone));
		if(!tremorZones.isEmpty())
			for(int i=-1;i<=1;i++)
				for(int j=-1;j<=1;j++)
					for(int k=-1;k<=1;k++) {
						Vec3i testVec = new Vec3i(startingZone.getX()+i, startingZone.getY()+j, startingZone.getZ()+k);
						if(!testVec.equals(startingZone) && tremorZones.containsKey(testVec))
							addTremorPositions(posCollection, testVec);
					}
	}
}
