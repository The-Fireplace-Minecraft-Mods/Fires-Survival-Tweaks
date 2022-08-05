package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FSTConstants;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnerBlock.class)
public abstract class ClientSpawnerBlockMixin extends BaseEntityBlock
{
    protected ClientSpawnerBlockMixin(Properties settings) {
        super(settings);
    }

    @SuppressWarnings("DuplicatedCode")
    @Inject(at = @At("HEAD"), method = "getCloneItemStack", cancellable = true)
    private void getPickStack(BlockGetter world, BlockPos pos, BlockState state, CallbackInfoReturnable<ItemStack> callbackInfo) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be == null) {
            FSTConstants.LOGGER.error("Null BE for silked spawner!");
            return;
        }
        ItemStack spawnerStack = new ItemStack(Blocks.SPAWNER);
        CompoundTag dropItemCompound = new CompoundTag();
        Level level = null;
        if (world instanceof Level) {
            level = (Level) world;
        }
        BaseSpawner logic = ((SpawnerBlockEntity) be).getSpawner();
        CompoundTag spawnerNbt = logic.save(level, pos, new CompoundTag());
        dropItemCompound.put("spawnerdata", spawnerNbt);
        spawnerStack.setTag(dropItemCompound);
        Tag spawnData = spawnerNbt.get("SpawnData");
        if (spawnData instanceof CompoundTag && ((CompoundTag) spawnData).contains("id")) {
            ResourceLocation mobid = new ResourceLocation(((CompoundTag) spawnData).getString("id"));
            spawnerStack.setHoverName(new TranslatableComponent(Util.makeDescriptionId("entity", mobid)).append(" ").append(new TranslatableComponent("block.minecraft.spawner")));
        }
        callbackInfo.setReturnValue(spawnerStack);
    }
}
