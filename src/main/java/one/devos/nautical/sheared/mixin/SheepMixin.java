package one.devos.nautical.sheared.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.world.entity.animal.sheep.Sheep;
import one.devos.nautical.sheared.mixinterface.SheepExtension;

@Mixin(Sheep.class)
abstract class SheepMixin implements SheepExtension {
	@Unique
	private float shearProgress = 0;
	@Unique
	private float shearProgressO = 0;

	@Override
	public float sheared$getShearProgress() {
		return shearProgress;
	}

	@Override
	public float sheared$getShearProgressO() {
		return shearProgressO;
	}

	@Override
	public void sheared$setShearProgress(float shearProgress) {
		this.shearProgress = shearProgress;
	}

	@Override
	public void sheared$setShearProgressO(float shearProgressO) {
		this.shearProgressO = shearProgressO;
	}
}
