package one.devos.nautical.sheared.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import one.devos.nautical.sheared.mixinterface.SheepExtension;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
	@Inject(method = "tick", at = @At("RETURN"))
	private void onReturnTick(CallbackInfo ci) {
		if (!((Object) this instanceof Sheep sheep)) {
			return;
		}

		SheepExtension extension = (SheepExtension) sheep;
		float shearProgress = extension.sheared$getShearProgress();
		extension.sheared$setShearProgressO(shearProgress);
		float target = sheep.isSheared() ? 1 : 0;
		extension.sheared$setShearProgress(Mth.lerp(0.08f, shearProgress, target));
	}
}
