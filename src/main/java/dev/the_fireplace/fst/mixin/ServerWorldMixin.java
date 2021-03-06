package dev.the_fireplace.fst.mixin;

import com.google.common.collect.Sets;
import dev.the_fireplace.annotateddi.impl.AnnotatedDI;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.CaveinLogic;
import dev.the_fireplace.fst.logic.CoordMath;
import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {
	@Shadow public abstract ServerChunkManager getChunkManager();

	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
			config = AnnotatedDI.getInjector().getInstance(ConfigValues.class);
		}

		return config;
	}

	protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
		super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
	}

	private final ConcurrentHashMap<Vec3i, Set<BlockPos>> tremorZones = new ConcurrentHashMap<>();
	private boolean tremoring = false;

	@Inject(at = @At(value="TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		//noinspection ConstantConditions
		if (getConfig().isEnableCaveins() && !tremoring && getServer().getTicks() % 40 == 0) {
			tremoring = true;
			//TODO run calculations on another thread?
			//clear tremor storage and trigger tremors
			for (Vec3i zonePos: tremorZones.keySet()) {
				Collection<BlockPos> tremorPositions = getTremorPositions(zonePos);
				BlockPos fieldCenter = CoordMath.getFocalPoint(tremorPositions);
				//Iron Pickaxe breaks 7.5 stone in 3 seconds. So every six seconds with an iron pickaxe without stopping, there is one chance of rockslide.
				CaveinLogic.cavein(this, fieldCenter, CoordMath.getAverageDistanceFromFocus(tremorPositions, fieldCenter), tremorPositions.size()-7);
			}
			if (!tremorZones.isEmpty()) {
				FiresSurvivalTweaks.LOGGER.debug("Not all tremor zones were removed!");
			}
			tremoring = false;
		}
	}

	@Inject(at = @At(value="HEAD"), method = "onBlockChanged")
	private void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo callbackInfo) {
		if (getConfig().isEnableCaveins()
			&& getChunkManager().getWorldChunk(pos.getX() >> 4, pos.getZ() >> 4) != null
			&& oldBlock.isIn(FSTBlockTags.FALLING_ROCKS)
			&& !newBlock.isIn(FSTBlockTags.FALLING_ROCKS)
		) {
			getTremorZone(pos).add(pos);
		}
	}

	private Collection<BlockPos> getTremorZone(BlockPos pos) {
		Vec3i zone = new Vec3i(pos.getX()/(2* CaveinLogic.MAX_TREMOR_RANGE), pos.getY()/(2* CaveinLogic.MAX_TREMOR_RANGE), pos.getZ()/(2* CaveinLogic.MAX_TREMOR_RANGE));
		tremorZones.putIfAbsent(zone, Sets.newHashSet());
		return tremorZones.get(zone);
	}

	private Collection<BlockPos> getTremorPositions(Vec3i startingZone) {
		Collection<BlockPos> posCollection = Sets.newHashSet();
		Set<BlockPos> zoneContents = tremorZones.remove(startingZone);
		if (zoneContents != null)
			posCollection.addAll(zoneContents);
		if (!tremorZones.isEmpty())
			for (int i=-1;i<=1;i++)
				for (int j=-1;j<=1;j++)
					for (int k=-1;k<=1;k++) {
						Vec3i testVec = new Vec3i(startingZone.getX()+i, startingZone.getY()+j, startingZone.getZ()+k);
						if (!testVec.equals(startingZone) && tremorZones.containsKey(testVec))
							posCollection.addAll(getTremorPositions(testVec));
					}
		return posCollection;
	}
}
