package uk.co.samwho.whobot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import uk.co.samwho.whobot.ParsedMessageContent;

import java.util.function.BiConsumer;

public interface Command extends BiConsumer<MessageReceivedEvent, ParsedMessageContent> {

}
