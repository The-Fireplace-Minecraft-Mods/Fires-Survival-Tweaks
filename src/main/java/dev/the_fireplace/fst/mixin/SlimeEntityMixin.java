package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.the_fireplace.fst.FiresSurvivalTweaks;
import dev.the_fireplace.fst.logic.SlimeGrowthLogic;
import dev.the_fireplace.fst.tags.FSTBlockTags;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity {
	@Shadow public abstract int getSize();

	@Shadow protected abstract void setSize(int size, boolean heal);

	@Shadow public abstract void remove();

	protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(at = @At(value="TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		boolean isMagma = (Object)this instanceof MagmaCubeEntity;
		if (!isMagma) {
			if (ModConfig.getData().isEnableSlimeGrowth() && (ModConfig.getData().getSlimeSizeLimit() <= 0 || this.getSize() < ModConfig.getData().getSlimeSizeLimit())) {
				for (BlockPos pos : SlimeGrowthLogic.getNearbyBlocks((SlimeEntity) (Object) this)) {
					BlockState state = world.getBlockState(pos);
					if (ModConfig.getData().isEnableSlimeToMagmaCube() && FSTBlockTags.MAGMA_ABSORBABLES.contains(state.getBlock())) {
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						MagmaCubeEntity newCube = new MagmaCubeEntity(EntityType.MAGMA_CUBE, world);
						((SlimeInvoker)newCube).invokeSetSize(getSize(), true);
						newCube.updatePositionAndAngles(x, y, z, yaw, pitch);
						world.spawnEntity(newCube);
						this.remove();
						break;
					}
					if (ModConfig.getData().getSlimeSizeLimit() > 0 && this.getSize() >= ModConfig.getData().getSlimeSizeLimit())
						break;
					if (FSTBlockTags.SLIME_ABSORBABLES.contains(state.getBlock())) {
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						this.setSize(this.getSize() + 1, true);
						world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 0.8f, 0.8f+random.nextFloat()*2/5);
					}
				}
			}
		} else {
			if (ModConfig.getData().isEnableMagmaCubeToSlime() && isTouchingWaterOrRain()) {
				SlimeEntity newCube = new SlimeEntity(EntityType.SLIME, world);
				((SlimeInvoker)newCube).invokeSetSize(getSize(), true);
				newCube.updatePositionAndAngles(x, y, z, yaw, pitch);
				world.spawnEntity(newCube);
				this.remove();
				return;
			}
			if (ModConfig.getData().isEnableMagmaCubeGrowth() && (ModConfig.getData().getMagmaCubeSizeLimit() <= 0 || this.getSize() < ModConfig.getData().getMagmaCubeSizeLimit())) {
				for (BlockPos pos : SlimeGrowthLogic.getNearbyBlocks((SlimeEntity) (Object) this)) {
					if (ModConfig.getData().getMagmaCubeSizeLimit() > 0 && this.getSize() >= ModConfig.getData().getMagmaCubeSizeLimit())
						break;
					BlockState state = world.getBlockState(pos);
					if (FSTBlockTags.MAGMA_ABSORBABLES.contains(state.getBlock())) {
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						this.setSize(this.getSize() + 1, true);
						world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH, SoundCategory.HOSTILE, 0.8f, 0.7f+random.nextFloat()/2);
					}
				}
			}
		}
	}
}
