package fr.idarkay.morefeatures;

import fr.idarkay.morefeatures.features.HudRenderer;
import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Options;
import fr.idarkay.morefeatures.options.screen.FeaturesOptionsScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.io.File;

@Environment(EnvType.CLIENT)
public class FeaturesClient implements ClientModInitializer {
    private static final String MOD_ID = FeaturesMod.MOD_ID;
    private static FeaturesGameOptions CONFIG;

    //sound
    public static final Identifier BREAK_SAFE_ID = Identifier.parse("more_features_id:break_safe");
    public static final SoundEvent BREAK_SAFE_EVENT = SoundEvent.createVariableRangeEvent(BREAK_SAFE_ID);
    public static long LOCAL_TIME = 12000;
    public static boolean isEating = false;

    private long lastInput = 0;
    private int countDown = 0;
    private long lastShown = 0;


    @Override
    public void onInitializeClient() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, FeaturesClient.BREAK_SAFE_ID, BREAK_SAFE_EVENT);
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.fromNamespaceAndPath(MOD_ID, "before_chat"), new HudRenderer()::render);
        KeyBindings.init();

        startClientTickEvents();
    }

    private void startClientTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isPaused()) return;
            if (KeyBindings.OPEN_OPTIONS_KEYS.isDown()) {
                client.setScreen(new FeaturesOptionsScreen(null, FeaturesClient.options()));
            } else if (FeaturesClient.options().localTime
                    && KeyBindings.ADD_LOCAL_TIME_KEYS.isDown()) {
                FeaturesClient.LOCAL_TIME += 500;
            } else if (FeaturesClient.options().localTime
                    && KeyBindings.REMOVE_LOCAL_TIME_KEYS.isDown()) {
                    FeaturesClient.LOCAL_TIME -= 500;
            } else if (System.currentTimeMillis() - lastInput > 250) {
                if (KeyBindings.ACTIVE_LOCAL_TIME.isDown()) {
                    lastInput = System.currentTimeMillis();
                    Options.LOCAL_IME.set(FeaturesClient.options());
                } else if (KeyBindings.TOGGLE_BREAK_PROTECTION.isDown()) {
                    lastInput = System.currentTimeMillis();
                    options().breakSafe = !options().breakSafe;
                    lastShown = System.currentTimeMillis();
                    String text = "message." + MOD_ID + ".break_protection" + (options().breakSafe ? "On" : "Off");
                    displayInHud(client, text);
                }
            }
            if (System.currentTimeMillis() - lastShown > 3000) {
                if (options().breakSafeWarning) {
                    if (!options().breakSafe) {
                        if (client != null && client.player != null && client.player.getMainHandItem() != null) {
                            ItemStack mainHand = client.player.getMainHandItem();
                            if (mainHand.isDamageableItem()) {
                                lastShown = System.currentTimeMillis();
                                String text = "message." + MOD_ID + ".noBreakProtection";
                                displayInHud(client, text);
                            }
                        }
                    }
                }
            }
        });
    }

    private void displayInHud(Minecraft client, String text) {
        MutableComponent mutableText = Component.translatable(text);
        client.getChatListener().handleSystemMessage(mutableText, true); //Util.NIL_UUID
    }

    public static FeaturesGameOptions options() {
        if (CONFIG == null) {
            CONFIG = loadConfig();
        }

        return CONFIG;
    }

    private static FeaturesGameOptions loadConfig() {

        return FeaturesGameOptions.load(new File("config/more_features_id.json"));
    }

}
