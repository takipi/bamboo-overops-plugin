package com.overops.plugins.bamboo;

import java.io.*;
import java.util.*;

public class Utils {

    private Utils() {
    }


    public static String getVersion() {
        Properties props = new Properties();
        try {
            props.load(Utils.class.getResourceAsStream("/version.properties"));
        } catch (IOException ex) {
            props.setProperty("version", "N/A");
        }
        return props.getProperty("version");
    }
}