package com.zittla.bot;

import com.google.inject.Guice;
import com.zittla.bot.discord.DiscordBotLauncher;
import com.zittla.bot.inject.MainModule;
import com.zittla.bot.util.Terminal;

import javax.inject.Inject;

public class EconomyBotLauncher {

    public static void main(String[] args) {
        new EconomyBotLauncher().start();
    }

    private @Inject Terminal terminal;
    private @Inject DiscordBotLauncher discordBot;

    public EconomyBotLauncher() {
        Guice.createInjector(
                new MainModule()
        ).injectMembers(this);
    }

    private void start() {
        discordBot.start();
        terminal.start();
    }

}
