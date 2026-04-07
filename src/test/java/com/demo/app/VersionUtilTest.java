package com.demo.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VersionUtilTest {

    @Test
    void shouldReturnApplicationVersion() {
        VersionUtil versionUtil = new VersionUtil();
        String version = versionUtil.getApplicationVersion();

        assertNotNull(version);
        assertFalse(version.isEmpty());
    }

    @Test
    void shouldReturnApplicationName() {
        VersionUtil versionUtil = new VersionUtil();
        String appName = versionUtil.getApplicationName();

        assertNotNull(appName);
        assertEquals("Sample CI CD Demo App", appName);
    }
}
