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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
    @Inject(at = @At("HEAD"), method = "getPickStack", cancellable = true)
    private void getPickStack(BlockView world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> callbackInfo) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) {
            FiresSurvivalTweaks.LOGGER.error("Null BE for silked spawner!");
            return;
        }
        ItemStack spawnerStack = new ItemStack(Blocks.SPAWNER);
        CompoundTag dropItemCompound = new CompoundTag();
        MobSpawnerLogic logic = ((MobSpawnerBlockEntity) be).getLogic();
        CompoundTag spawnerNbt = logic.serialize(new CompoundTag());
        dropItemCompound.put("spawnerdata", spawnerNbt);
        spawnerStack.setTag(dropItemCompound);
        Tag spawnData = spawnerNbt.get("SpawnData");
        if (spawnData instanceof CompoundTag && ((CompoundTag) spawnData).contains("id")) {
            Identifier mobid = new Identifier(((CompoundTag) spawnData).getString("id"));
            spawnerStack.setCustomName(new TranslatableText(Util.createTranslationKey("entity", mobid)).append(" ").append(new TranslatableText("block.minecraft.spawner")));
        }
        callbackInfo.setReturnValue(spawnerStack);
    }
}
