package com.zittla.bot.configuration;

import com.zittla.bot.configuration.key.ConfigurationKey;

import static com.zittla.bot.configuration.key.ImmutableConfigurationKey.immutableIntegerKey;
import static com.zittla.bot.configuration.key.ImmutableConfigurationKey.immutableStringKey;

public interface ConfigurationKeys {

    ConfigurationKey<String> DISCORD_TOKEN = immutableStringKey("token", "token");

    ConfigurationKey<Integer> THREAD_POOL_SIZE = immutableIntegerKey("thread-pool-size", Runtime.getRuntime().availableProcessors());

}
