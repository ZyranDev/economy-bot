package com.zittla.bot.util;

import com.zittla.bot.sender.Source;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.Namespace;
import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jetbrains.annotations.NotNull;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.zittla.bot.inject.InjectedKeys.CONSOLE_COMMAND_MANAGER_KEY;
import static com.zittla.bot.inject.InjectedKeys.RUNNING_STATE_KEY;

public class Terminal extends SimpleTerminalConsole {

    @Named(RUNNING_STATE_KEY)
    private @Inject AtomicBoolean runningState;
    @Named(CONSOLE_COMMAND_MANAGER_KEY)
    private @Inject CommandManager commandManager;

    public Terminal() {
        CustomPrintStream stream = new CustomPrintStream();
        System.setOut(stream);
        System.setErr(stream);
    }

    @Override
    protected boolean isRunning() {
        return runningState.get();
    }

    @Override
    protected LineReader buildReader(LineReaderBuilder builder) {
        return super.buildReader(builder
                .appName("EconomyBot")
                .completer((reader, parsedLine, candidates) -> {
                    String line = parsedLine.line();
                    String commandName = parsedLine.word();
                    if (line.length() == commandName.length()) {
                        commandManager.getCommands().forEach(command -> {
                            if (command.getName().startsWith(commandName)) {
                                candidates.add(new Candidate(command.getName()));
                            }
                        });
                        return;
                    }
                    List<String> suggestions = commandManager.getSuggestions(createDefaultNamespace(), line);
                    if (suggestions == null || suggestions.size() < 1) {
                        return;
                    }
                    suggestions.remove(0);
                    suggestions.forEach(suggestion -> candidates.add(new Candidate(suggestion)));
                }));
    }

    @Override
    protected void runCommand(String command) {
        if (command.isEmpty()) {
            return;
        }
        commandManager.execute(createDefaultNamespace(), command);
    }

    private @NotNull Namespace createDefaultNamespace() {
        Namespace namespace = Namespace.create();
        namespace.setObject(Source.class, "sender", Console.INSTANCE);
        return namespace;
    }

    @Override
    protected void shutdown() {
        // TODO: 10/3/2022 Implement shutdown
    }

}
