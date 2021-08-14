package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.annotateddi.impl.AnnotatedDI;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.SilkedSpawnerManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
@Mixin(Block.class)
public abstract class BlockMixin {
	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
			config = AnnotatedDI.getInjector().getInstance(ConfigValues.class);
		}

		return config;
	}
	
	@Inject(at = @At(value="HEAD"), method = "onBreak")
	private void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo callbackInfo) {
		if (getConfig().isEnableSilkSpawners()
			&& world instanceof ServerWorld
			&& this.equals(Blocks.SPAWNER)
			&& player.getMainHandStack().isSuitableFor(state)
			&& EnchantmentHelper.getEquipmentLevel(Enchantments.SILK_TOUCH, player) > 0
		) {
			BlockEntity be = world.getBlockEntity(pos);
			if (be == null) {
				FiresSurvivalTweaks.LOGGER.error("Null BE for silked spawner!");
				return;
			}
			ItemStack spawnerStack = new ItemStack(Blocks.SPAWNER);
			NbtCompound dropItemCompound = new NbtCompound();
			MobSpawnerLogic logic = ((MobSpawnerBlockEntity) be).getLogic();
			NbtCompound spawnerNbt = logic.writeNbt(world, pos, new NbtCompound());
			dropItemCompound.put("spawnerdata", spawnerNbt);
			spawnerStack.setNbt(dropItemCompound);
			NbtElement spawnData = spawnerNbt.get("SpawnData");
			if (spawnData instanceof NbtCompound && ((NbtCompound) spawnData).contains("id")) {
				Identifier mobid = new Identifier(((NbtCompound) spawnData).getString("id"));
				spawnerStack.setCustomName(new TranslatableText(Util.createTranslationKey("entity", mobid)).append(" ").append(new TranslatableText("block.minecraft.spawner")));
			}
			world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), spawnerStack));
			SilkedSpawnerManager.addSilkedSpawner((ServerWorld)world, pos);
		}
	}

	@Inject(at = @At(value="HEAD"), method = "onPlaced")
	private void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack, CallbackInfo callbackInfo) {
		if (world instanceof ServerWorld && this.equals(Blocks.SPAWNER)) {
			BlockEntity be = world.getBlockEntity(pos);
			if (be == null) {
				FiresSurvivalTweaks.LOGGER.error("Null BE for placed spawner!");
				return;
			}
			if (itemStack.getNbt() == null) {
				FiresSurvivalTweaks.LOGGER.error("No stack tag for placed spawner!");
				return;
			}
			MobSpawnerLogic logic = ((MobSpawnerBlockEntity) be).getLogic();
			logic.readNbt(world, pos, itemStack.getNbt().getCompound("spawnerdata"));
		}
	}
}
