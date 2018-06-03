package uk.co.samwho.whobot.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import uk.co.samwho.whobot.util.ParsedMessageContent;
import uk.co.samwho.whobot.commands.Command;

import java.util.*;

@Singleton
public final class CommandDispatcher extends ListenerAdapter {
    private final Map<String, Command> commands;
    private final String prefix;

    @Inject
    CommandDispatcher(Map<String, Command> commands, @Named("prefix") String prefix) {
        this.commands = commands;
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        ParsedMessageContent pmc = ParsedMessageContent.from(event);
        if (!pmc.isCommand(prefix)) {
            return;
        }

        Command command = commands.get(pmc.getCommandName(prefix));
        if (command == null) {
            // TODO(samwho): error handling here
            return;
        }

        command.accept(event, pmc);
    }
}
