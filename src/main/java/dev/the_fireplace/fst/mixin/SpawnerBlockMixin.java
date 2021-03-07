package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.config.ModConfig;
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
import dev.the_fireplace.fst.logic.SilkedSpawnerManager;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin extends BlockWithEntity {
	protected SpawnerBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(at = @At(value="HEAD"), method = "onStacksDropped", cancellable = true)
	private void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, CallbackInfo callbackInfo) {
		if (ModConfig.getData().isEnableSilkSpawners() && SilkedSpawnerManager.isSilkedSpawner(world, pos))
			callbackInfo.cancel();
	}
}