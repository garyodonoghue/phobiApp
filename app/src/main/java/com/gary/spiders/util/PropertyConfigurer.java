package com.gary.spiders.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyConfigurer {

    public static String getProperty(String key,Context context) {
        String property = null;
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            property = properties.getProperty(key);
        } catch (IOException e) {
            // do nothing
        }

        return property;
    }
}