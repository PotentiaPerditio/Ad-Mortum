package net.thevoidthaumoturge.admortum;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
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

public class PentagramBlock extends Block {
	public static final IntProperty LIT = IntProperty.of("lit", 0, 5);

	public PentagramBlock(Settings settings) {
		super(settings);
		setDefaultState(this.getStateManager().getDefaultState().with(LIT, 0));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getInventory().getMainHandStack().getItem() == Items.FLINT_AND_STEEL && state.get(LIT) < 6) {
			state.with(LIT, state.get(LIT) + 1);
			player.getInventory().getMainHandStack().setDamage(player.getInventory().getMainHandStack().getDamage() + 1);
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(-1.0, 0.0, -1.0, 2.0, 1.0, 2.0);
	}
}
