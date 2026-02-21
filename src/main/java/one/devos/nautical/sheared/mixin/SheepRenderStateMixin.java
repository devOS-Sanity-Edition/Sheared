package one.devos.nautical.sheared.mixin;

import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.client.renderer.entity.state.SheepRenderState;
import one.devos.nautical.sheared.mixinterface.SheepRenderStateExtension;

@Mixin(SheepRenderState.class)
abstract class SheepRenderStateMixin implements SheepRenderStateExtension {
	@Unique
	@Nullable
	private UUID uuid;

	@Unique
	private float shearProgress;

	@Override
	@Nullable
	public UUID sheared$getUuid() {
		return uuid;
	}

	@Override
	public float sheared$getShearProgress() {
		return shearProgress;
	}

	@Override
	public void sheared$setUuid(@Nullable UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public void sheared$setShearProgress(float shearProgress) {
		this.shearProgress = shearProgress;
	}
}
