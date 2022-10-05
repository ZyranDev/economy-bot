package com.zittla.bot.commands.discord.internal;

import com.zittla.bot.commands.discord.internal.parts.SenderPartFactory;
import com.zittla.bot.sender.Source;
import me.fixeddev.commandflow.annotated.part.AbstractModule;
import me.fixeddev.commandflow.annotated.part.Key;
import me.fixeddev.commandflow.discord.annotation.Sender;
import me.fixeddev.commandflow.discord.factory.MemberSenderPartFactory;
import me.fixeddev.commandflow.discord.factory.MessagePartFactory;
import me.fixeddev.commandflow.discord.factory.UserSenderPartFactory;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class DiscordCommandModule extends AbstractModule {
    @Override
    public void configure() {
        bindFactory(Message.class, new MessagePartFactory());
        bindFactory(new Key(Source.class, Sender.class), new SenderPartFactory());
        bindFactory(new Key(Member.class, Sender.class), new MemberSenderPartFactory());
        bindFactory(new Key(User.class, Sender.class), new UserSenderPartFactory());
    }
}
