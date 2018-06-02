package uk.co.samwho.whobot.listeners;

import com.google.common.flogger.FluentLogger;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public final class EventLogger extends ListenerAdapter {
    private static FluentLogger logger = FluentLogger.forEnclosingClass();

    @Override
    public void onReady(ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            logger.atInfo().log("connected to guild: name=%s, id=%s", guild.getName(), guild.getId());

            for (Role role : guild.getRoles()) {
                logger.atInfo().log("found role: name=%s, id=%s", role.getName(), role.getId());
            }
        }
    }
}
