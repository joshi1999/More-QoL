package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import fr.idarkay.morefeatures.options.FeaturesGameOptions;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable {

    @Shadow
    @Final
    private DefaultedList<ItemStack> main;

    FeaturesGameOptions options = FeaturesClient.options();

    @Overwrite
    public int getEmptySlot() {
        for(int i = 0; i < this.main.size(); ++i) {
            if (((ItemStack)this.main.get(i)).isEmpty()) {
                if (options.keepSlotEmpty && i == (options.selectedSlot - 1)) {
                    continue;
                }
                return i;
            }
        }

        return -1;
    }
}
