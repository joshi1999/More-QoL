package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Safebreak implementation
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), cancellable = true)
    public void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> ci) {
        if (FeaturesClient.options().breakSafe) {
            ItemStack mainHandItem = this.minecraft.player.getItemInHand(InteractionHand.MAIN_HAND);
            if (mainHandItem != null && mainHandItem.isDamageableItem()
                    && mainHandItem.getMaxDamage() - mainHandItem.getDamageValue()
                    < FeaturesClient.options().protectDurability) {
                if (FeaturesClient.options().breakSafeSound)
                    this.minecraft.player.playSound(FeaturesClient.BREAK_SAFE_EVENT, 1f, 1f);
                ci.setReturnValue(false);
            }
        }
    }

    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    public void attackEntity(Player player, Entity target, CallbackInfo ci) {
        if (FeaturesClient.options().breakSafe) {
            ItemStack mainHandItem = this.minecraft.player.getItemInHand(InteractionHand.MAIN_HAND);
            if (mainHandItem != null && mainHandItem.isDamageableItem()
                    && mainHandItem.getMaxDamage() - mainHandItem.getDamageValue()
                    < FeaturesClient.options().protectDurability) {
                if (FeaturesClient.options().breakSafeSound)
                    this.minecraft.player.playSound(FeaturesClient.BREAK_SAFE_EVENT, 1f, 1f);
                ci.cancel();
            }
        }
    }

}
