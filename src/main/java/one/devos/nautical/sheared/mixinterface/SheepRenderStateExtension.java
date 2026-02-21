package one.devos.nautical.sheared.mixinterface;

import java.util.UUID;

import org.jspecify.annotations.Nullable;

public interface SheepRenderStateExtension {
	@Nullable
	UUID sheared$getUuid();

	float sheared$getShearProgress();

	void sheared$setUuid(@Nullable UUID uuid);

	void sheared$setShearProgress(float shearProgress);
}
