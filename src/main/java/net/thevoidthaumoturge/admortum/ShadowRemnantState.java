package net.thevoidthaumoturge.admortum;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.common.advancement.criterion.RequiemCriteria;
import ladysnake.requiem.common.entity.effect.RequiemStatusEffects;
import ladysnake.requiem.common.network.RequiemNetworking;
import ladysnake.requiem.common.remnant.DemonRemnantState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.WorldEvents;

import java.util.List;

public class ShadowRemnantState extends DemonRemnantState {
	public ShadowRemnantState(PlayerEntity player) {
		super(player);
	}

	@Override
	public boolean canSplit(boolean forced) {
		return true;
	}

	@Override
	protected void onRespawnAfterDeath() {
		player.sendMessage(Text.literal("\u00A7k\u00A75Welcome Back"), true);
	}

	@Override
	public boolean canDissociateFrom(MobEntity possessed) {
		return true;
	}

	@Override
	public void serverTick() {
		if (RemnantComponent.get(player).isVagrant()) {
			Predicate<Entity> at = e -> true;
			List<Entity> entities = player.getWorld().getEntitiesByClass(Entity.class, new Box(player.getBlockPos()).expand(3), at);
			for (Entity entity : entities) {
				if (entity instanceof PlayerEntity ee && ee == player) {
					return;
				} else {
					entity.damage((new DamageSource("obscurusKill")).setOutOfWorld().setBypassesArmor().setUnblockable(), Float.MAX_VALUE);
				}
			}
		}
	}
}
