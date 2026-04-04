package com.demo.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class VersionUtilTest {

    @Test
    void shouldReturnApplicationVersion() {
        VersionUtil versionUtil = new VersionUtil();
        String version = versionUtil.getApplicationVersion();

        assertNotNull(version);
        assertEquals("2.0.0", version);
    }

    @Test
    void shouldReturnApplicationName() {
        VersionUtil versionUtil = new VersionUtil();
        String appName = versionUtil.getApplicationName();

        assertNotNull(appName);
        assertEquals("Sample CI CD Demo App", appName);
    }
}
