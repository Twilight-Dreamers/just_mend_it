package ru.twd;

import net.minecraft.util.TypedActionResult;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.api.ModInitializer;



public class Just_mend_it implements ModInitializer {
	@Override
	public void onInitialize() {
		UseItemCallback.EVENT.register((player, world, hand) -> ru.twd.Common.mend(player) ? TypedActionResult.success(ru.twd.Common.get_item(player)) : TypedActionResult.pass(ru.twd.Common.get_item(player)));
	}
}
