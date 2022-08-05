package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.SilkedSpawnerManager;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
@Mixin(Block.class)
public abstract class BlockMixin
{
    private ConfigValues config = null;

    private ConfigValues getConfig() {
        if (config == null) {
            config = FSTConstants.getInjector().getInstance(ConfigValues.class);
        }

        return config;
    }

    @Inject(at = @At("HEAD"), method = "playerWillDestroy")
    private void onBreak(Level world, BlockPos pos, BlockState state, Player player, CallbackInfo callbackInfo) {
        if (getConfig().isEnableSilkSpawners()
            && world instanceof ServerLevel
            && this.equals(Blocks.SPAWNER)
            && player.getMainHandItem().isCorrectToolForDrops(state)
            && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player) > 0
        ) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be == null) {
                FSTConstants.LOGGER.error("Null BE for silked spawner!");
                return;
            }
            ItemStack spawnerStack = new ItemStack(Blocks.SPAWNER);
            CompoundTag dropItemCompound = new CompoundTag();
            BaseSpawner logic = ((SpawnerBlockEntity) be).getSpawner();
            CompoundTag spawnerNbt = logic.save(world, pos, new CompoundTag());
            dropItemCompound.put("spawnerdata", spawnerNbt);
            spawnerStack.setTag(dropItemCompound);
            Tag spawnData = spawnerNbt.get("SpawnData");
            if (spawnData instanceof CompoundTag && ((CompoundTag) spawnData).contains("id")) {
                ResourceLocation mobid = new ResourceLocation(((CompoundTag) spawnData).getString("id"));
                spawnerStack.setHoverName(new TranslatableComponent(Util.makeDescriptionId("entity", mobid)).append(" ").append(new TranslatableComponent("block.minecraft.spawner")));
            }
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), spawnerStack));
            SilkedSpawnerManager.addSilkedSpawner((ServerLevel) world, pos);
        }
    }

    @Inject(at = @At("HEAD"), method = "setPlacedBy")
    private void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo callbackInfo) {
        if (world instanceof ServerLevel && this.equals(Blocks.SPAWNER)) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be == null) {
                FSTConstants.LOGGER.error("Null BE for placed spawner!");
                return;
            }
            if (itemStack.getTag() == null) {
                FSTConstants.LOGGER.error("No stack tag for placed spawner!");
                return;
            }
            BaseSpawner logic = ((SpawnerBlockEntity) be).getSpawner();
            logic.load(world, pos, itemStack.getTag().getCompound("spawnerdata"));
        }
    }
}
