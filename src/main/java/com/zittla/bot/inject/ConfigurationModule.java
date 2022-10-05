package com.zittla.bot.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zittla.bot.configuration.Configuration;
import com.zittla.bot.configuration.impl.ConfigurateConfiguration;
import com.zittla.bot.util.Resources;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigurationModule extends AbstractModule {

    @Provides
    @Singleton
    public Configuration provideConfiguration(
            Path folder
    ) throws IOException {
        String filename = "config.yml";
        Path file = folder.resolve(filename);
        Resources.copyResourceIfNotExists(filename, file);
        return ConfigurateConfiguration.createYamlConfiguration(file);
    }

}
