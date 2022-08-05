package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow
    public abstract float getDestroySpeed(BlockState block);

	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
            config = FSTConstants.getInjector().getInstance(ConfigValues.class);
		}

		return config;
	}

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "getDestroySpeed", cancellable = true)
	private void getBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> callbackInfo) {
		if (getConfig().isEnableInfestedBlockBlend() && state.getBlock() instanceof InfestedBlock) {
            callbackInfo.setReturnValue(getDestroySpeed(((InfestedBlock) state.getBlock()).getHostBlock().defaultBlockState()));
		}
	}
}
