package one.devos.nautical.sheared.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.sheep.Sheep;
import one.devos.nautical.sheared.mixinterface.SheepExtension;
import one.devos.nautical.sheared.mixinterface.SheepRenderStateExtension;

@Mixin(SheepRenderer.class)
abstract class SheepRendererMixin {
	@Inject(method = "extractRenderState", at = @At("RETURN"))
	private void onReturnExtractRenderState(Sheep sheep, SheepRenderState renderState, float partialTick, CallbackInfo ci) {
		SheepExtension extension = ((SheepExtension) sheep);
		float shearProgress = extension.sheared$getShearProgress();
		float shearProgressO = extension.sheared$getShearProgressO();

		((SheepRenderStateExtension) renderState).sheared$setUuid(sheep.getUUID());
		float stateShearProgress = Mth.lerp(partialTick, shearProgressO, shearProgress);
		((SheepRenderStateExtension) renderState).sheared$setShearProgress(stateShearProgress);
	}
}
