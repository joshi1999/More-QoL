package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.FeaturesMod;
import net.minecraft.text.Text;

public abstract class MenuButtons {
    private static final String MOD_ID = FeaturesMod.MOD_ID;

    public static Text LIGHT_ITEM_TEXT = Text.translatable("over_light." + MOD_ID + ".title");
    public static Text FEATURES_TEXT = Text.translatable("features." + MOD_ID + ".title");
    public static Text BREAKAGE_PROTECTION_TEXT = Text.translatable("breakSafe." + MOD_ID + ".title");
    public static Text KEEP_SLOT_EMPTY_TEXT = Text.translatable("keepSlotEmpty." + MOD_ID + ".title");

    public static MenuButton LIGHT_ITEM = new MenuButton(LIGHT_ITEM_TEXT, LightItemOptionsScreen::new);
    public static MenuButton FEATURES = new MenuButton(FEATURES_TEXT, FeaturesOptionsScreen::new);
    public static MenuButton BREAKAGE_PROTECTION = new MenuButton(BREAKAGE_PROTECTION_TEXT, BreakageProtectionScreen::new);
    public static MenuButton KEEP_SLOT_EMPTY = new MenuButton(KEEP_SLOT_EMPTY_TEXT, SlotKeepingScreen::new);
}
