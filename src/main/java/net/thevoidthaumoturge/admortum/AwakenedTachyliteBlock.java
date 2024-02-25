package net.thevoidthaumoturge.admortum;

import ladysnake.requiem.common.block.RequiemBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AwakenedTachyliteBlock extends Block {
	public AwakenedTachyliteBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof ServerPlayerEntity p) {
			p.giveItemStack(new ItemStack(AdMortum.CRUCIFIX));
			world.getServer().getPlayerManager().getUserBanList().add(new BannedPlayerEntry(p.getGameProfile(), (Date) null, "Awakened Tachylite", (Date) null, "Sacrifice"));
			world.setBlockState(pos, RequiemBlocks.CHISELED_TACHYLITE.getDefaultState());
		}
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		if (!world.isClient()) {
			ServerWorld w = (ServerWorld) world;

			for (Entity e : w.getEntitiesByClass(Entity.class, new Box(pos).expand(100), e -> true)) {
				e.damage(DamageSource.WITHER, Float.MAX_VALUE);
			}

			int radius = 100;
			for (int x = -radius; x <= radius; x++) {
				for (int y = -radius; y <= radius; y++) {
					for (int z = -radius; z <= radius; z++) {
						BlockPos a = pos.add(x, y, z);

						if (pos.getManhattanDistance(a) <= radius) {
							((ServerWorld) world).setBlockState(a, Blocks.AIR.getDefaultState());
						}
					}
				}
			}

			w.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, Float.MAX_VALUE, 1, true);
		}
	}
}
