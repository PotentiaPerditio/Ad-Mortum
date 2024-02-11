package net.thevoidthaumoturge.admortum;

import ladysnake.requiem.api.v1.remnant.RemnantState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class ObscurusRemnantState extends ShadowRemnantState{
	public ObscurusRemnantState(PlayerEntity player) {
		super(player);
	}

	@Override
	public void setup(RemnantState oldHandler) {
		player.addStatusEffect(new StatusEffectInstance(AdMortum.ETHEREAL_CHAINS, 300, 0, true, true));
		super.setup(oldHandler);
	}
}
