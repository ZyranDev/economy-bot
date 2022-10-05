package com.zittla.bot.discord;

import com.zittla.bot.commands.discord.BalanceCommand;
import com.zittla.bot.commands.discord.DepositCommand;
import com.zittla.bot.commands.discord.WithdrawCommand;
import com.zittla.bot.configuration.Configuration;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import net.dv8tion.jda.api.JDABuilder;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static com.zittla.bot.configuration.ConfigurationKeys.DISCORD_TOKEN;
import static com.zittla.bot.inject.InjectedKeys.DISCORD_COMMAND_MANAGER_KEY;
import static net.dv8tion.jda.api.requests.GatewayIntent.MESSAGE_CONTENT;

@Singleton
public class DiscordBotLauncher {

    private @Inject Configuration configuration;
    @Named(DISCORD_COMMAND_MANAGER_KEY)
    private @Inject CommandManager commandManager;
    private @Inject AnnotatedCommandTreeBuilder commandBuilder;

    private @Inject BalanceCommand balanceCommand;
    private @Inject WithdrawCommand withdrawCommand;
    private @Inject DepositCommand depositCommand;

    public void start() {
        commandManager.registerCommands(commandBuilder.fromClass(depositCommand));
        commandManager.registerCommands(commandBuilder.fromClass(balanceCommand));
        commandManager.registerCommands(commandBuilder.fromClass(withdrawCommand));
        JDABuilder.createDefault(configuration.get(DISCORD_TOKEN))
                .addEventListeners(new CommandListener(commandManager, "!"))
                .enableIntents(MESSAGE_CONTENT)
                .build();
    }

}
