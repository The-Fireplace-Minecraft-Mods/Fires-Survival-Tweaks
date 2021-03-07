package dev.the_fireplace.fst.mixin;

import com.mojang.authlib.GameProfile;
import dev.the_fireplace.fst.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.logic.CaveinLogic;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(at = @At(value="TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		if (ModConfig.getData().isEnableFallingBlockTriggering()
			&& getServer() != null
			&& getServer().getTicks() % 40 == 3
		) {
			new Thread(() -> {
				int xOff = random.nextInt(5)-2;
				int yOff = (random.nextBoolean() ? 1 : -1)*(random.nextInt(4)+1);
				int zOff = random.nextInt(5)-2;
				BlockPos targetPos = getBlockPos().add(xOff, yOff, zOff);
				BlockState state = world.getBlockState(targetPos);
				if (state.getBlock() instanceof FallingBlock)
					world.getBlockTickScheduler().schedule(targetPos, state.getBlock(), ((FallingBlockInvoker)state.getBlock()).invokeGetFallDelay());
			}).start();
		}
		if (ModConfig.getData().isEnableCaveins()
			&& getServer() != null
			&& getServer().getTicks() % 1300 == 0
			&& random.nextInt(1000) == 0
		) {
			CaveinLogic.cavein(world, getBlockPos(), 7, 1);
		}
	}
}
