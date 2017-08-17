package uk.co.samwho.whobot;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ParsedMessageContent {
    private static final Pattern tokenizer = Pattern.compile("\\s+");

    private final List<String> splitMessage;

    public static ParsedMessageContent from(MessageReceivedEvent event) {
        return from(event.getMessage().getContent());
    }

    public static ParsedMessageContent from(Message message) {
        return from(message.getContent());
    }

    public static ParsedMessageContent from(String content) {
        return new ParsedMessageContent(content);
    }

    private ParsedMessageContent(String content) {
        this.splitMessage = Collections.unmodifiableList(Arrays.asList(tokenizer.split(content)));
    }

    public boolean isCommand() {
        return splitMessage.size() >= 2 && splitMessage.get(0).equals(Config.COMMAND_PREFIX);
    }

    public String getCommandName() {
        if (!isCommand()) {
            return null;
        }

        return splitMessage.get(1);
    }

    public List<String> getCommandArgs() {
        return Collections.unmodifiableList(splitMessage.subList(2, splitMessage.size()));
    }
}
