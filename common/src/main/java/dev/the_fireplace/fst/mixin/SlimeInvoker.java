package dev.the_fireplace.fst.mixin;

import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Slime.class)
public interface SlimeInvoker {
    @Invoker("setSize")
    void invokeSetSize(int size, boolean heal);
}
