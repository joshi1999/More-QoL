package fr.idarkay.morefeatures.features;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.logging.Logger;


public class HudRenderer {
    // Render the remaining time of the effect in the hud, directly under the effect which is shown.
    public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (!FeaturesClient.options().effectTime) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.currentScreen instanceof InventoryScreen) {
            return;
        }
        // Get the beneficialEffects collection to determine icon size
        boolean hasEffects = !client.player.getStatusEffects().isEmpty();
        if (!hasEffects) return;

        // Icon size is 24 for small icons, 32 for large icons (when ambient effects are shown)
        int iconSize = 24;
        int x = drawContext.getScaledWindowWidth() - iconSize;
        int y = 1;

        for (StatusEffectInstance effect : client.player.getStatusEffects()) {
            Logger.getAnonymousLogger().info(effect.toString());

            int duration = effect.getDuration();
            int seconds = duration / 20;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            String timeText = String.format("%d:%02d", minutes, seconds);

            // Calculate text width and center it under the icon
            int textWidth = client.textRenderer.getWidth(timeText);
            int centeredX = x + (iconSize - textWidth) / 2;

            // Draw text directly below the icon
            drawContext.drawText(client.textRenderer, timeText, centeredX, y + iconSize + 2, 0xFFFFFFFF, true);

            x -= (iconSize + 2);
        }
    }
}