package the_fireplace.fst.mixin;

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
import the_fireplace.fst.EntityUtils;
import the_fireplace.fst.FiresSurvivalTweaks;
import the_fireplace.fst.tags.FSTBlockTags;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntity {
	@Shadow public abstract int getSize();

	@Shadow protected abstract void setSize(int size, boolean heal);

	protected SlimeEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@SuppressWarnings("ConstantConditions")
	@Inject(at = @At(value="TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		boolean isMagma = (Object)this instanceof MagmaCubeEntity;
		if(!isMagma) {
			if (FiresSurvivalTweaks.config.enableSlimeGrowth && (FiresSurvivalTweaks.config.slimeSizeLimit <= 0 || this.getSize() < FiresSurvivalTweaks.config.slimeSizeLimit)) {
				for (BlockPos pos : EntityUtils.getNearbyBlocks((SlimeEntity) (Object) this)) {
					if(FiresSurvivalTweaks.config.slimeSizeLimit > 0 && this.getSize() >= FiresSurvivalTweaks.config.slimeSizeLimit)
						break;
					BlockState state = world.getBlockState(pos);
					if (state.isIn(FSTBlockTags.SLIME_ABSORBABLES)) {
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						this.setSize(this.getSize() + 1, true);
						world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.HOSTILE, 0.8f, 0.8f+random.nextFloat()*2/5);
					}
				}
			}
		} else {
			if (FiresSurvivalTweaks.config.enableMagmaCubeGrowth && (FiresSurvivalTweaks.config.magmaCubeSizeLimit <= 0 || this.getSize() < FiresSurvivalTweaks.config.magmaCubeSizeLimit)) {
				for (BlockPos pos : EntityUtils.getNearbyBlocks((SlimeEntity) (Object) this)) {
					if(FiresSurvivalTweaks.config.magmaCubeSizeLimit > 0 && this.getSize() >= FiresSurvivalTweaks.config.magmaCubeSizeLimit)
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
	}
}
