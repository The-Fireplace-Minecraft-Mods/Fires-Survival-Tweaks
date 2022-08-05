package dev.the_fireplace.fst.mixin;

import dev.the_fireplace.fst.FSTConstants;
import dev.the_fireplace.fst.domain.config.ConfigValues;
import dev.the_fireplace.fst.logic.SlimeGrowthLogic;
import dev.the_fireplace.fst.tags.FSTBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"LawOfDemeter", "ConstantConditions"})
@Mixin(Slime.class)
public abstract class SlimeEntityMixin extends Mob
{
    @Shadow
    public abstract int getSize();

    @Shadow
    protected abstract void setSize(int size, boolean heal);

    @Shadow
    public abstract void remove(Entity.RemovalReason reason);

    private ConfigValues config = null;

    private ConfigValues getConfig() {
        if (config == null) {
            config = FSTConstants.getInjector().getInstance(ConfigValues.class);
        }

        return config;
    }

    protected SlimeEntityMixin(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo callbackInfo) {
        boolean isMagma = (Object) this instanceof MagmaCube;
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
        return getConfig().isEnableMagmaCubeToSlime() && isInWaterOrRain();
    }

    private void convertToSlime() {
        Slime newCube = new Slime(EntityType.SLIME, level);
        ((SlimeInvoker) newCube).invokeSetSize(getSize(), true);
        newCube.absMoveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        level.addFreshEntity(newCube);
        this.remove(RemovalReason.DISCARDED);
    }

    private void growMagmaIfApplicable() {
        ConfigValues config = getConfig();
        if (config.isEnableMagmaCubeGrowth() && (config.getMagmaCubeSizeLimit() <= 0 || this.getSize() < config.getMagmaCubeSizeLimit())) {
            for (BlockPos pos : SlimeGrowthLogic.getNearbyBlocks((Slime) (Object) this)) {
                if (config.getMagmaCubeSizeLimit() > 0 && this.getSize() >= config.getMagmaCubeSizeLimit()) {
                    break;
                }
                BlockState state = level.getBlockState(pos);
                if (state.is(FSTBlockTags.MAGMA_ABSORBABLES)) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    this.setSize(this.getSize() + 1, true);
                    level.playSound(null, this, SoundEvents.MAGMA_CUBE_SQUISH, SoundSource.HOSTILE, 0.8f, 0.7f + random.nextFloat() / 2);
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
            for (BlockPos pos : SlimeGrowthLogic.getNearbyBlocks((Slime) (Object) this)) {
                BlockState state = level.getBlockState(pos);
                if (config.isEnableSlimeToMagmaCube() && state.is(FSTBlockTags.MAGMA_ABSORBABLES)) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    MagmaCube newCube = new MagmaCube(EntityType.MAGMA_CUBE, level);
                    ((SlimeInvoker) newCube).invokeSetSize(getSize(), true);
                    newCube.absMoveTo(getX(), getY(), getZ(), getYRot(), getXRot());
                    level.addFreshEntity(newCube);
                    this.remove(RemovalReason.DISCARDED);
                    break;
                }
                if (config.getSlimeSizeLimit() > 0 && this.getSize() >= config.getSlimeSizeLimit()) {
                    break;
                }
                if (state.is(FSTBlockTags.SLIME_ABSORBABLES)) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    this.setSize(this.getSize() + 1, true);
                    level.playSound(null, this, SoundEvents.SLIME_SQUISH, SoundSource.HOSTILE, 0.8f, 0.8f + random.nextFloat() * 2 / 5);
                }
            }
        }
    }
}
