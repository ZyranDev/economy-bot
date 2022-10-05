package com.zittla.bot.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;

public class CustomPrintStream extends PrintStream {

    public final Logger LOGGER = LogManager.getLogger(CustomPrintStream.class);

    public CustomPrintStream() {
        super(System.out);
    }

    @Override
    public void println(@Nullable Object x) {
        if (x != null) {
            LOGGER.info(x.toString());
        }
    }
}
