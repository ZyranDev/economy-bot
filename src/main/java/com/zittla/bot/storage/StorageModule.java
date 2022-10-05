package com.zittla.bot.storage;

import com.google.inject.AbstractModule;
import com.zittla.bot.storage.sql.ConnectionProvider;
import com.zittla.bot.storage.sql.SQLStorage;
import com.zittla.bot.storage.sql.SQLiteConnectionProvider;

public class StorageModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Storage.class)
                .to(SQLStorage.class)
                .asEagerSingleton();
        bind(ConnectionProvider.class)
                .to(SQLiteConnectionProvider.class)
                .asEagerSingleton();
    }
}
