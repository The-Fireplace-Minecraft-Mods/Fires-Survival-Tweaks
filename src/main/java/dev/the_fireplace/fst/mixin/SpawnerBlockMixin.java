package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.annotateddi.api.DIContainer;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.SilkedSpawnerManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin extends BlockWithEntity {

	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
			config = DIContainer.get().getInstance(ConfigValues.class);
		}

		return config;
	}
	
	protected SpawnerBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(at = @At("HEAD"), method = "onStacksDropped", cancellable = true)
	private void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, CallbackInfo callbackInfo) {
		if (getConfig().isEnableSilkSpawners() && SilkedSpawnerManager.isSilkedSpawner(world, pos))
			callbackInfo.cancel();
	}
}
