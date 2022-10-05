package com.zittla.bot.commands.discord;

import com.zittla.bot.sender.Source;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.discord.annotation.Sender;
import net.cosmogrp.economy.EconomyHandler;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

@Command(names = "balance")
public class BalanceCommand implements CommandClass {

    private @Inject EconomyHandler economyHandler;

    @Command(names = "")
    public void runDefaultCommand(
            @Sender @NotNull Source source
    ) {
        economyHandler.sendBalance(source);
    }

}
