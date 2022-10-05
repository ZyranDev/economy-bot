package com.zittla.bot.storage.sql;

import com.zittla.bot.storage.Storage;
import net.cosmogrp.economy.account.EconomyAccount;
import net.cosmogrp.economy.account.SimpleEconomyAccount;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLStorage implements Storage {

    private final ConnectionProvider connectionProvider;
    private final Connection connection;

    @Inject
    public SQLStorage(@NotNull ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        connection = connectionProvider.getConnection();
        System.out.println("Using " + connectionProvider.getName() + " as storage");
        createTable();
    }

    private void createTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accounts (id VARCHAR(36) PRIMARY KEY, balance DOUBLE)");
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EconomyAccount getOrCreateAccount(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new SimpleEconomyAccount(id,
                        resultSet.getDouble("balance")
                );
            }
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accounts (id, balance) VALUES (?, ?)");
            preparedStatement.setString(1, id);
            preparedStatement.setDouble(2, 0);
            preparedStatement.execute();
            return new SimpleEconomyAccount(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EconomyAccount> getAccounts() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts");
            ResultSet resultSet = statement.executeQuery();
            List<EconomyAccount> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(new SimpleEconomyAccount(resultSet.getString("id"),
                        resultSet.getDouble("balance")
                ));
            }
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBalance(EconomyAccount account) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
            statement.setDouble(1, account.getBalance());
            statement.setString(2, account.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
