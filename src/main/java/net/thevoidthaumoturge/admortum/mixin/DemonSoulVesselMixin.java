package net.thevoidthaumoturge.admortum.mixin;

import ladysnake.requiem.common.item.DemonSoulVesselItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.thevoidthaumoturge.admortum.AdMortum;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DemonSoulVesselItem.class)
public class DemonSoulVesselMixin {
	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void onUse(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (player.hasStatusEffect(AdMortum.ETHEREAL_CHAINS)) cir.setReturnValue(new TypedActionResult<>(ActionResult.FAIL, player.getStackInHand(hand)));
	}

}
