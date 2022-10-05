package com.zittla.bot.discord;

import com.zittla.bot.sender.Source;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.NamespaceImpl;
import me.fixeddev.commandflow.discord.DiscordCommandManager;
import me.fixeddev.commandflow.exception.CommandException;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    private final CommandManager commandManager;
    private final String commandPrefix;

    public CommandListener(CommandManager commandManager, String commandPrefix) {
        this.commandManager = commandManager;
        this.commandPrefix = commandPrefix;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageChannelUnion channel = event.getChannel();
        if (channel.getType() != ChannelType.TEXT) {
            return;
        }

        Member member = event.getMember();
        User user = event.getAuthor();
        Message message = event.getMessage();

        String rawMessage = event.getMessage().getContentRaw();

        if (!rawMessage.startsWith(commandPrefix)) {
            return;
        }

        rawMessage = rawMessage.substring(commandPrefix.length());

        String label = rawMessage.split(" ")[0];

        Namespace namespace = new NamespaceImpl();

        namespace.setObject(Message.class, DiscordCommandManager.MESSAGE_NAMESPACE, message);
        namespace.setObject(Member.class, DiscordCommandManager.MEMBER_NAMESPACE, member);
        namespace.setObject(User.class, DiscordCommandManager.USER_NAMESPACE, user);
        namespace.setObject(TextChannel.class, DiscordCommandManager.CHANNEL_NAMESPACE, channel.asTextChannel());
        namespace.setObject(String.class, "label", label);
        namespace.setObject(Source.class, "SOURCE", new DiscordSource(user,
                throwable -> message.reply("I cannot send you private messages! :disappointed_relieved:" +
                                "\nPlease enable them and try again.")
                        .queue()));

        try {
            commandManager.execute(namespace, rawMessage);
        } catch (CommandException e) {
            CommandException exceptionToSend = e;

            if (e.getCause() instanceof CommandException) {
                exceptionToSend = (CommandException) e.getCause();
            }
            throw new CommandException("An unexpected exception occurred while executing the command " + e.getCommand().getName(), exceptionToSend);
        }
    }

}
