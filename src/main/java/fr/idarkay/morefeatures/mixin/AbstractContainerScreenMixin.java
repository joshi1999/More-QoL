package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.network.chat.Component;
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
@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {
    @Shadow
    protected abstract boolean checkHotbarKeyPressed(KeyEvent keyInput);

    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Shadow
    protected abstract void slotClicked(Slot slot, int invSlot, int clickData, ContainerInput actionType);

    @Shadow
    @Final
    protected T menu;

    @Shadow
    @Nullable
    protected abstract Slot getHoveredSlot(double xPosition, double yPosition);

    protected AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    @Overwrite
    public boolean keyPressed(KeyEvent keyInput) {
        if (super.keyPressed(keyInput)) {
            return true;
        } else if (keyInput.input() != 256 && !this.minecraft.options.keyInventory.matches(keyInput)) {
            this.checkHotbarKeyPressed(keyInput);
            if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                if (this.minecraft.options.keyPickItem.matches(keyInput)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ContainerInput.CLONE);
                } else if (this.minecraft.options.keyDrop.matches(keyInput)) {
                    boolean control = keyInput.hasControlDownWithQuirk();
                    if (keyInput.hasShiftDown() && control) {
                        Item focusedType = this.hoveredSlot.getItem().getItem();
                        if (!focusedType.equals(Items.AIR)) {
                            for (Slot slot : menu.slots) {
                                if (slot.getItem().getItem().equals(focusedType)) {
                                    this.slotClicked(slot, slot.index, 1, ContainerInput.THROW);
                                }
                            }
                        }
                    } else
                        this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, control ? 1 : 0, ContainerInput.THROW);
                }
            }
            return true;
        } else {
            this.minecraft.player.closeContainer();
            return true;
        }
    }

    @Inject(method = "mouseDragged", at = @At("TAIL"))
    public void mouseDragged(MouseButtonEvent click, double offsetX, double offsetY, CallbackInfoReturnable<Boolean> cir) {
        if ((click.button() == 0 || click.button() == 1) && click.hasShiftDown()
                && this.minecraft.player.containerMenu.getCarried().isEmpty()) {
            Slot slot = this.getHoveredSlot(click.x(), click.y());
            if (slot != null && !slot.getItem().isEmpty()) {
                this.slotClicked(slot, slot.index, click.button(), ContainerInput.QUICK_MOVE);
            }
        }
    }

    @Inject(method = "renderSlot", at = @At("RETURN"))
    private void drawSlot(GuiGraphics context, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        final ItemStack cursor = this.menu.getCarried();
        final ItemStack slotIT = slot.getItem();
        if ((FeaturesClient.options().lightSameItem && !slotIT.isEmpty()
                && !cursor.isEmpty()
                && slot.getItem().getItem().equals(cursor.getItem()))
        ) {
            context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, FeaturesClient.options().getLightSameItemColor());
        }
    }
}