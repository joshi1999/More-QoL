package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.Nameable;
import net.minecraft.core.NonNullList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container, Nameable {

    @Shadow
    @Final
    private NonNullList<ItemStack> items;

    FeaturesGameOptions options = FeaturesClient.options();

    @Overwrite
    public int getFreeSlot() {
        for(int i = 0; i < this.items.size(); ++i) {
            if (((ItemStack)this.items.get(i)).isEmpty()) {
                if (options.keepSlotEmpty && i == (options.selectedSlot - 1)) {
                    continue;
                }
                return i;
            }
        }

        return -1;
    }
}
