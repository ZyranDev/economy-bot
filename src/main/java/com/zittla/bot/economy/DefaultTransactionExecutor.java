package com.zittla.bot.economy;

import com.zittla.bot.storage.Storage;
import net.cosmogrp.economy.account.EconomyAccount;
import net.cosmogrp.economy.context.TransactionContext;
import net.cosmogrp.economy.message.Messenger;
import net.cosmogrp.economy.transaction.TransactionType;
import net.cosmogrp.economy.transaction.executor.SimpleTransactionExecutor;
import net.cosmogrp.economy.transaction.handle.DepositTransactionHandler;
import net.cosmogrp.economy.transaction.handle.TransactionHandler;
import net.cosmogrp.economy.transaction.handle.TransferTransactionHandler;
import net.cosmogrp.economy.transaction.handle.WithdrawTransactionHandler;

public class DefaultTransactionExecutor extends SimpleTransactionExecutor<TransactionContext> {

    public DefaultTransactionExecutor(
            Storage storage,
            Messenger messenger
    ) {
        super(messenger);
        UpdatableTransaction handler = new UpdatableTransaction(storage);
        for (TransactionType type : TransactionType.values()) {
            addHandlers(type, getHandler(type), handler);
        }
    }

    public TransactionHandler getHandler(TransactionType type) {
        switch (type) {
            case DEPOSIT:
                return new DepositTransactionHandler();
            case WITHDRAW:
                return new WithdrawTransactionHandler();
            case TRANSFER:
                return new TransferTransactionHandler();
            default:
                throw new IllegalArgumentException("Unknown transaction type: " + type);
        }
    }

}

class UpdatableTransaction implements TransactionHandler {
    private final Storage storage;

    UpdatableTransaction(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean handle(TransactionContext context) {
        EconomyAccount account = context.getTargetAccount();
        try {
            storage.updateBalance(account);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
