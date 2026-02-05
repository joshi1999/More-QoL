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
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.File;

@Environment(EnvType.CLIENT)
public class FeaturesClient implements ClientModInitializer {
    private static final String MOD_ID = FeaturesMod.MOD_ID;
    private static FeaturesGameOptions CONFIG;

    //sound
    public static final Identifier BREAK_SAFE_ID = Identifier.of("more_features_id:break_safe");
    public static final SoundEvent BREAK_SAFE_EVENT = SoundEvent.of(BREAK_SAFE_ID);
    public static long LOCAL_TIME = 12000;
    public static boolean isEating = false;

    private long lastInput = 0;
    private int countDown = 0;
    private long lastShown = 0;


    @Override
    public void onInitializeClient() {
        Registry.register(Registries.SOUND_EVENT, FeaturesClient.BREAK_SAFE_ID, BREAK_SAFE_EVENT);
        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(MOD_ID, "before_chat"), new HudRenderer()::render);
        KeyBindings.init();

        startClientTickEvents();
    }

    private void startClientTickEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.isPaused()) return;
            if (KeyBindings.OPEN_OPTIONS_KEYS.isPressed()) {
                client.setScreen(new FeaturesOptionsScreen(null, FeaturesClient.options()));
            } else if (FeaturesClient.options().localTime
                    && KeyBindings.ADD_LOCAL_TIME_KEYS.isPressed()) {
                FeaturesClient.LOCAL_TIME += 500;
            } else if (FeaturesClient.options().localTime
                    && KeyBindings.REMOVE_LOCAL_TIME_KEYS.isPressed()) {
                    FeaturesClient.LOCAL_TIME -= 500;
            } else if (System.currentTimeMillis() - lastInput > 250) {
                if (KeyBindings.ACTIVE_LOCAL_TIME.isPressed()) {
                    lastInput = System.currentTimeMillis();
                    Options.LOCAL_IME.set(FeaturesClient.options());
                } else if (KeyBindings.TOGGLE_BREAK_PROTECTION.isPressed()) {
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
                        if (client != null && client.player != null && client.player.getMainHandStack() != null) {
                            ItemStack mainHand = client.player.getMainHandStack();
                            if (mainHand.isDamageable()) {
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

    private void displayInHud(MinecraftClient client, String text) {
        MutableText mutableText = Text.translatable(text);
        client.getMessageHandler().onGameMessage(mutableText, true); //Util.NIL_UUID
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
