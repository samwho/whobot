package uk.co.samwho.whobot.listeners;

import com.google.common.base.Stopwatch;
import com.google.common.flogger.FluentLogger;
import com.google.inject.Singleton;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.RichPresence;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.user.update.UserUpdateGameEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.restaction.AuditableRestAction;

@Singleton
public final class StreamingNowRoleAssigner extends ListenerAdapter {
    private static final long GUILD_PROGDISC = 238666723824238602L;
    private static final long ROLE_STREAMING_NOW = 452619134832738304L;

    private static FluentLogger logger = FluentLogger.forEnclosingClass();

    private boolean hasNecessaryPermissions(Guild guild) {
        return guild.getSelfMember().hasPermission(Permission.MANAGE_ROLES);
    }

    @Override
    public void onReady(ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            if (guild.getIdLong() != GUILD_PROGDISC) {
                continue;
            }

            if (!hasNecessaryPermissions(guild)) {
                logger.atSevere().log("bot doesn't have the necessary permissions in %s", guild.getName());
                continue;
            }

            Stopwatch stopwatch = Stopwatch.createStarted();
            int add = 0;
            int remove = 0;

            synchronized (this) {
                for (Member member : guild.getMembers()) {
                    if (hasStreamingNowRole(guild, member)) {
                        if (!isTwitchProgrammingStream(member.getGame())) {
                            removeStreamingNowRoleFromMember(guild, member);
                            remove++;
                        }
                    } else if (isTwitchProgrammingStream(member.getGame())) {
                        addStreamingNowRoleToMember(guild, member);
                        add++;
                    }
                }
            }

            logger.atInfo().log(
                    "build state on guild available took %s, added role to %s members, removed from %s members",
                    stopwatch.elapsed(), add, remove);
        }
    }

    @Override
    public void onUserUpdateGame(UserUpdateGameEvent event) {
        if (event.getGuild().getIdLong() != GUILD_PROGDISC) {
            return;
        }

        Member member = event.getMember();
        Guild guild = event.getGuild();

        if (!hasStreamingNowRole(guild, member) && isStreamStart(event) && isTwitchProgrammingStream(event.getNewGame())) {
            addStreamingNowRoleToMember(guild, member);
        } else if (hasStreamingNowRole(guild, member) && isStreamEnd((event))) {
            removeStreamingNowRoleFromMember(guild, member);
        }
    }

    private void addStreamingNowRoleToMember(Guild guild, Member member) {
        AuditableRestAction<Void> action =
                guild.getController().addSingleRoleToMember(member, guild.getRoleById(ROLE_STREAMING_NOW));

        String name = member.getUser().getName();
        action.queue(
                (success) -> {
                    logger.atInfo().log("user %s successfully given StreamingNow role", name);
                },
                (fail) -> {
                    logger.atInfo().withCause(fail).log("could not give user %s StreamingNow role", name);
                }
        );
    }

    private void removeStreamingNowRoleFromMember(Guild guild, Member member) {
        AuditableRestAction<Void> action =
                guild.getController().removeSingleRoleFromMember(member, guild.getRoleById(ROLE_STREAMING_NOW));

        String name = member.getUser().getName();
        action.queue(
                (success) -> {
                    logger.atInfo().log("user %s successfully stripped of StreamingNow role", name);
                },
                (fail) -> {
                    logger.atInfo().withCause(fail).log("could not give user %s StreamingNow role", name);
                }
        );
    }

    private boolean hasStreamingNowRole(Guild guild, Member member) {
        return member.getRoles().contains(guild.getRoleById(ROLE_STREAMING_NOW));
    }

    private boolean isStreamStart(UserUpdateGameEvent event) {
        return event.getNewGame() != null
                && event.getNewGame().getType() == Game.GameType.STREAMING;
    }

    private boolean isStreamEnd(UserUpdateGameEvent event) {
        return event.getOldGame() != null
                && event.getOldGame().getType() == Game.GameType.STREAMING;
    }

    private boolean isTwitchProgrammingStream(Game game) {
        if (game == null) {
            return false;
        }

        if (game.getType() != Game.GameType.STREAMING) {
            return false;
        }

        RichPresence presence = game.asRichPresence();
        if (presence == null) {
            return false;
        }

        if (presence.getUrl() == null) {
            return false;
        }

        if (!presence.getUrl().startsWith("https://www.twitch.tv")) {
            return false;
        }

        if (presence.getDetails() == null) {
            return false;
        }

        if (!presence.getDetails().equals("Programming")) {
            return false;
        }

        return true;
    }
}
