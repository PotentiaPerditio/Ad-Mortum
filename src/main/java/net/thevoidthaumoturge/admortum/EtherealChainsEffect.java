package net.thevoidthaumoturge.admortum;

import ladysnake.requiem.api.v1.RequiemPlugin;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.api.v1.remnant.RemnantType;
import ladysnake.requiem.api.v1.remnant.StickyStatusEffect;
import ladysnake.requiem.common.VanillaRequiemPlugin;
import ladysnake.requiem.common.remnant.RemnantTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

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

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity instanceof PlayerEntity p) {
			RemnantComponent r = RemnantComponent.get(p);
			if (r.getRemnantType() == RemnantTypes.REMNANT) {
				p.setVelocity(0, 0, 0);
			}
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}
