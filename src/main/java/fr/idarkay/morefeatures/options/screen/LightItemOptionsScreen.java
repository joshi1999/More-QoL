package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Option;
import fr.idarkay.morefeatures.options.Options;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class LightItemOptionsScreen extends FeaturesScreen {
    private static final Option[] OPTIONS = new Option[]{Options.LIGHT_SAME_ITEM, Options.LIGHT8SAME_ITEM_RED,
            Options.LIGHT8SAME_ITEM_GREEN, Options.LIGHT8SAME_ITEM_BLUE, Options.LIGHT8SAME_ITEM_ALPHA};

    public LightItemOptionsScreen(@Nullable Screen parent, FeaturesGameOptions featuresGameOptions) {
        super(MenuButtons.LIGHT_ITEM_TEXT, parent, featuresGameOptions, OPTIONS, null);
    }

    @Override
    protected void addOptions() {

    }
}
