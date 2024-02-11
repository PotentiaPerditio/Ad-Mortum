package net.thevoidthaumoturge.admortum;

import com.mojang.blaze3d.platform.InputUtil;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBind;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class AdMortumClient implements ClientModInitializer {

	private static KeyBind keyBind;

	@Override
	public void onInitializeClient(ModContainer mod) {
		keyBind = KeyBindingHelper.registerKeyBinding(new KeyBind(
				"key.admortum.restore",
				InputUtil.Type.KEYSYM,
				82,
				"category.admortum"
		));


		ClientTickEvents.END.register(client -> {
			while(keyBind.wasPressed()) {
				PacketByteBuf buf = PacketByteBufs.create();
				ClientPlayNetworking.send(AdMortum.RESTORE_IDENTIFIER, buf);
			}
		});
	}
}
