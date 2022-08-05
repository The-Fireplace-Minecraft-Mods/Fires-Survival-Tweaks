package dev.the_fireplace.fst.eventhandlers;

import dev.the_fireplace.fst.logic.NetherCropGrowthHandler;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.inject.Inject;

public final class UseBlock
{
    private final NetherCropGrowthHandler netherCropGrowthHandler;

    @Inject
    public UseBlock(NetherCropGrowthHandler netherCropGrowthHandler) {
        this.netherCropGrowthHandler = netherCropGrowthHandler;
    }

    @SubscribeEvent
    public void onUseBlock(PlayerInteractEvent.RightClickBlock event) {
        InteractionResult result = netherCropGrowthHandler.interact(
            event.getPlayer(),
            event.getWorld(),
            event.getHand(),
            event.getHitVec()
        );

        if (result != InteractionResult.PASS) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }
}
