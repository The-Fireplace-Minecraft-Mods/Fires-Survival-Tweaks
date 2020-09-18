package the_fireplace.fst.mixin;

import net.minecraft.entity.mob.MagmaCubeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MagmaCubeEntity.class)
public interface MagmaCubeInvoker {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
