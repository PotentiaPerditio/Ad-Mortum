package net.thevoidthaumoturge.admortum;

import com.mojang.brigadier.Command;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import ladysnake.requiem.api.v1.remnant.SoulbindingRegistry;
import ladysnake.requiem.common.item.DemonSoulVesselItem;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import net.scirave.advitam.AdVitam;
import net.scirave.advitam.registry.AdVitamEffects;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class AdMortum implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Ad Mortum");
	public static final Identifier RESTORE_IDENTIFIER = new Identifier("admortum", "restore");
	public static final Item SOUL_VESSEL = new DemonSoulVesselItem(AdMortumRPlugin.SHADOW, Formatting.OBFUSCATED, new QuiltItemSettings().maxCount(1).group(ItemGroup.MISC).rarity(Rarity.EPIC), "item.admortum.soul_vessel.tooltip");
	public static final StatusEffect ETHEREAL_CHAINS = new EtherealChainsEffect(StatusEffectType.HARMFUL, 0xE000FF);
	public static final Block PENTAGRAM = new PentagramBlock(QuiltBlockSettings.of(new Material(MapColor.BRIGHT_RED, false, false, false, false, false, false, PistonBehavior.DESTROY)));
	public static final Item DAGGER = new DaggerItem(new QuiltItemSettings().group(ItemGroup.MISC));
	public static final Item CRUCIFIX = new CrucifixItem(new QuiltItemSettings().group(ItemGroup.MISC));
	public static final Item SOUL_SHARD = new SoulShardItem(new QuiltItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC));
	public static final Block AWAKENED_TACHYLITE = new AwakenedTachyliteBlock(QuiltBlockSettings.of(new Material(MapColor.BRIGHT_TEAL, false, true, true, true, false, false, PistonBehavior.BLOCK)).dropsNothing().allowsSpawning((blockState, blockView, blockPos, object) -> false).strength(-1, 3600000).luminance(15));
	public static final Item AWAKENED_TACHYLITE_ITEM = new BlockItem(AWAKENED_TACHYLITE, new QuiltItemSettings().group(ItemGroup.MISC));

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
		ServerPlayNetworking.registerGlobalReceiver(RESTORE_IDENTIFIER, (server, player, handler, buf, responseSender) -> {
			server.execute(() -> {
				if(RemnantComponent.get(player).isVagrant() && (RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.SHADOW || RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.OBSCURUS) && !player.getWorld().isClient()) {
					RemnantComponent.get(player).setVagrant(false);
					player.setHealth(player.getMaxHealth());
					player.addStatusEffect(new StatusEffectInstance(AdVitamEffects.SECOND_WIND, 100));
					List<LivingEntity> i = player.getWorld().getEntitiesByClass(LivingEntity.class, new Box(player.getBlockPos()).expand(10), e -> true);
					for (LivingEntity entity : i) {
						if (entity instanceof ServerPlayerEntity p && p == player) return;
						entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 5, true, false, false));
					}
				} else if (RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.SHADOW || RemnantComponent.get(player).getRemnantType() == AdMortumRPlugin.OBSCURUS) {
					RemnantComponent.get(player).setVagrant(true);
					LOGGER.info(player.getY() + "");
				}
			});
		});
		ServerPlayNetworking.registerGlobalReceiver(new Identifier("admortum", "sacrifice"), (server, player, handler, buf, responseSender) -> {
			server.execute(() -> {
				ItemStack s = new ItemStack(SOUL_SHARD);
				s.getOrCreateNbt().putUuid("player", player.getUuid());
				s.getNbt().putString("playername", player.getName().getString());
				player.getWorld().spawnEntity(new ItemEntity(player.getWorld(), player.getX(), player.getY(), player.getZ(), s));
				server.getPlayerManager().getUserBanList().add(new BannedPlayerEntry(player.getGameProfile(), (Date) null, player.getName().getString(), (Date) null, "Sacrificed"));
				player.networkHandler.disconnect(Text.literal("You have given up your soul"));
			});
		});
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "soul_vessel"), SOUL_VESSEL);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "dagger"), DAGGER);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "crucifix"), CRUCIFIX);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "soul_shard"), SOUL_SHARD);
		Registry.register(Registry.ITEM, new Identifier(mod.metadata().id(), "awakened_tachylite"), AWAKENED_TACHYLITE_ITEM);
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "awakened_tachylite"), AWAKENED_TACHYLITE);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(mod.metadata().id(), "ethereal_chains"), ETHEREAL_CHAINS);
		Registry.register(Registry.BLOCK, new Identifier(mod.metadata().id(), "pentagram"), PENTAGRAM);
	}
}
