package fr.idarkay.morefeatures.features;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.logging.Logger;

/**
 * Renders remaining effect time under the effect icons
 */
public class HudRenderer {
    public void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (!FeaturesClient.options().effectTime) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.currentScreen instanceof InventoryScreen) {
            return;
        }

        boolean hasEffects = !client.player.getStatusEffects().isEmpty();
        if (!hasEffects) return;

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

            int textWidth = client.textRenderer.getWidth(timeText);
            int centeredX = x + (iconSize - textWidth) / 2;

            drawContext.drawText(client.textRenderer, timeText, centeredX, y + iconSize + 2, 0xFFFFFFFF, true);

            x -= (iconSize + 2);
        }
    }
}