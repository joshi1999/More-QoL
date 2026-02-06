package fr.idarkay.morefeatures.options;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;

import java.util.function.*;

@Environment(EnvType.CLIENT)
public class BooleanOption extends Option {

    private final Predicate<FeaturesGameOptions> getter;
    private final BiConsumer<FeaturesGameOptions, Boolean> setter;

    protected BooleanOption(MutableComponent prefix, Predicate<FeaturesGameOptions> getter, BiConsumer<FeaturesGameOptions, Boolean> setter) {
        super(prefix);
        this.getter = getter;
        this.setter = setter;
    }

    public void set(FeaturesGameOptions options, boolean value) {
        setter.accept(options, value);
        options.writeChanges();
    }

    public void set(FeaturesGameOptions options) {
        set(options, !get(options));
    }

    public boolean get(FeaturesGameOptions options) {
        return getter.test(options);
    }

    @Override
    public AbstractWidget createButton(FeaturesGameOptions options, int x, int y, int width) {
        Button.Builder builder = Button.builder(getDisplayString(options), button ->
        {
            set(options);
            button.setMessage(getDisplayString(options));
        });
        builder.pos(x, y);
        builder.width(width);
        return builder.build();
    }

    public Component getDisplayString(FeaturesGameOptions options) {
        return this.getDisplayPrefix().copy().append(CommonComponents.optionStatus(this.get(options)));
    }

}
