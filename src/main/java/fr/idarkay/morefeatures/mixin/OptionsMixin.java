package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.client.Options.genericValueLabel;

/**
 * Optionsintegration
 */
@Mixin(Options.class)
public abstract class OptionsMixin {

    private static final OptionInstance<Double> myGamma;
    private static boolean isFirstResponse = true;

    static {
        myGamma = new OptionInstance("options.gamma", OptionInstance.noTooltip(), (optionText, value) -> {
            int i = (int) ((Double) value * 100.0);
            if (i == 0) {
                return genericValueLabel(optionText, Component.translatable("options.gamma.min"));
            } else if (i == 50) {
                return genericValueLabel(optionText, Component.translatable("options.gamma.default"));
            } else {
                return i == 1000 ? genericValueLabel(optionText, Component.translatable("options.gamma.max")) : genericValueLabel(optionText, i);
            }
        }, OptionInstance.UnitDouble.INSTANCE.xmap((progress) -> {
            FeaturesClient.options().featuresGamma = progress*50;
            FeaturesClient.options().writeChanges();
            return progress * 50;
            } , (value) -> value / 50) , 1.0, (value) -> { });
    }

    @Inject(method = "gamma()Lnet/minecraft/client/OptionInstance;", at = @At("RETURN"), cancellable = true)
    private void injectedReturn(CallbackInfoReturnable<OptionInstance<Double>> cir) {
        if (isFirstResponse) {
            myGamma.set(FeaturesClient.options().featuresGamma);
            isFirstResponse = false;
        }
        cir.setReturnValue(myGamma);
    }
}
