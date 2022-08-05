package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.SilkedSpawnerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin extends BaseEntityBlock
{

    private ConfigValues config = null;

    private ConfigValues getConfig() {
        if (config == null) {
            config = FSTConstants.getInjector().getInstance(ConfigValues.class);
        }

        return config;
    }

    protected SpawnerBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "spawnAfterBreak", cancellable = true)
    private void onStacksDropped(BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, CallbackInfo callbackInfo) {
        if (getConfig().isEnableSilkSpawners() && SilkedSpawnerManager.isSilkedSpawner(world, pos)) {
            callbackInfo.cancel();
        }
    }
}
