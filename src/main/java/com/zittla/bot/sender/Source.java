package com.zittla.bot.sender;

import net.cosmogrp.economy.message.Sender;
import net.kyori.adventure.text.Component;

public interface Source extends Sender {

    String getName();

    default void sendMessage(String message) {
        sendMessage(Component.text(message));
    }

    void sendMessage(Component message);

}
