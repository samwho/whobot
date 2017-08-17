package uk.co.samwho.whobot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import uk.co.samwho.whobot.commands.Command;

import java.util.*;

public final class EventDispatcher extends ListenerAdapter {
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        ParsedMessageContent pmc = ParsedMessageContent.from(event);
        if (pmc.isCommand()) {
            Command command = commands.get(pmc.getCommandName());
            command.accept(event);
            return;
        }
    }

    public void registerCommand(String name, Command command) {
        if (commands.containsKey(name)) {
            throw new IllegalArgumentException("command with name \"" + name + "\" already exists");
        }

        this.commands.put(name, command);
    }
}
