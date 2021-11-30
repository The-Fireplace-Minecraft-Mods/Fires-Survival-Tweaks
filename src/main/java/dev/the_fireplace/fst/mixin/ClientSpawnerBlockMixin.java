package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FiresSurvivalTweaks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SpawnerBlock.class)
public abstract class ClientSpawnerBlockMixin extends BlockWithEntity {
    protected ClientSpawnerBlockMixin(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("DuplicatedCode")
    @Inject(at = @At(value="HEAD"), method = "getPickStack", cancellable = true)
    private void getPickStack(BlockView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> callbackInfo) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) {
            FiresSurvivalTweaks.LOGGER.error("Null BE for silked spawner!");
            return;
        }
        ItemStack spawnerStack = new ItemStack(Blocks.SPAWNER);
        NbtCompound dropItemCompound = new NbtCompound();
        MobSpawnerLogic logic = ((MobSpawnerBlockEntity) be).getLogic();
        NbtCompound spawnerNbt = logic.writeNbt(new NbtCompound());
        dropItemCompound.put("spawnerdata", spawnerNbt);
        spawnerStack.setNbt(dropItemCompound);
        NbtElement spawnData = spawnerNbt.get("SpawnData");
        if (spawnData instanceof NbtCompound && ((NbtCompound) spawnData).contains("id")) {
            Identifier mobid = new Identifier(((NbtCompound) spawnData).getString("id"));
            spawnerStack.setCustomName(new TranslatableText(Util.createTranslationKey("entity", mobid)).append(" ").append(new TranslatableText("block.minecraft.spawner")));
        }
        callbackInfo.setReturnValue(spawnerStack);
    }
}
