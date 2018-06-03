package uk.co.samwho.whobot.util;

import com.google.common.collect.ImmutableList;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ParsedMessageContent {
    private static final Pattern tokenizer = Pattern.compile("\\s+");

    private final List<String> splitMessage;

    public static ParsedMessageContent from(MessageReceivedEvent event) {
        return from(event.getMessage());
    }

    public static ParsedMessageContent from(Message message) {
        return from(message.getContentRaw());
    }

    public static ParsedMessageContent from(String content) {
        return new ParsedMessageContent(content);
    }

    private ParsedMessageContent(String content) {
        this.splitMessage = ImmutableList.copyOf(Arrays.asList(tokenizer.split(content)));
    }

    public boolean isCommand(String prefix) {
        return splitMessage.size() >= 2 && splitMessage.get(0).equals(prefix);
    }

    public String getCommandName(String prefix) {
        if (!isCommand(prefix)) {
            return null;
        }

        return splitMessage.get(1);
    }

    public List<String> getCommandArgs() {
        return ImmutableList.copyOf(splitMessage.subList(2, splitMessage.size()));
    }
}
