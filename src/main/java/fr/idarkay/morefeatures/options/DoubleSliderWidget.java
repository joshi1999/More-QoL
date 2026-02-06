package fr.idarkay.morefeatures.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;


@Environment(EnvType.CLIENT)
public class DoubleSliderWidget extends AbstractSliderButton {
    private final FeaturesGameOptions options;
    private final DoubleOption option;

    public DoubleSliderWidget(FeaturesGameOptions options, int x, int y, int width, int height, DoubleOption option) {
        super(x, y, width, height, Component.empty(), (double) ((float) option.getRatio(option.get(options))));
        this.options = options;
        this.option = option;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        this.setMessage(this.option.getDisplayString(options));
    }

    @Override
    protected void applyValue() {
        this.option.set(options, this.option.getValue(this.value));
        options.writeChanges();
    }
}
