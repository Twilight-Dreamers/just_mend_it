package ru.twd;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.util.TypedActionResult;
import net.fabricmc.api.ClientModInitializer;

public class Just_mend_itClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

			UseItemCallback.EVENT.register((player, world, hand) -> ru.twd.Common.mend(player) ? TypedActionResult.success(ru.twd.Common.get_item(player)) : TypedActionResult.pass(ru.twd.Common.get_item(player)));
	}
}
