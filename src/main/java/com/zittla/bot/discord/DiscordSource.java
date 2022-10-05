package com.zittla.bot.discord;

import com.zittla.bot.sender.Source;
import net.dv8tion.jda.api.entities.User;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

public class DiscordSource implements Source {

    private final WeakReference<User> userRef;
    private final Consumer<Throwable> errorHandler;

    public DiscordSource(User user, Consumer<Throwable> errorHandler) {
        this(new WeakReference<>(user), errorHandler);
    }

    public DiscordSource(WeakReference<User> userRef, Consumer<Throwable> errorHandler) {
        this.userRef = userRef;
        this.errorHandler = errorHandler;
    }

    @Override
    public String getId() {
        return user().getId();
    }

    @Override
    public String getName() {
        return user().getName();
    }

    @Override
    public void sendMessage(Component message) {
        if (!(message instanceof TextComponent)) return;
        user().openPrivateChannel()
                .queue(channel -> channel.sendMessage(((TextComponent) message).content())
                        .queue(ignored -> {
                        }, errorHandler));
    }

    private @NotNull User user() {
        User user = userRef.get();
        if (user == null) {
            throw new IllegalStateException("The user is no longer available");
        }
        return user;
    }

}
