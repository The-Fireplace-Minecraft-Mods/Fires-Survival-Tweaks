package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.annotateddi.impl.AnnotatedDI;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.SlimeGrowthLogic;
import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
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

@SuppressWarnings({"LawOfDemeter", "ConstantConditions"})
@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity {
	@Shadow public abstract int getSize();

	@Shadow protected abstract void setSize(int size, boolean heal);

	@Shadow public abstract void remove(Entity.RemovalReason reason);
	
	private ConfigValues config = null;
	private ConfigValues getConfig() {
		if (config == null) {
			config = AnnotatedDI.getInjector().getInstance(ConfigValues.class);
		}

		return config;
	}

	protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		boolean isMagma = (Object)this instanceof MagmaCubeEntity;
		if (isMagma) {
			tickMagmaCube();
		} else {
			tickSlime();
		}
	}

	private void tickMagmaCube() {
		if (canConvertToSlime()) {
			convertToSlime();
		} else {
			growMagmaIfApplicable();
		}
	}

	private boolean canConvertToSlime() {
		return getConfig().isEnableMagmaCubeToSlime() && isTouchingWaterOrRain();
	}

	private void convertToSlime() {
		SlimeEntity newCube = new SlimeEntity(EntityType.SLIME, world);
		((SlimeInvoker) newCube).invokeSetSize(getSize(), true);
		newCube.updatePositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
		world.spawnEntity(newCube);
		this.remove(RemovalReason.DISCARDED);
	}

	private void growMagmaIfApplicable() {
		ConfigValues config = getConfig();
		if (config.isEnableMagmaCubeGrowth() && (config.getMagmaCubeSizeLimit() <= 0 || this.getSize() < config.getMagmaCubeSizeLimit())) {
			for (BlockPos pos : SlimeGrowthLogic.getNearbyBlocks((SlimeEntity) (Object) this)) {
				if (config.getMagmaCubeSizeLimit() > 0 && this.getSize() >= config.getMagmaCubeSizeLimit())
					break;
				BlockState state = world.getBlockState(pos);
				if (state.isIn(FSTBlockTags.MAGMA_ABSORBABLES)) {
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					this.setSize(this.getSize() + 1, true);
					world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MAGMA_CUBE_SQUISH, SoundCategory.HOSTILE, 0.8f, 0.7f+random.nextFloat()/2);
				}
			}
		}
	}

	private void tickSlime() {
		growSlimeIfApplicable();
	}

	private void growSlimeIfApplicable() {
		ConfigValues config = getConfig();
		if (config.isEnableSlimeGrowth() && (config.getSlimeSizeLimit() <= 0 || this.getSize() < config.getSlimeSizeLimit())) {
			for (BlockPos pos : SlimeGrowthLogic.getNearbyBlocks((SlimeEntity) (Object) this)) {
				BlockState state = world.getBlockState(pos);
				if (config.isEnableSlimeToMagmaCube() && state.isIn(FSTBlockTags.MAGMA_ABSORBABLES)) {
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					MagmaCubeEntity newCube = new MagmaCubeEntity(EntityType.MAGMA_CUBE, world);
					((SlimeInvoker)newCube).invokeSetSize(getSize(), true);
					newCube.updatePositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
					world.spawnEntity(newCube);
					this.remove(RemovalReason.DISCARDED);
					break;
				}
				if (config.getSlimeSizeLimit() > 0 && this.getSize() >= config.getSlimeSizeLimit())
					break;
				if (state.isIn(FSTBlockTags.SLIME_ABSORBABLES)) {
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
					this.setSize(this.getSize() + 1, true);
					world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 0.8f, 0.8f+random.nextFloat()*2/5);
				}
			}
		}
	}
}
