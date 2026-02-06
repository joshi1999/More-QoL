package fr.idarkay.morefeatures.mixin;

import fr.idarkay.morefeatures.FeaturesClient;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Localtime implementation
 */
@Mixin(ClientLevel.ClientLevelData.class)
public abstract class ClientWorldPropertiesMixin {


    @Shadow
    private long gameTime;


    /**
     * @author IDarKay
     * @reason for local time
     */
    @Overwrite
    public long getGameTime() {
        if (FeaturesClient.options().localTime)
            return FeaturesClient.LOCAL_TIME;
        return this.gameTime;
    }

}
