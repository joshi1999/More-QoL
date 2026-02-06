package fr.idarkay.morefeatures.features;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.DeltaTracker;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.logging.Logger;

/**
 * Renders remaining effect time under the effect icons
 */
public class HudRenderer {
    public void render(GuiGraphics drawContext, DeltaTracker renderTickCounter) {
        if (!FeaturesClient.options().effectTime) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        if (client.screen instanceof InventoryScreen) {
            return;
        }

        boolean hasEffects = !client.player.getActiveEffects().isEmpty();
        if (!hasEffects) return;

        int iconSize = 24;
        int x = drawContext.guiWidth() - iconSize;
        int y = 1;

        for (MobEffectInstance effect : client.player.getActiveEffects()) {
            Logger.getAnonymousLogger().info(effect.toString());

            int duration = effect.getDuration();
            int seconds = duration / 20;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            String timeText = String.format("%d:%02d", minutes, seconds);

            int textWidth = client.font.width(timeText);
            int centeredX = x + (iconSize - textWidth) / 2;

            drawContext.drawString(client.font, timeText, centeredX, y + iconSize + 2, 0xFFFFFFFF, true);

            x -= (iconSize + 2);
        }
    }
}