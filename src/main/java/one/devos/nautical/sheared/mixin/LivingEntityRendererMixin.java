package one.devos.nautical.sheared.mixin;

import java.util.Random;
import java.util.UUID;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import one.devos.nautical.sheared.mixinterface.SheepRenderStateExtension;

@Mixin(LivingEntityRenderer.class)
abstract class LivingEntityRendererMixin {
	@Unique
	private static final Random RANDOM = new Random();

	@Inject(method = "setupRotations", at = @At("RETURN"))
	private void onReturnSetupRotations(LivingEntityRenderState renderState, PoseStack poseStack, float bodyRot, float scale, CallbackInfo ci) {
		if (!(renderState instanceof SheepRenderStateExtension sheepRenderStateExt)) {
			return;
		}

		UUID uuid = sheepRenderStateExt.sheared$getUuid();

		if (uuid == null) {
			return;
		}

		long seed = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
		RANDOM.setSeed(seed);

		if (RANDOM.nextInt(5) != 0) {
			return;
		}

		float shearProgress = sheepRenderStateExt.sheared$getShearProgress();

		if (shearProgress <= Mth.EPSILON) {
			return;
		}

		float shearDirAngle = RANDOM.nextFloat(Mth.TWO_PI);
		float shearDirX = Mth.cos(shearDirAngle);
		float shearDirZ = Mth.sin(shearDirAngle);
		float shearAmount = RANDOM.nextFloat(0.25f, 1.0f);
		shearAmount *= shearProgress;

		Matrix4f matrix = new Matrix4f();
		matrix.m10(shearDirX * shearAmount);
		matrix.m12(shearDirZ * shearAmount);
		poseStack.mulPose(matrix);
	}
}
