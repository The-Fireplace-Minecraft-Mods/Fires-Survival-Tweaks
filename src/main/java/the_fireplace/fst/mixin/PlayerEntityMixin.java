package the_fireplace.fst.mixin;

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
import the_fireplace.fst.FiresSurvivalTweaks;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	@Shadow public abstract float getBlockBreakingSpeed(BlockState block);

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At(value="HEAD"), method = "getBlockBreakingSpeed", cancellable = true)
	private void getBlockBreakingSpeed(BlockState state, CallbackInfoReturnable<Float> callbackInfo) {
		if(FiresSurvivalTweaks.config.enableInfestedBlockBlend && state.getBlock() instanceof InfestedBlock) {
			callbackInfo.setReturnValue(getBlockBreakingSpeed(((InfestedBlock) state.getBlock()).getRegularBlock().getDefaultState()));
		}
	}
}
