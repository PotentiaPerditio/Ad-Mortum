package net.thevoidthaumoturge.admortum;

import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.api.v1.remnant.StickyStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;

public class EtherealChainsEffect extends StatusEffect implements StickyStatusEffect {
	public EtherealChainsEffect(StatusEffectType type, int color) {
		super(type, color);
	}

	@Override
	public boolean shouldStick(LivingEntity entity) {
		return false;
	}

	@Override
	public boolean shouldFreezeDuration(LivingEntity entity) {
		if (entity instanceof PlayerEntity player) {
			return RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.OBSCURUS;
		}
		return false;
	}
}
