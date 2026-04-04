package com.demo.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionUtil {

    public String getApplicationVersion() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                return "unknown";
            }
            properties.load(inputStream);
            return properties.getProperty("app.version", "unknown");
        } catch (IOException e) {
            return "unknown";
        }
    }

    public String getApplicationName() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                return "Sample App";
            }
            properties.load(inputStream);
            return properties.getProperty("app.name", "Sample App");
        } catch (IOException e) {
            return "Sample App";
        }
    }
}
