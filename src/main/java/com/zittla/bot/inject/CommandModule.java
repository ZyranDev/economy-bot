package com.zittla.bot.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.zittla.bot.commands.discord.internal.DiscordCommandModule;
import com.zittla.bot.util.Console;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.SimpleCommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilder;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import javax.inject.Named;

import static com.zittla.bot.inject.InjectedKeys.CONSOLE_COMMAND_MANAGER_KEY;

public class CommandModule extends AbstractModule {

    @Provides
    @Singleton
    @Named(CONSOLE_COMMAND_MANAGER_KEY)
    public CommandManager provideCommandManager() {
        return createCommandManager();
    }

    public static @NotNull CommandManager createCommandManager() {
        CommandManager commandManager = new SimpleCommandManager();
        commandManager.getErrorHandler().setFallbackHandler((namespace, throwable) -> {
            Console.INSTANCE.sendMessage(Component.text("An error occurred while executing this command!", NamedTextColor.RED));
            throwable.printStackTrace();
            return false;
        });
        return commandManager;
    }

    @Provides
    @Singleton
    public AnnotatedCommandTreeBuilder provideCommandBuilder(
            Injector injector
    ) {
        PartInjector partInjector = PartInjector.create();
        partInjector.install(new DefaultsModule());
        partInjector.install(new DiscordCommandModule());
        return new AnnotatedCommandTreeBuilderImpl(
                AnnotatedCommandBuilder.create(partInjector),
                (clazz, parent) -> injector.getInstance(clazz)
        );
    }

}
