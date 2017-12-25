package uk.co.samwho.whobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import uk.co.samwho.whobot.commands.EchoCommand;
import uk.co.samwho.whobot.listeners.CommandDispatcher;
import uk.co.samwho.whobot.listeners.StatsCollector;
import uk.co.samwho.whobot.listeners.WordListTracker;
import uk.co.samwho.whobot.util.WordList;

import javax.security.auth.login.LoginException;

import java.io.IOException;
import java.time.Duration;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Whobot {
    private static Logger logger = Logger.getLogger(Whobot.class.getName());

    public static void main(String ...args) throws IOException {
        LogManager.getLogManager().readConfiguration(
            ClassLoader.getSystemResourceAsStream("logging.properties"));

        StatsCollector stats = new StatsCollector();

        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register("echo", new EchoCommand());

        WordListTracker swearTracker = WordListTracker.builder()
                .wordList(WordList.from(Config.badWords()))
                .duration(Duration.ofMinutes(10L))
                .threshold(5)
                .addCallback((user) -> logger.info(user.getName() + " is swearing a lot!"))
                .build();

        try
        {
            logger.info("connecting to Discord...");
            new JDABuilder(AccountType.BOT)
                .setToken(Config.token())
                .addEventListener(dispatcher)
                .addEventListener(stats)
                .addEventListener(swearTracker)
                .buildBlocking();
            logger.info("connected!");
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            logger.severe("error connecting to Discord: " + e);
            System.exit(1);
        }
    }

}
