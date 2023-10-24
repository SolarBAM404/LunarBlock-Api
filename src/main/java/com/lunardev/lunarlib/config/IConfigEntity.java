package com.lunardev.lunarlib.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface IConfigEntity {

    /**
     * Gets the File associated with this config type.
     *
     * @return this config type's File
     */
    File getFile();

    /**
     * Sets the File associated with this configtype.
     *
     * @param configFile the File that should be associated with this config type.
     */
    void setFile(File configFile);

    /**
     * Gets the FileConfiguration associated with this config type.
     *
     * @return this config type's FileConfiguration
     */
    FileConfiguration getFileConfiguration();

    /**
     * Sets the FileConfiguration associated with this configtype.
     *
     * @param fileConfiguration the FileConfiguration that should be associated
     *                          with this configtype.
     */
    void setFileConfiguration(FileConfiguration fileConfiguration);

    /**
     * Gets the file name of a certain configtype.
     *
     * @return the file name of the config, without the path but with extension
     */
    String getFileName();

    <T> Class<T> getConfigClass();
}
