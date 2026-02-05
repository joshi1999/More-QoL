package fr.idarkay.morefeatures.options.screen;

import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import fr.idarkay.morefeatures.options.Option;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public abstract class FeaturesScreen extends GameOptionsScreen {

    @Nullable
    protected final Screen parent;
    protected final FeaturesGameOptions option;
    protected final Option[] options;
    protected final MenuButton[] subMenu;


    protected FeaturesScreen(Text title, @Nullable Screen parent, FeaturesGameOptions featuresGameOptions,
                             Option[] options, MenuButton[] subMenu) {
        super(parent, MinecraftClient.getInstance().options, title);
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
                this.addDrawableChild(menu.createButton(this, this.option, width, height, 150));
            } else {
                Option option = this.options[j - subMenuLength];
                this.addDrawableChild(option.createButton(this.option, width, height, 150));
            }
            ++i;
        }
        ButtonWidget.Builder builder = ButtonWidget.builder(ScreenTexts.DONE, button ->
        {
            this.option.writeChanges();
            this.client.setScreen(this.parent);
        });
        builder.position(this.width / 2 - 100, this.height / 6 + 24 * (i + 1) / 2);
        builder.width(200);
        this.addDrawableChild(builder.build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        //this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

}
