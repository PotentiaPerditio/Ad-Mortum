package net.thevoidthaumoturge.admortum;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Function;

public class PentagramBlock extends Block {
	public static final BooleanProperty LIT = BooleanProperty.of("lit");
	public static final IntProperty SOULS = IntProperty.of("souls", 0, 10);

	public PentagramBlock(Settings settings) {
		super(settings);
		setDefaultState(this.getStateManager().getDefaultState().with(LIT, false).with(SOULS, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
		builder.add(SOULS);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getInventory().getMainHandStack().getItem() == Items.FLINT_AND_STEEL && !state.get(LIT)) {
			world.setBlockState(pos, state.with(LIT, true));
			player.getInventory().getMainHandStack().setCount(0);
		} else if (player.getInventory().getMainHandStack().getItem() == Items.WATER_BUCKET && state.get(LIT)) {
			world.setBlockState(pos, state.with(LIT, false));
			player.getInventory().getMainHandStack().setCount(0);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (state.get(LIT)) {
			entity.damage(new DamageSource("absorbed").setUnblockable().setBypassesArmor().setOutOfWorld(), Float.MAX_VALUE);
			if(state.get(SOULS) < 10) world.setBlockState(pos, state.with(SOULS, state.get(SOULS) + 1));
		}
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return state.get(LIT);
	}
}
