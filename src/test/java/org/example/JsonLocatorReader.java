package org.example;

import com.google.gson.Gson;
import java.io.FileReader;
import java.util.Map;

public class JsonLocatorReader {
    private static Map locators;

    static {
        try {
            FileReader reader = new FileReader("src/test/resources/locators.json");

            locators = new Gson().fromJson(reader, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String locatorName(String key) {
        return (String) locators.get(key);
    }
}
