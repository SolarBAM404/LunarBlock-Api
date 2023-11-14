package com.lunardev.lunarlib.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public abstract class ConfigManager {

    ConfigManager() {
    }

    public static <T> T getConfig(IConfigEntity entity) {
        File file = entity.getFile();

        if (file != null && file.exists() && file.isFile()) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                return mapper.readValue(file, entity.getConfigClass());
            } catch (IOException ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, ex.getMessage());
                return null;
            }
        }
        return null;
    }

    public static <T> void saveConfig(IConfigEntity entity, T data) {
        File file = entity.getFile();

        if (file != null && file.exists() && file.isFile()) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            try {
                mapper.writeValue(file, data);
            } catch (IOException ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, ex.getMessage());
            }
        }
    }

}
