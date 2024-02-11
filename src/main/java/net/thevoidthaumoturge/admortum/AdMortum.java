package net.thevoidthaumoturge.admortum;

import com.mojang.brigadier.Command;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.api.v1.remnant.SoulbindingRegistry;
import ladysnake.requiem.common.item.DemonSoulVesselItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.scirave.advitam.AdVitam;
import net.scirave.advitam.registry.AdVitamEffects;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdMortum implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Ad Mortum");
	public static final Identifier RESTORE_IDENTIFIER = new Identifier("admortum", "restore");
	public static final Item SOUL_VESSEL = new DemonSoulVesselItem(AdMortumRPlugin.SHADOW, Formatting.OBFUSCATED, new QuiltItemSettings().maxCount(1).group(ItemGroup.MISC).rarity(Rarity.EPIC), "item.admortum.soul_vessel.tooltip");
	public static final StatusEffect ETHEREAL_CHAINS = new EtherealChainsEffect(StatusEffectType.HARMFUL, 0xE000FF);

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		ServerPlayNetworking.registerGlobalReceiver(RESTORE_IDENTIFIER, (server, player, handler, buf, responseSender) -> {
			server.execute(() -> {
				if(RemnantComponent.get(player).isVagrant() && (RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.SHADOW || RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.OBSCURUS) && !player.getWorld().isClient()) {
					RemnantComponent.get(player).setVagrant(false);
					player.setHealth(player.getMaxHealth());
					player.addStatusEffect(new StatusEffectInstance(AdVitamEffects.SECOND_WIND, 100));
					final double iy = player.getY() + 2;
					while (player.getY() < iy) player.setPosition(player.getX(), player.getY() + 0.001, player.getZ());

				} else if (RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.SHADOW || RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.OBSCURUS) {
					RemnantComponent.get(player).setVagrant(true);
					LOGGER.info(player.getY() + "");
				}
			});
		});
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "soul_vessel"), SOUL_VESSEL);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(mod.metadata().id(), "ethereal_chains"), ETHEREAL_CHAINS);
	}
}
