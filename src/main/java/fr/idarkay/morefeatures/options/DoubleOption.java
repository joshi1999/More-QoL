package fr.idarkay.morefeatures.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class DoubleOption extends Option {

    private final double min;
    private final double max;
    private final float step;
    private final Function<FeaturesGameOptions, Double> getter;
    private final BiConsumer<FeaturesGameOptions, Double> setter;
    private final BiFunction<FeaturesGameOptions, DoubleOption, Component> displayStringGetter;

    public DoubleOption(MutableComponent prefix,
                        double min,
                        double max,
                        float step,
                        Function<FeaturesGameOptions, Double> getter,
                        BiConsumer<FeaturesGameOptions, Double> setter,
                        BiFunction<FeaturesGameOptions, DoubleOption, Component> displayStringGetter) {
        super(prefix);
        this.min = min;
        this.max = max;
        this.step = step;
        this.getter = getter;
        this.setter = setter;
        this.displayStringGetter = displayStringGetter;
    }

    public double getRatio(double value) {
        return Mth.clamp((this.adjust(value) - this.min) / (this.max - this.min), 0.0D, 1.0D);
    }

    public double getValue(double ratio) {
        return this.adjust(Mth.lerp(Mth.clamp(ratio, 0.0D, 1.0D), this.min, this.max));
    }

    private double adjust(double value) {
        if (this.step > 0.0F) {
            value = (double) (this.step * (float) Math.round(value / (double) this.step));
        }

        return Mth.clamp(value, this.min, this.max);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void set(FeaturesGameOptions options, double value) {
        setter.accept(options, value);
        options.writeChanges();
    }

    public double get(FeaturesGameOptions options) {
        return getter.apply(options);
    }

    public Component getDisplayString(FeaturesGameOptions options) {
        return getDisplayPrefix().copy().append(this.displayStringGetter.apply(options, this));
    }

    @Override
    public AbstractWidget createButton(FeaturesGameOptions options, int x, int y, int width) {
        return new DoubleSliderWidget(options, x, y, width, 20, this);
    }
}
