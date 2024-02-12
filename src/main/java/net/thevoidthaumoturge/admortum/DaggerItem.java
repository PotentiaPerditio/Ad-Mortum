package net.thevoidthaumoturge.admortum;

import net.minecraft.block.BlockState;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DaggerItem extends Item {
	public DaggerItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.getMaxHealth() > user.getHealth()) {user.damage(DamageSource.GENERIC, user.getHealth()); return super.use(world, user, hand);}
		user.damage(DamageSource.GENERIC, user.getMaxHealth() - 1);
		world.setBlockState(user.getBlockPos(), AdMortum.PENTAGRAM.getDefaultState());
		return super.use(world, user, hand);
	}
}
