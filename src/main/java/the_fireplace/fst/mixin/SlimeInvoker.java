package the_fireplace.fst.mixin;

import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SlimeEntity.class)
public interface SlimeInvoker {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
