package com.simibubi.create.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.simibubi.create.content.logistics.trains.CameraDistanceModifier;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;

@Mixin(Camera.class)
public abstract class CameraMixin {

	@Shadow
	private double getMaxZoom(double pStartingDistance) {
		throw new AssertionError();
	}

	@Shadow
	protected void move(double pDistanceOffset, double pVerticalOffset, double pHorizontalOffset) {
		throw new AssertionError();
	}

	@ModifyArg(
			method = "Lnet/minecraft/client/Camera;setup(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;ZZF)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;move(DDD)V", ordinal = 0),
			index = 0
	)
	public double modifyCameraOffset(double originalValue) {
		return -this.getMaxZoom(4.0D * CameraDistanceModifier.getMultiplier(AnimationTickHolder.getPartialTicks()));
	}

}