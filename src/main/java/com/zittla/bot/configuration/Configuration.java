package com.zittla.bot.configuration;

import com.zittla.bot.configuration.key.ConfigurationKey;
import org.jetbrains.annotations.NotNull;

public interface Configuration {

    @NotNull <T> T get(@NotNull ConfigurationKey<T> key);

}
