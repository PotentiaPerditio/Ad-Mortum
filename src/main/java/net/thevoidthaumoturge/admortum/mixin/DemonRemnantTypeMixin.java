package net.thevoidthaumoturge.admortum.mixin;

import com.google.common.base.Preconditions;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.common.advancement.criterion.RequiemCriteria;
import ladysnake.requiem.common.entity.effect.AttritionStatusEffect;
import ladysnake.requiem.common.entity.effect.RequiemStatusEffects;
import ladysnake.requiem.common.network.RequiemNetworking;
import ladysnake.requiem.common.remnant.DemonRemnantState;
import ladysnake.requiem.core.remnant.MutableRemnantState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.WorldEvents;
import net.thevoidthaumoturge.admortum.AdMortum;
import net.thevoidthaumoturge.admortum.PentagramBlock;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(DemonRemnantState.class)
public class DemonRemnantTypeMixin extends MutableRemnantState {

	public DemonRemnantTypeMixin(PlayerEntity player) {
		super(player);
	}

	@Override
	protected void regenerateBody(LivingEntity body) {
		Preconditions.checkState(!this.player.world.isClient);
		RequiemNetworking.sendBodyCureMessage(player);
		RemnantComponent.get(player).setVagrant(false);
		RequiemCriteria.TRANSFORMED_POSSESSED_ENTITY.handle((ServerPlayerEntity) player, body, player, true);
		body.remove(Entity.RemovalReason.DISCARDED);
		player.removeStatusEffect(RequiemStatusEffects.ATTRITION);
		player.setHealth(body.getHealth());
		player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
		player.world.syncWorldEvent(null, WorldEvents.ZOMBIE_VILLAGER_CURED, player.getBlockPos(), 0);
	}

	@Override
	protected void onRespawnAfterDeath() {
		AttritionStatusEffect.apply(player);
	}

	@Override
	public void serverTick() {
		if (player.getWorld().getBlockState(player.getBlockPos().down()).contains(PentagramBlock.SOULS)) {
			if (player.getWorld().getBlockState(player.getBlockPos().down()).get(PentagramBlock.SOULS) == 10) {
				player.addStatusEffect(new StatusEffectInstance(AdMortum.ETHEREAL_CHAINS, 20));
			}
		}
	}
}
