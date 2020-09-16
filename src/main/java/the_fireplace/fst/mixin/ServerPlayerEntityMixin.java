package the_fireplace.fst.mixin;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import the_fireplace.fst.FiresSurvivalTweaks;
import the_fireplace.fst.tags.FSTBlockTags;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
	public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(at = @At(value="TAIL"), method = "tick")
	private void tick(CallbackInfo callbackInfo) {
		if(FiresSurvivalTweaks.config.enableFallingBlockTriggering && getServer() != null && getServer().getTicks() % 40 == 3) {
			new Thread(() -> {
				int xOff = random.nextInt(5)-2;
				int yOff = (random.nextBoolean() ? 1 : -1)*(random.nextInt(4)+1);
				int zOff = random.nextInt(5)-2;
				BlockPos targetPos = getBlockPos().add(xOff, yOff, zOff);
				BlockState state = world.getBlockState(targetPos);
				if(state.getBlock() instanceof FallingBlock)
					world.getBlockTickScheduler().schedule(targetPos, state.getBlock(), ((FallingBlockInvoker)state.getBlock()).invokeGetFallDelay());
			}).start();
		}
		if(FiresSurvivalTweaks.config.enableRockslides && getServer() != null && getServer().getTicks() % 13000 == 25)
			rockslide(getBlockPos());
	}

	private void rockslide(BlockPos fieldCenter) {
		int xOff = random.nextInt(7)-3;
		int yOff = (random.nextBoolean() ? 1 : -1)*(random.nextInt(5)+1);
		int zOff = random.nextInt(7)-3;
		BlockPos targetPos = fieldCenter.add(xOff, yOff, zOff);
		for(BlockPos pos: Lists.newArrayList(targetPos, targetPos.north(), targetPos.north(2), targetPos.south(), targetPos.south(2), targetPos.east(), targetPos.east(2), targetPos.west(), targetPos.west(2), targetPos.north().west(), targetPos.north().east(), targetPos.south().west(), targetPos.south().east())) {
			BlockState state = world.getBlockState(pos);
			if(state.getBlock().isIn(FSTBlockTags.FALLING_ROCKS) && FallingBlock.canFallThrough(world.getBlockState(pos.down()))) {
				fall(pos, state);
				if(random.nextInt(3) == 0)
					rockslide(pos);
			}
		}
	}

	private void fall(BlockPos pos, BlockState state) {
		FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, (double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, state);
		fallingBlockEntity.setHurtEntities(true);
		world.spawnEntity(fallingBlockEntity);
	}
}
