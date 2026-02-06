package fr.idarkay.morefeatures.options;

import net.minecraft.network.chat.Component;

public abstract class Options {
    public static final BooleanOption BREAK_SAFE = new BooleanOption(
            Component.translatable("options.more_features_id.breakSafe"),
            options -> options.breakSafe,
            (options, aBoolean) -> options.breakSafe = aBoolean
    );
    public static final BooleanOption BREAK_SAFE_WARNING = new BooleanOption(
            Component.translatable("options.more_features_id.breakSafeWarning"),
            options -> options.breakSafeWarning,
            (options, aBoolean) -> options.breakSafeWarning = aBoolean
    );
    public static final BooleanOption LIGHT_SAME_ITEM = new BooleanOption(
            Component.translatable("options.more_features_id.lightSameItem"),
            options -> options.lightSameItem,
            (options, aBoolean) -> options.lightSameItem = aBoolean
    );
    public static final BooleanOption SHOW_EFFECT_TIME = new BooleanOption(
            Component.translatable("options.more_features_id.potionTime"),
            options -> options.effectTime,
            (options, aBoolean) -> options.effectTime = aBoolean
    );
    public static final BooleanOption PROTECT_SOUND = new BooleanOption(
            Component.translatable("options.more_features_id.protectSound"),
            options -> options.breakSafeSound,
            (options, aBoolean) -> options.breakSafeSound = aBoolean
    );
    public static final BooleanOption LOCAL_IME = new BooleanOption(
            Component.translatable("options.more_features_id.localTime"),
            options -> options.localTime,
            (options, aBoolean) -> options.localTime = aBoolean
    );
    public static final BooleanOption RENDER_BEACON_BEAM = new BooleanOption(
            Component.translatable("options.more_features_id.renderBeaconBeam"),
            options -> options.renderBeaconBeam,
            (options, aBoolean) -> options.renderBeaconBeam = aBoolean
    );
    public static final DoubleOption LIGHT8SAME_ITEM_RED = new DoubleOption(
            Component.translatable("options.more_features_id.red"),
            0.0d, 255.0d, 1.0f,
            options -> (double) options.rLightSameItem,
            (options, aDouble) -> options.rLightSameItem = aDouble.intValue(),
            (options, doubleOption) -> Component.literal(String.valueOf((int) doubleOption.get(options)))
    );
    public static final DoubleOption LIGHT8SAME_ITEM_GREEN = new DoubleOption(
            Component.translatable("options.more_features_id.green"),
            0.0d, 255.0d, 1.0f,
            options -> (double) options.gLightSameItem,
            (options, aDouble) -> options.gLightSameItem = aDouble.intValue(),
            (options, doubleOption) -> Component.literal(String.valueOf((int) doubleOption.get(options)))
    );
    public static final DoubleOption LIGHT8SAME_ITEM_BLUE = new DoubleOption(
            Component.translatable("options.more_features_id.blue"),
            0.0d, 255.0d, 1.0f,
            options -> (double) options.bLightSameItem,
            (options, aDouble) -> options.bLightSameItem = aDouble.intValue(),
            (options, doubleOption) -> Component.literal(String.valueOf((int) doubleOption.get(options)))
    );
    public static final DoubleOption LIGHT8SAME_ITEM_ALPHA = new DoubleOption(
            Component.translatable("options.more_features_id.alpha"),
            0.0d, 255.0d, 1.0f,
            options -> (double) options.aLightSameItem,
            (options, aDouble) -> options.aLightSameItem = aDouble.intValue(),
            (options, doubleOption) -> Component.literal(String.valueOf((int) doubleOption.get(options)))
    );
    public static final DoubleOption PROTECT_DURABILITY = new DoubleOption(
            Component.translatable("options.more_features_id.protectDurability"),
            5.0d, 100.0d, 1.0f,
            options -> (double) options.protectDurability,
            (options, aDouble) -> options.protectDurability = aDouble.intValue(),
            (options, doubleOption) -> Component.literal(String.valueOf((int) doubleOption.get(options)))
    );
    public static final DoubleOption SELECTED_SLOT = new DoubleOption(
            Component.translatable("options.more_features_id.selectedSlot"),
            1.0d, 9.0, 1.0f,
            options -> (double) options.selectedSlot,
            (options, aDouble) -> options.selectedSlot = aDouble.intValue(),
            (options, doubleOption) -> Component.literal(String.valueOf((int) doubleOption.get(options)))
    );
    public static final BooleanOption KEEP_SLOT_EMPTY = new BooleanOption(
            Component.translatable("options.more_features_id.keepSlotEmpty"),
            options -> options.keepSlotEmpty,
            (options, aBoolean) -> options.keepSlotEmpty = aBoolean
    );
}
