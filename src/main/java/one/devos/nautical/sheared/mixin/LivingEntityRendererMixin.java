package one.devos.nautical.sheared.mixin;

import java.util.Random;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import one.devos.nautical.sheared.mixinterface.SheepExtension;

@Mixin(LivingEntityRenderer.class)
abstract class LivingEntityRendererMixin {
	@Unique
	private static final Random RANDOM = new Random();

	@Inject(method = "setupRotations", at = @At("RETURN"))
	private void onReturnSetupRotations(LivingEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale, CallbackInfo ci) {
		if (!(entity instanceof Sheep sheep)) {
			return;
		}

		long seed = entity.getUUID().getMostSignificantBits() ^ entity.getUUID().getLeastSignificantBits();
		RANDOM.setSeed(seed);

		if (RANDOM.nextInt(5) != 0) {
			return;
		}

		SheepExtension extension = ((SheepExtension) sheep);
		float shearProgress = extension.sheared$getShearProgress();
		float shearProgressO = extension.sheared$getShearProgressO();

		if (shearProgress <= Mth.EPSILON && shearProgressO <= Mth.EPSILON) {
			return;
		}

		float shearDirAngle = RANDOM.nextFloat(Mth.TWO_PI);
		float shearDirX = Mth.cos(shearDirAngle);
		float shearDirZ = Mth.sin(shearDirAngle);
		float shearAmount = RANDOM.nextFloat(0.25f, 1.0f);
		shearAmount *= Mth.rotLerp(partialTick, shearProgressO, shearProgress);

		Matrix4f matrix = new Matrix4f();
		matrix.m10(shearDirX * shearAmount);
		matrix.m12(shearDirZ * shearAmount);
		poseStack.mulPose(matrix);
	}
}
