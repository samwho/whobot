package uk.co.samwho.whobot.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.function.Consumer;

public interface Command extends Consumer<MessageReceivedEvent> {

}
