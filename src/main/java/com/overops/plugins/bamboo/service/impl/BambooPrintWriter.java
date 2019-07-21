package com.overops.plugins.bamboo.service.impl;

import com.atlassian.bamboo.build.logger.BuildLogger;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.PrintStream;

public class BambooPrintWriter extends PrintStream {

    private BuildLogger logger;


    public BambooPrintWriter(@NotNull OutputStream out, BuildLogger logger) {
        super(out);
        this.logger = logger;
    }

    public void print(String s) {
        super.print(s);
        logger.addBuildLogEntry(s);
    }
}
