package com.zittla.bot.configuration.key;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

// TODO: 10/5/2022 implement immutable configuration values
public interface ImmutableConfigurationKey<T> extends ConfigurationKey<T> {

    static ConfigurationKey<String> immutableStringKey(String path, String defaultValue) {
        return immutableKey(path, defaultValue);
    }

    static ConfigurationKey<Integer> immutableIntegerKey(String path, int defaultValue) {
        return immutableKey(path, defaultValue);
    }

    static <T> ConfigurationKey<T> immutableKey(String path, T defaultValue, Type type) {
        return new ImmutableConfigurationKeyImpl<>(path, defaultValue, type);
    }

    static <T> ConfigurationKey<T> immutableKey(String path, T defaultValue) {
        return immutableKey(path, defaultValue, defaultValue.getClass());
    }

}

class ImmutableConfigurationKeyImpl<T> implements ConfigurationKey<T> {

    private final String path;
    private final T defaultValue;
    private final Type type;

    ImmutableConfigurationKeyImpl(String path, T defaultValue, Type type) {
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
