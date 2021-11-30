package dev.the_fireplace.fst.mixin;

import com.mojang.authlib.GameProfile;
import dev.the_fireplace.annotateddi.impl.AnnotatedDI;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.CaveinLogic;
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

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
			config = AnnotatedDI.getInjector().getInstance(ConfigValues.class);
		}

		return config;
	}

	protected ServerPlayerEntityMixin(World world, GameProfile profile) {
		super(world, profile);
	}

	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		if (getConfig().isEnableFallingBlockTriggering()
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
					world.getBlockTickScheduler().schedule(targetPos, state.getBlock(), state.getBlock().getTickRate(world));
			}).start();
		}
		if (getConfig().isEnableCaveins()
			&& getServer() != null
			&& getServer().getTicks() % 1300 == 0
			&& random.nextInt(1000) == 0
		) {
			CaveinLogic.cavein(world, getBlockPos(), 7, 1);
		}
	}
}
