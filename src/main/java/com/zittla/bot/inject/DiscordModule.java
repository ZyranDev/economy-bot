package com.zittla.bot.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.commandflow.CommandManager;

import javax.inject.Named;

import static com.zittla.bot.inject.InjectedKeys.DISCORD_COMMAND_MANAGER_KEY;

public class DiscordModule extends AbstractModule {

    @Provides
    @Singleton
    @Named(DISCORD_COMMAND_MANAGER_KEY)
    public CommandManager provideCommandManager() {
        return CommandModule.createCommandManager();
    }

}
