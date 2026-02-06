package fr.idarkay.morefeatures;

import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public abstract class KeyBindings {
    private static final String MOD_ID = FeaturesMod.MOD_ID;
    private static final KeyMapping.Category featuresCategory = KeyMapping.Category.register(Identifier.parse("key.categories." + MOD_ID));

    public static final KeyMapping OPEN_OPTIONS_KEYS = new KeyMapping("key." + MOD_ID + ".options",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F,
            featuresCategory);
    public static final KeyMapping ADD_LOCAL_TIME_KEYS = new KeyMapping("key." + MOD_ID + ".addTime",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_ADD,
            featuresCategory);
    public static final KeyMapping REMOVE_LOCAL_TIME_KEYS = new KeyMapping("key." + MOD_ID + ".removeTime",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_KP_SUBTRACT,
            featuresCategory);
    public static final KeyMapping ACTIVE_LOCAL_TIME = new KeyMapping("key." + MOD_ID + ".localTime",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            featuresCategory);
    public static final KeyMapping TOGGLE_BREAK_PROTECTION = new KeyMapping("key." + MOD_ID + ".toggleBreakProtection",
            InputConstants.UNKNOWN.getValue(),
            featuresCategory);

    public static void init() {
        KeyMappingHelper.registerKeyMapping(OPEN_OPTIONS_KEYS);
        KeyMappingHelper.registerKeyMapping(ADD_LOCAL_TIME_KEYS);
        KeyMappingHelper.registerKeyMapping(REMOVE_LOCAL_TIME_KEYS);
        KeyMappingHelper.registerKeyMapping(ACTIVE_LOCAL_TIME);
        KeyMappingHelper.registerKeyMapping(TOGGLE_BREAK_PROTECTION);
    }

}
