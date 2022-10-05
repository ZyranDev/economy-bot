package com.zittla.bot.storage;

import net.cosmogrp.economy.account.EconomyAccount;

import java.util.List;

public interface Storage {

    EconomyAccount getOrCreateAccount(String id);

    List<EconomyAccount> getAccounts();

    void updateBalance(EconomyAccount account);

}
