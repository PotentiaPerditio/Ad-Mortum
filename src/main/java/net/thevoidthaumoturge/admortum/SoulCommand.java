package net.thevoidthaumoturge.admortum;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class SoulCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandBuildContext context, CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(CommandManager.literal("soul").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2)).then(CommandManager.argument("player", EntityArgumentType.player()).executes(SoulCommand::run)));
	}

	private static int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
		ItemStack s = new ItemStack(AdMortum.SOUL_SHARD);
		s.getOrCreateNbt().putUuid("player", EntityArgumentType.getPlayer(ctx, "player").getUuid());
		s.getOrCreateNbt().putString("playername", EntityArgumentType.getPlayer(ctx, "player").getName().getString());
		s.getOrCreateNbt().putBoolean("cheated", true);
		ctx.getSource().getPlayer().giveItemStack(s);
		return 1;
	}
}
