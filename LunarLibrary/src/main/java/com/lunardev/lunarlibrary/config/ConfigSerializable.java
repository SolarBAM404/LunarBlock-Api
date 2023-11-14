package com.lunardev.lunarlibrary.config;

import java.util.Map;


/**
 * This abstract class is used for saving objects to a FileConfig.
 * Each class that wants to be saved to a FileConfig should extend this class
 * and implement the saveMap method.
 */
public abstract class ConfigSerializable {

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigSerializable() {}

    /**
     * Save the object to a map.
     * The map can then be used to save the object to a FileConfig.
     *
     * @param map The map to which the object's data should be saved.
     */
    public abstract void saveMap(Map<String, Object> map);

}
