package the_fireplace.fst;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import the_fireplace.fst.config.ConfigValues;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * @author The_Fireplace
 */
@Mod.EventBusSubscriber(modid=FiresSurvivalTweaks.MODID)
public class CommonEvents {
	private static final Random rand = new Random();
	//public static final Method setSlimeSize = ReflectionHelper.findMethod(EntityMagmaCube.class, "setSlimeSize", "func_70799_a", int.class, boolean.class);

	@SubscribeEvent
	public static void blockInteract(PlayerInteractEvent.RightClickBlock event) {
		if (ConfigValues.ENABLE_BPB)
			if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.BLAZE_POWDER) {
				if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockNetherWart && event.getWorld().getBlockState(event.getPos().down()).getBlock() == Blocks.SOUL_SAND) {
					if (!MinecraftForge.EVENT_BUS.post(new BlockEvent.CropGrowEvent.Pre(event.getWorld(), event.getPos(), event.getWorld().getBlockState(event.getPos())))) {
						IBlockState preState = event.getWorld().getBlockState(event.getPos());
						if (applyBlazePowder(event.getItemStack(), event.getWorld(), event.getPos(), event.getEntityPlayer())) {
							if (!event.getWorld().isRemote)
								event.getWorld().playEvent(2005, event.getPos(), 0);
							MinecraftForge.EVENT_BUS.post(new BlockEvent.CropGrowEvent.Post(event.getWorld(), event.getPos(), preState, event.getWorld().getBlockState(event.getPos())));
						}
					}
				}
			}
	}

	@SubscribeEvent
	public static void livingUpdate(LivingEvent.LivingUpdateEvent event){
		World world = event.getEntityLiving().world;
		if(world.isRemote || !(event.getEntityLiving() instanceof EntitySlime) || world.getTotalWorldTime() % 20 != 0 || (!ConfigValues.ENABLE_S2M && !ConfigValues.ENABLE_MCG))
			return;
		EntitySlime slime = (EntitySlime) event.getEntityLiving();

		if(ConfigValues.ENABLE_S2M && !(slime instanceof EntityMagmaCube)){
			if(world.getBlockState(slime.getPosition().down()).getBlock() == Blocks.MAGMA){
				EntityMagmaCube newCube = new EntityMagmaCube(world);
				//try {
					//setSlimeSize.invoke(newCube, slime.getSlimeSize(), false);
					newCube.setSlimeSize(slime.getSlimeSize(), false);
					newCube.setPositionAndRotation(slime.posX, slime.posY, slime.posZ, slime.rotationYaw, slime.rotationPitch);
					world.removeEntity(slime);
					world.spawnEntity(newCube);
					newCube.setPositionAndRotation(slime.posX, slime.posY, slime.posZ, slime.rotationYaw, slime.rotationPitch);
					world.setBlockToAir(slime.getPosition().down());
				//}catch(Exception e){
					//e.printStackTrace();
				//}
			}
		}else if(ConfigValues.ENABLE_MCG && slime instanceof EntityMagmaCube && (ConfigValues.MCG_LIMIT == 0 || slime.getSlimeSize() < ConfigValues.MCG_LIMIT)){
			if(world.getBlockState(slime.getPosition().down()).getBlock() == Blocks.MAGMA) {
				EntityMagmaCube magmaCube = (EntityMagmaCube) slime;
				//try {
					//setSlimeSize.invoke(magmaCube, magmaCube.getSlimeSize() + 1, false);
					magmaCube.setSlimeSize(magmaCube.getSlimeSize()+1, false);
					world.setBlockToAir(magmaCube.getPosition().down());
				//} catch (Exception e) {
					//e.printStackTrace();
				//}
			}
		}
	}

	@SubscribeEvent
	public static void playerUpdate(TickEvent.PlayerTickEvent event){
		if((ConfigValues.ENABLE_RRS || ConfigValues.ENABLE_RFB) && !event.player.world.isRemote && event.player.world.getWorldTime() % 40 == 0){
			BlockPos pos = new BlockPos(event.player).add(rand.nextInt(10)-5, rand.nextInt(12)-6, rand.nextInt(10)-5);
			IBlockState state = event.player.world.getBlockState(pos);
			IBlockState downState = event.player.world.getBlockState(pos.down());
			Block block = state.getBlock();
			if(ConfigValues.ENABLE_RFB && block instanceof BlockFalling)
				event.player.world.scheduleUpdate(pos, block, 4);
			else if(ConfigValues.ENABLE_RRS && rand.nextInt(1100) == 0 && (block == Blocks.STONE || block == Blocks.NETHERRACK) && (event.player.world.isAirBlock(pos.down()) || BlockFalling.canFallThrough(downState))){
				EntityFallingBlock stone = new EntityFallingBlock(event.player.world, pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, state);
				event.player.world.spawnEntity(stone);
				for(int i=0;i< EnumFacing.values().length;i++){
					if(rand.nextInt(2) == 0){
						BlockPos npos = pos.offset(EnumFacing.values()[i]);
						IBlockState nstate = event.player.world.getBlockState(npos);
						IBlockState nDownState = event.player.world.getBlockState(npos.down());
						if((nstate.getBlock() == Blocks.STONE || nstate.getBlock() == Blocks.NETHERRACK) && (event.player.world.isAirBlock(npos.down()) || BlockFalling.canFallThrough(nDownState))) {
							EntityFallingBlock stone2 = new EntityFallingBlock(event.player.world, npos.getX(), npos.getY(), npos.getZ(), nstate);
							event.player.world.spawnEntity(stone2);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void cropGrow(BlockEvent.CropGrowEvent.Post event) {
		if (ConfigValues.ENABLE_F2S && !event.getWorld().isRemote)
			if (event.getWorld().getBlockState(event.getPos().down()).getBlock() instanceof BlockFarmland) {
				if (event.getWorld().rand.nextInt(100) == 0) {
					event.getWorld().setBlockState(event.getPos().down(), Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
				}
			}
	}

	@SubscribeEvent
	public static void bonemealEvent(BonemealEvent event) {
		if (ConfigValues.ENABLE_F2S && !event.getWorld().isRemote && event.getResult() == Event.Result.DEFAULT)
			if (event.getWorld().getBlockState(event.getPos().down()).getBlock() instanceof BlockFarmland) {
				if (event.getWorld().rand.nextInt(25) == 0) {
					event.getWorld().setBlockState(event.getPos().down(), Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND));
				}
			}
	}

	@SubscribeEvent
	public static void breakSpeed(PlayerEvent.BreakSpeed event) {
		if (ConfigValues.ENABLE_SEB && event.getState().getBlock() == Blocks.MONSTER_EGG) {
			if (Blocks.MONSTER_EGG.getBlockHardness(null, null, null) != Blocks.STONE.getBlockHardness(null, null, null))
				Blocks.MONSTER_EGG.setHardness(Blocks.STONE.getBlockHardness(null, null, null));
			event.setNewSpeed(event.getEntityPlayer().getDigSpeed(Blocks.STONE.getDefaultState(), event.getPos()));
		}
	}

	public static boolean applyBlazePowder(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player) {
		IBlockState iblockstate = worldIn.getBlockState(target);

		if (iblockstate.getBlock() instanceof BlockNetherWart) {
			int currage = iblockstate.getValue(BlockNetherWart.AGE);
			if (currage < 3) {
				if (!worldIn.isRemote) {
					iblockstate = iblockstate.getBlock().getDefaultState().withProperty(BlockNetherWart.AGE, worldIn.rand.nextInt(3 - currage) + currage + 1);
					worldIn.setBlockState(target, iblockstate);
					if (!player.capabilities.isCreativeMode)
						stack.shrink(1);
				}

				return true;
			}
		}

		return false;
	}
}
