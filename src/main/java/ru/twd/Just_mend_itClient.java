package ru.twd;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;

public class Just_mend_itClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MidnightConfig.init("just_mend_it", Config.class);
	}
}
