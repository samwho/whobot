package uk.co.samwho.whobot;

import com.google.common.flogger.FluentLogger;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import uk.co.samwho.whobot.commands.EchoCommand;
import uk.co.samwho.whobot.listeners.CommandDispatcher;
import uk.co.samwho.whobot.listeners.EventLogger;
import uk.co.samwho.whobot.listeners.WordListTracker;
import uk.co.samwho.whobot.util.WordList;

import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.LogManager;

public class Whobot {
    private static FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String ...args) throws IOException {
        LogManager.getLogManager().readConfiguration(
                Whobot.class.getClassLoader().getResourceAsStream("logging.properties"));

        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register("echo", new EchoCommand());

        WordListTracker swearTracker = WordListTracker.builder()
                .wordList(WordList.from(Config.badWords()))
                .duration(Duration.ofMinutes(10L))
                .threshold(5)
                .addCallback((user) -> logger.atInfo().log(user.getName() + " is swearing a lot!"))
                .build();

        try
        {
            logger.atInfo().log("connecting to Discord...");
            new JDABuilder(AccountType.BOT)
                .setToken(Config.token())
                .addEventListener(dispatcher)
                .addEventListener(swearTracker)
                .addEventListener(new EventLogger())
                .buildBlocking();
            logger.atInfo().log("connected!");
        }
        catch (LoginException | InterruptedException e)
        {
            logger.atSevere().withCause(e).log("error connecting to Discord");
            System.exit(1);
        }
    }

}
