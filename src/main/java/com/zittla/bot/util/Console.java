package com.zittla.bot.util;

import com.zittla.bot.sender.Source;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class Console implements Source {

    public static final Console INSTANCE = new Console();

    private final Logger logger = LogManager.getLogger(Console.class);
    private final String id = new UUID(0, 0).toString();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public void sendMessage(Component message) {
        logger.info(AnsiUtil.format(message));
    }

}
