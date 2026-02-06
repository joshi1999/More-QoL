package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Option;
import fr.idarkay.morefeatures.options.Options;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class SlotKeepingScreen extends FeaturesScreen {
    private static final Option[] OPTIONS = new Option[]{Options.KEEP_SLOT_EMPTY, Options.SELECTED_SLOT};

    public SlotKeepingScreen(@Nullable Screen parent, FeaturesGameOptions featuresGameOptions) {
        super(MenuButtons.KEEP_SLOT_EMPTY_TEXT, parent, featuresGameOptions, OPTIONS, null);
    }

    @Override
    protected void addOptions() {

    }
}
