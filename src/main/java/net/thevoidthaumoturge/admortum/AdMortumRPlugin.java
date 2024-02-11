package net.thevoidthaumoturge.admortum;

import ladysnake.requiem.api.v1.RequiemPlugin;
import ladysnake.requiem.api.v1.remnant.RemnantType;
import ladysnake.requiem.api.v1.remnant.SoulbindingRegistry;
import ladysnake.requiem.common.remnant.SimpleRemnantType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AdMortumRPlugin implements RequiemPlugin {
	public static final RemnantType SHADOW = new SimpleRemnantType(ShadowRemnantState::new, true, "remnant.admortum.sentence", () -> AdMortum.SOUL_VESSEL);
	public static final RemnantType OBSCURUS = new SimpleRemnantType(ObscurusRemnantState::new, true, null, null);

	@Override
	public void registerRemnantStates(Registry<RemnantType> registry) {
		Registry.register(registry, new Identifier("admortum", "shadow"), SHADOW);
		Registry.register(registry, new Identifier("admortum", "obscurus"), OBSCURUS);
	}

	@Override
	public void registerSoulBindings(SoulbindingRegistry registry) {
		registry.registerSoulbound(AdMortum.ETHEREAL_CHAINS);
	}
}
