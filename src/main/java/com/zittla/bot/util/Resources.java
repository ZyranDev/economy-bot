package com.zittla.bot.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Resources {

    private Resources() {
        throw new UnsupportedOperationException();
    }

    public static InputStream getInternalResource(String name) {
        return Resources.class.getClassLoader().getResourceAsStream(name);
    }

    public static void copyResourceIfNotExists(String name, Path target) throws IOException {
        if (!Files.exists(target)) {
            Files.copy(getInternalResource(name), target);
        }
    }

}
