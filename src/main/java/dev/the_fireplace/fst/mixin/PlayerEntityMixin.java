package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.annotateddi.impl.AnnotatedDI;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow public abstract float getBlockBreakingSpeed(BlockState block);

	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
			config = AnnotatedDI.getInjector().getInstance(ConfigValues.class);
		}

		return config;
	}

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At(value="HEAD"), method = "getBlockBreakingSpeed", cancellable = true)
	private void getBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> callbackInfo) {
		if (getConfig().isEnableInfestedBlockBlend() && state.getBlock() instanceof InfestedBlock) {
			callbackInfo.setReturnValue(getBlockBreakingSpeed(((InfestedBlock) state.getBlock()).getRegularBlock().getDefaultState()));
		}
	}
}
