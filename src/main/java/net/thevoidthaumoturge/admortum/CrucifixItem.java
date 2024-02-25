package net.thevoidthaumoturge.admortum;

import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.common.remnant.RemnantTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.dedicated.command.BanCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Date;

public class CrucifixItem extends Item {
	public CrucifixItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		for (ServerPlayerEntity player : world.getEntitiesByClass(ServerPlayerEntity.class, new Box(user.getBlockPos()).expand(5), e -> true)) {
			if (RemnantComponent.get(player).getRemnantType() == RemnantTypes.REMNANT) {
				player.getServer().getPlayerManager().getUserBanList().add(new BannedPlayerEntry(user.getGameProfile(), (Date) null, user.getName().getString(), (Date) null, "Exorcism"));
				player.networkHandler.disconnect(Text.literal("You Have Been Exorcised"));
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
}
