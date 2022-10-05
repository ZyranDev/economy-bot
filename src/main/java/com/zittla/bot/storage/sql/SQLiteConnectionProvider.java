package com.zittla.bot.storage.sql;

import javax.inject.Inject;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionProvider implements ConnectionProvider {

    private final Path file;

    @Inject
    public SQLiteConnectionProvider(Path folder) {
        file = folder.resolve("economy-bot.db");
    }

    @Override
    public String getName() {
        return "SQLite";
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            // TODO: 10/5/2022 implement no closeable connection
            return DriverManager.getConnection("jdbc:sqlite:" + file.toAbsolutePath());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
