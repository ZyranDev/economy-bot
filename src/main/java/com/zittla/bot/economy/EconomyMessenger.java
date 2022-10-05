package com.zittla.bot.economy;

import com.zittla.bot.configuration.Configuration;
import net.cosmogrp.economy.message.Messenger;
import net.cosmogrp.economy.message.Sender;

import javax.inject.Inject;
import java.util.Arrays;

import static com.zittla.bot.configuration.key.ConfigurationKey.stringKey;

public class EconomyMessenger implements Messenger {

    private @Inject Configuration configuration;

    @Override
    public void sendMessage(Sender source, String path, Object... replacements) {
        if (source == null) {
            return;
        }
        if (replacements.length % 2 != 0) {
            throw new IllegalArgumentException("replacements must be even");
        }
        String message = configuration.get(stringKey(path, path));
        for (int i = 0; i < replacements.length; i += 2) {
            message = message.replace(replacements[i].toString(), replacements[i + 1].toString());
        }
        if (message.equals(path)) {
            source.sendMessage(Arrays.toString(replacements));
        }
        source.sendMessage(message);
    }
}
