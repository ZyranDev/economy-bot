package com.zittla.bot.commands.discord.internal.parts;

import com.zittla.bot.sender.Source;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.annotated.part.PartFactory;
import me.fixeddev.commandflow.exception.ArgumentParseException;
import me.fixeddev.commandflow.part.CommandPart;
import me.fixeddev.commandflow.stack.ArgumentStack;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.List;

public class SenderPartFactory implements PartFactory {

    @Override
    public CommandPart createPart(String name, List<? extends Annotation> modifiers) {
        return new CommandPart() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public void parse(CommandContext context, ArgumentStack stack, @Nullable CommandPart caller) throws ArgumentParseException {
                Source source = context.getObject(Source.class, "SOURCE");
                context.setValue(this, source);
            }
        };
    }
}
