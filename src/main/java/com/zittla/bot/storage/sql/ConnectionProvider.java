package com.zittla.bot.storage.sql;

import java.sql.Connection;

public interface ConnectionProvider {

    String getName();

    Connection getConnection();

}
