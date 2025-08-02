package one.devos.nautical.sheared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;

public class ShearedClient implements ClientModInitializer {
	public static final String ID = "sheared";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitializeClient() {
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}
