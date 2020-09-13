package the_fireplace.fst.mixin;

import net.minecraft.block.FallingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FallingBlock.class)
public interface FallingBlockInvoker {
    @Invoker("getFallDelay")
    int invokeGetFallDelay();
}
