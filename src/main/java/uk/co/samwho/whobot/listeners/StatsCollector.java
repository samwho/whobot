package uk.co.samwho.whobot.listeners;

import java.util.logging.Logger;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public final class StatsCollector extends ListenerAdapter {
    private static Logger logger = Logger.getLogger(StatsCollector.class.getName());

    @Override
    public void onReady(ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            logger.info(() -> "joined guild " + guild.getName());
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        logger.info(() -> "[" + event.getGuild().getName() + "#" + event.getChannel().getName() + "] "
            + " <" + event.getAuthor().getName() + "> "
            + event.getMessage().getContent());
    }
}