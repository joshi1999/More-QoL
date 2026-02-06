package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class MenuButton {

    private final Component name;
    private final BiFunction<Screen, FeaturesGameOptions, Screen> creator;

    public MenuButton(Component name, BiFunction<Screen, FeaturesGameOptions, Screen> creator) {
        this.name = name;
        this.creator = creator;
    }

    public AbstractWidget createButton(@Nullable Screen parent, FeaturesGameOptions options, int x, int y, int width) {
        Button.Builder builder = Button.builder(this.name, button ->
                Minecraft.getInstance().setScreen(creator.apply(parent, options)));
        return builder.pos(x, y).width(width).build();
    }

}
