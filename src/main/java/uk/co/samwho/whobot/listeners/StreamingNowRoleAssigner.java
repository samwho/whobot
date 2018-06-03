package uk.co.samwho.whobot.listeners;

import com.google.common.flogger.FluentLogger;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.RichPresence;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.user.update.UserUpdateGameEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.restaction.AuditableRestAction;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public final class StreamingNowRoleAssigner extends ListenerAdapter {
    private static final long GUILD_PROGDISC = 238666723824238602L;
    private static final long ROLE_STREAMING_NOW = 452619134832738304L;

    private static FluentLogger logger = FluentLogger.forEnclosingClass();
    private final Set<User> streamingNow;

    public StreamingNowRoleAssigner() {
        this.streamingNow = new HashSet<>();
    }

    @Override
    public void onUserUpdateGame(UserUpdateGameEvent event) {
        if (event.getGuild().getIdLong() != GUILD_PROGDISC) {
            return;
        }

        User user = event.getUser();


        if (isStreamStart(event) && isTwitchProgrammingStream(event.getNewGame())) {
            AuditableRestAction<Void> voidAuditableRestAction =
                    event.getGuild().getController().addSingleRoleToMember(
                        event.getMember(), event.getGuild().getRoleById(ROLE_STREAMING_NOW));
            try {
                voidAuditableRestAction.submit().get();
                streamingNow.add(user);
                logger.atInfo().log("programming stream started for user %s", user);
            } catch (InterruptedException | ExecutionException e) {
                // do something
            }
        } else if (streamingNow.contains(user) && isStreamEnd((event))) {
            AuditableRestAction<Void> voidAuditableRestAction =
                    event.getGuild().getController().removeSingleRoleFromMember(
                            event.getMember(), event.getGuild().getRoleById(ROLE_STREAMING_NOW));
            try {
                voidAuditableRestAction.submit().get();
                streamingNow.remove(user);
                logger.atInfo().log("programming stream ended for user %s", user);
            } catch (InterruptedException | ExecutionException e) {
                // do something
            }
        }
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

        if (presence.getUrl() != null && !presence.getUrl().startsWith("https://twitch.tv")) {
            return false;
        }

        if (presence.getDetails() != null && !presence.getDetails().equals("Programming")) {
            return false;
        }

        return true;
    }
}
