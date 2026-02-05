package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.input.KeyInput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Implementation of various Item operations in the inventory
 */
@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    @Shadow
    protected abstract boolean handleHotbarKeyPressed(KeyInput keyInput);

    @Shadow
    @Nullable
    protected Slot focusedSlot;

    @Shadow
    protected abstract void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType);

    @Shadow
    @Final
    protected T handler;

    @Shadow
    @Nullable
    protected abstract Slot getSlotAt(double xPosition, double yPosition);

    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Overwrite
    public boolean keyPressed(KeyInput keyInput) {
        if (super.keyPressed(keyInput)) {
            return true;
        } else if (keyInput.getKeycode() != 256 && !this.client.options.inventoryKey.matchesKey(keyInput)) {
            this.handleHotbarKeyPressed(keyInput);
            if (this.focusedSlot != null && this.focusedSlot.hasStack()) {
                if (this.client.options.pickItemKey.matchesKey(keyInput)) {
                    this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 0, SlotActionType.CLONE);
                } else if (this.client.options.dropKey.matchesKey(keyInput)) {
                    boolean control = keyInput.hasCtrlOrCmd();
                    if (keyInput.hasShift() && control) {
                        Item focusedType = this.focusedSlot.getStack().getItem();
                        if (!focusedType.equals(Items.AIR)) {
                            for (Slot slot : handler.slots) {
                                if (slot.getStack().getItem().equals(focusedType)) {
                                    this.onMouseClick(slot, slot.id, 1, SlotActionType.THROW);
                                }
                            }
                        }
                    } else
                        this.onMouseClick(this.focusedSlot, this.focusedSlot.id, control ? 1 : 0, SlotActionType.THROW);
                }
            }
            return true;
        } else {
            this.client.player.closeHandledScreen();
            return true;
        }
    }

    @Inject(method = "mouseDragged", at = @At("TAIL"))
    public void mouseDragged(Click click, double offsetX, double offsetY, CallbackInfoReturnable<Boolean> cir) {
        if ((click.button() == 0 || click.button() == 1) && click.hasShift()
                && this.client.player.currentScreenHandler.getCursorStack().isEmpty()) {
            Slot slot = this.getSlotAt(click.x(), click.y());
            if (slot != null && !slot.getStack().isEmpty()) {
                this.onMouseClick(slot, slot.id, click.button(), SlotActionType.QUICK_MOVE);
            }
        }
    }

    @Inject(method = "drawSlot", at = @At("RETURN"))
    private void drawSlot(DrawContext context, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        final ItemStack cursor = this.handler.getCursorStack();
        final ItemStack slotIT = slot.getStack();
        if ((FeaturesClient.options().lightSameItem && !slotIT.isEmpty()
                && !cursor.isEmpty()
                && slot.getStack().getItem().equals(cursor.getItem()))
        ) {
            context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, FeaturesClient.options().getLightSameItemColor());
        }
    }
}