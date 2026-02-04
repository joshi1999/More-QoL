package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.block.entity.BeamEmitter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.state.BeaconBlockEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Canceling the rendering process of the beacon beam if wished by the player
 */
@Mixin(BeaconBlockEntityRenderer.class)
public abstract class BeaconBlockEntityRendererMixin<T extends BlockEntity & BeamEmitter> {

    @Inject(method = "render(Lnet/minecraft/client/render/block/entity/state/BeaconBlockEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("HEAD"), cancellable = true)
    public void render(BeaconBlockEntityRenderState beaconBlockEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        if (!FeaturesClient.options().renderBeaconBeam) {
            ci.cancel();
        }
    }

}
