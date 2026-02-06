package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.FeaturesMod;
import net.minecraft.network.chat.Component;

public abstract class MenuButtons {
    private static final String MOD_ID = FeaturesMod.MOD_ID;

    public static Component LIGHT_ITEM_TEXT = Component.translatable("over_light." + MOD_ID + ".title");
    public static Component FEATURES_TEXT = Component.translatable("features." + MOD_ID + ".title");
    public static Component BREAKAGE_PROTECTION_TEXT = Component.translatable("breakSafe." + MOD_ID + ".title");
    public static Component KEEP_SLOT_EMPTY_TEXT = Component.translatable("keepSlotEmpty." + MOD_ID + ".title");

    public static MenuButton LIGHT_ITEM = new MenuButton(LIGHT_ITEM_TEXT, LightItemOptionsScreen::new);
    public static MenuButton FEATURES = new MenuButton(FEATURES_TEXT, FeaturesOptionsScreen::new);
    public static MenuButton BREAKAGE_PROTECTION = new MenuButton(BREAKAGE_PROTECTION_TEXT, BreakageProtectionScreen::new);
    public static MenuButton KEEP_SLOT_EMPTY = new MenuButton(KEEP_SLOT_EMPTY_TEXT, SlotKeepingScreen::new);
}
