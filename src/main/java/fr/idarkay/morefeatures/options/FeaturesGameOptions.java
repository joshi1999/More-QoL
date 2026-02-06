package fr.idarkay.morefeatures.options;

import com.google.gson.*;
import fr.idarkay.morefeatures.annotation.Exclude;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Environment(EnvType.CLIENT)
public class FeaturesGameOptions {

    public boolean breakSafe = true;
    public boolean breakSafeSound = true;
    public boolean breakSafeWarning = true;
    public double featuresGamma = 5.0;
    public boolean effectTime = true;
    public boolean localTime = false;
    public boolean renderBeaconBeam = true;
    public boolean lightSameItem = true;
    public boolean keepSlotEmpty = false;
    public int rLightSameItem = 0xFF;
    public int gLightSameItem = 0x0;
    public int bLightSameItem = 0x0;
    public int aLightSameItem = 0xA0;
    public int protectDurability = 10;
    public int selectedSlot = 1;

    @Exclude
    private File saveFile;

    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting()
            .setExclusionStrategies(new ExcludeExclusionStrategy())
            .create();

    public int getLightSameItemColor() {
        return (aLightSameItem % 256) << 24 | (rLightSameItem % 256) << 16 | (gLightSameItem % 256) << 8 | (bLightSameItem % 256);
    }

    public static FeaturesGameOptions load(File file) {
        FeaturesGameOptions config;

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                config = gson.fromJson(reader, FeaturesGameOptions.class);
            } catch (IOException e) {
                throw new RuntimeException("Could not parse config", e);
            }

        } else {
            config = new FeaturesGameOptions();
        }
        config.saveFile = file;

        return config;
    }

    public void writeChanges() {
        File dir = this.saveFile.getParentFile();

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Could not create parent directories");
            }
        } else if (!dir.isDirectory()) {
            throw new RuntimeException("The parent file is not a directory");
        }

        try (FileWriter writer = new FileWriter(this.saveFile)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Could not save configuration file", e);
        }
    }

    static class ExcludeExclusionStrategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(Exclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }

}
