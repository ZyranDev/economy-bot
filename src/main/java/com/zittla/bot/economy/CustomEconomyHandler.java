package com.zittla.bot.economy;

import com.zittla.bot.storage.Storage;
import com.zittla.bot.util.Console;
import net.cosmogrp.economy.DefaultEconomyHandler;
import net.cosmogrp.economy.account.EconomyAccount;
import net.cosmogrp.economy.message.Messenger;
import net.cosmogrp.economy.message.Sender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

public class CustomEconomyHandler extends DefaultEconomyHandler {

    private final Storage storage;

    @Inject
    public CustomEconomyHandler(
            Storage storage,
            Messenger messenger
    ) {
        super(new DefaultTransactionExecutor(storage, messenger), messenger, "messages");
        this.storage = storage;
    }

    @Override
    public double getBalance(Sender source) {
        EconomyAccount account = this.getAccount(source);
        return account == null ? 0.0 : account.getBalance();
    }

    @Override
    public void sendBalance(Sender source) {
        EconomyAccount account = this.getAccount(source);
        if (account != null) {
            this.messenger.sendMessage(source, this.identifier + ".balance", "%balance%", account.getBalance());
            return;
        }
        this.messenger.sendMessage(source, this.identifier + ".no-account");
    }

    @Override
    protected Sender defaultSource() {
        return null;
    }

    @Override
    public @Nullable EconomyAccount getAccount(@NotNull Sender target) {
        return storage.getOrCreateAccount(target.getId());
    }

    @Override
    public @Nullable EconomyAccount getTargetAccount(Sender sender, Sender target) {
        return getAccount(target);
    }

}
