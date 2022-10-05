package com.zittla.bot.configuration.key;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public interface ConfigurationKey<T> {

    static ConfigurationKey<String> stringKey(String path, String defaultValue) {
        return key(path, defaultValue);
    }

    static <T> ConfigurationKey<T> key(String path, T defaultValue, Type type) {
        return new SimpleKey<>(path, defaultValue, type);
    }

    static <T> ConfigurationKey<T> key(String path, T defaultValue) {
        return key(path, defaultValue, defaultValue.getClass());
    }

    String getPath();

    @NotNull T getDefaultValue();

    @NotNull Type getType();

    class SimpleKey<T> implements ConfigurationKey<T> {

        private final String path;
        private final T defaultValue;
        private final Type type;

        SimpleKey(String path, T defaultValue, Type type) {
            this.path = path;
            this.defaultValue = defaultValue;
            this.type = type;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public @NotNull T getDefaultValue() {
            return defaultValue;
        }

        @Override
        public @NotNull Type getType() {
            return type;
        }

    }

}
