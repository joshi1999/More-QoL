package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.FeaturesClient;
import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Option;
import fr.idarkay.morefeatures.options.Options;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FeaturesOptionsScreen extends FeaturesScreen {
    private static final MenuButton[] SUB_MENU_BUTTONS = new MenuButton[]{MenuButtons.BREAKAGE_PROTECTION,
            MenuButtons.LIGHT_ITEM, MenuButtons.KEEP_SLOT_EMPTY};
    private static final Option[] OPTIONS = new Option[]{Options.SHOW_EFFECT_TIME,
            Options.LOCAL_IME, Options.RENDER_BEACON_BEAM};

    public FeaturesOptionsScreen(@Nullable Screen parent) {
        this(parent, FeaturesClient.options());
    }

    public FeaturesOptionsScreen(@Nullable Screen parent, FeaturesGameOptions featuresGameOptions) {
        super(MenuButtons.FEATURES_TEXT, parent, featuresGameOptions, OPTIONS, SUB_MENU_BUTTONS);
    }

    @Override
    protected void addOptions() {

    }
}
