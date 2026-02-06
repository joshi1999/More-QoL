package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Option;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.client.gui.components.Button;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class FeaturesScreen extends OptionsSubScreen {

    @Nullable
    protected final Screen parent;
    protected final FeaturesGameOptions option;
    protected final Option[] options;
    protected final MenuButton[] subMenu;


    protected FeaturesScreen(Component title, @Nullable Screen parent, FeaturesGameOptions featuresGameOptions,
                             Option[] options, MenuButton[] subMenu) {
        super(parent, Minecraft.getInstance().options, title);
        this.options = options;
        this.subMenu = subMenu == null ? new MenuButton[0] : subMenu;
        this.parent = parent;
        this.option = featuresGameOptions;
    }

    @Override
    protected void init() {
        int i = 0;
        int subMenuLength = this.subMenu.length;
        int optionLength = this.options.length;

        for (int j = 0; j < optionLength + subMenuLength; ++j) {
            int width = this.width / 2 - 155 + i % 2 * 160;
            int height = this.height / 6 + 24 * (i >> 1);
            if (j < subMenuLength) {
                MenuButton menu = this.subMenu[j];
                this.addRenderableWidget(menu.createButton(this, this.option, width, height, 150));
            } else {
                Option option = this.options[j - subMenuLength];
                this.addRenderableWidget(option.createButton(this.option, width, height, 150));
            }
            ++i;
        }
        Button.Builder builder = Button.builder(CommonComponents.GUI_DONE, button ->
        {
            this.option.writeChanges();
            this.minecraft.setScreen(this.parent);
        });
        builder.pos(this.width / 2 - 100, this.height / 6 + 24 * (i + 1) / 2);
        builder.width(200);
        this.addRenderableWidget(builder.build());
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        //this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }

}
