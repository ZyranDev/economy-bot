package com.zittla.bot.inject;

import com.google.inject.AbstractModule;
import com.zittla.bot.economy.CustomEconomyHandler;
import com.zittla.bot.economy.EconomyMessenger;
import net.cosmogrp.economy.EconomyHandler;
import net.cosmogrp.economy.message.Messenger;

public class EconomyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EconomyHandler.class)
                .to(CustomEconomyHandler.class)
                .asEagerSingleton();
        bind(Messenger.class)
                .to(EconomyMessenger.class)
                .asEagerSingleton();
    }
}
