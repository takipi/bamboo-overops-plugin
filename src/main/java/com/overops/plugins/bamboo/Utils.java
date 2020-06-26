package com.overops.plugins.bamboo;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.*;
import java.util.*;

public class Utils {

    private Utils() {
    }

    public static String getVersion() throws IOException {
        return getProperty("version");
    }

    public static String getArtifactId() throws IOException {
        return getProperty("artifactId");
    }

    private static String getProperty(String prop) throws IOException {
        Properties props = new Properties();
        props.load(Utils.class.getResourceAsStream("/version.properties"));
        return props.getProperty(prop);
    }
}