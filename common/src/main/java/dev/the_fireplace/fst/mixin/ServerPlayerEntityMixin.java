package dev.the_fireplace.fst.mixin;

import com.mojang.authlib.GameProfile;
import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.CaveinLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin extends Player
{

    private ConfigValues config = null;

    private ConfigValues getConfig() {
        if (config == null) {
            config = FSTConstants.getInjector().getInstance(ConfigValues.class);
        }

        return config;
    }

    public ServerPlayerEntityMixin(Level world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo callbackInfo) {
        if (getConfig().isEnableFallingBlockTriggering()
            && getServer() != null
            && getServer().getTickCount() % 40 == 3
        ) {
            new Thread(() -> {
                int xOff = random.nextInt(5) - 2;
                int yOff = (random.nextBoolean() ? 1 : -1) * (random.nextInt(4) + 1);
                int zOff = random.nextInt(5) - 2;
                BlockPos targetPos = new BlockPos(position()).offset(xOff, yOff, zOff);
                BlockState state = level.getBlockState(targetPos);
                if (state.getBlock() instanceof FallingBlock) {
                    level.getBlockTicks().scheduleTick(targetPos, state.getBlock(), state.getBlock().getTickDelay(level));
                }
            }).start();
        }
        if (getConfig().isEnableCaveins()
            && getServer() != null
            && getServer().getTickCount() % 1300 == 0
            && random.nextInt(1000) == 0
        ) {
            CaveinLogic.cavein(level, new BlockPos(position()), 7, 1);
        }
    }
}
