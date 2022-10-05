package com.zittla.bot.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.zittla.bot.configuration.Configuration;
import com.zittla.bot.economy.CustomEconomyHandler;
import com.zittla.bot.storage.StorageModule;
import com.zittla.bot.util.Terminal;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.zittla.bot.configuration.ConfigurationKeys.THREAD_POOL_SIZE;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Terminal.class);
        bind(AtomicBoolean.class)
                .annotatedWith(Names.named("RUNNING_STATE"))
                .toInstance(new AtomicBoolean(true));

        bind(CustomEconomyHandler.class);

        install(new EconomyModule());
        install(new StorageModule());
        install(new ConfigurationModule());
        install(new CommandModule());
        install(new DiscordModule());
    }

    @Provides
    @Singleton
    public Path provideRootFolder() {
        return Paths.get(System.getProperty("user.dir"));
    }

    @Provides
    @Singleton
    public Executor provideExecutor(
            Configuration config
    ) {
        return Executors.newFixedThreadPool(config.get(THREAD_POOL_SIZE));
    }

}
