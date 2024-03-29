package net.thevoidthaumoturge.admortum;

import com.mojang.authlib.GameProfile;
import ladysnake.requiem.common.block.RequiemBlocks;
import ladysnake.requiem.common.sound.RequiemSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.dedicated.command.BanCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulShardItem extends Item {
	public SoulShardItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getPlayer().getStackInHand(context.getHand()).getOrCreateNbt().contains("player")) {
			return ActionResult.FAIL;
		}
		Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();
		if (block == RequiemBlocks.CHISELED_TACHYLITE) {
			if (context.getWorld().isClient()) return ActionResult.SUCCESS;
			context.getWorld().setBlockState(context.getBlockPos(), AdMortum.AWAKENED_TACHYLITE.getDefaultState());
			for (ServerPlayerEntity p : context.getWorld().getServer().getPlayerManager().getPlayerList()) {
				p.playSound(RequiemSoundEvents.BLOCK_OBELISK_ACTIVATE, SoundCategory.MASTER, 10000, 1);
			}
			context.getPlayer().getStackInHand(context.getHand()).decrement(1);
			return ActionResult.CONSUME;
		}

		return super.useOnBlock(context);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) world.getServer().getPlayerManager().getUserBanList().remove(new GameProfile(user.getStackInHand(hand).getOrCreateNbt().getUuid("player"), user.getStackInHand(hand).getNbt().getString("playername")));
		if (!user.isCreative()) user.getStackInHand(hand).decrement(1);
		if (world.isClient()) return TypedActionResult.success(user.getStackInHand(hand));
		return TypedActionResult.consume(user.getStackInHand(hand));
	}

	@Override
	public Text getName(ItemStack stack) {
		return stack.getOrCreateNbt().contains("playername") ? Text.literal("The soul of " + stack.getNbt().getString("playername")) : Text.literal("Cheater!");
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (stack.getOrCreateNbt().contains("cheated") && stack.getNbt().getBoolean("cheated")) tooltip.add(Text.literal("You cheated!"));
	}
}
