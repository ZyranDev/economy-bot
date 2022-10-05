package com.zittla.bot.configuration.impl;

import com.google.common.base.Splitter;
import com.zittla.bot.configuration.Configuration;
import com.zittla.bot.configuration.key.ConfigurationKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

public class ConfigurateConfiguration implements Configuration {

    public static final Splitter PATH_SPLITTER = Splitter.on('.');

    @Contract("_ -> new")
    public static @NotNull Configuration createYamlConfiguration(Path file) throws ConfigurateException {
        return new ConfigurateConfiguration(YamlConfigurationLoader.builder()
                .path(file)
                .build()
                .load(),
                PATH_SPLITTER);
    }

    private final ConfigurationNode root;
    private final Splitter pathSplitter;

    public ConfigurateConfiguration(
            ConfigurationNode root,
            Splitter pathSplitter
    ) {
        this.root = root;
        this.pathSplitter = pathSplitter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NotNull T get(@NotNull ConfigurationKey<T> key) {
        try {
            T value = (T) root.node(pathSplitter.split(key.getPath())).get(key.getType());
            if (value == null) {
                return key.getDefaultValue();
            }
            return value;
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }
    }

}
