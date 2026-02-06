package fr.idarkay.morefeatures.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.MutableComponent;

@Environment(EnvType.CLIENT)
public abstract class Option {
    protected final MutableComponent prefix;

    protected Option(MutableComponent prefix) {
        this.prefix = prefix;
    }

    public abstract AbstractWidget createButton(FeaturesGameOptions options, int x, int y, int width);

    public MutableComponent getDisplayPrefix() {
        return prefix;
    }
}
