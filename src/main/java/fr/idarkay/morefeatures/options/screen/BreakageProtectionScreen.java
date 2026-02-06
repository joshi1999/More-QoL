package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Option;
import fr.idarkay.morefeatures.options.Options;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class BreakageProtectionScreen extends FeaturesScreen {
    private static final Option[] OPTIONS = new Option[]{Options.BREAK_SAFE, Options.BREAK_SAFE_WARNING,
            Options.PROTECT_DURABILITY, Options.PROTECT_SOUND};

    public BreakageProtectionScreen(@Nullable Screen parent, FeaturesGameOptions featuresGameOptions) {
        super(MenuButtons.BREAKAGE_PROTECTION_TEXT, parent, featuresGameOptions, OPTIONS, null);
    }

    @Override
    protected void addOptions() {

    }
}
