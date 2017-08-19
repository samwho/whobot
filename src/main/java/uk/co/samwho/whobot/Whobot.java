package uk.co.samwho.whobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.samwho.whobot.commands.EchoCommand;
import uk.co.samwho.whobot.listeners.CommandDispatcher;
import uk.co.samwho.whobot.listeners.StatsCollector;
import uk.co.samwho.whobot.listeners.WordListTracker;
import uk.co.samwho.whobot.util.WordList;

import javax.security.auth.login.LoginException;
import java.time.Duration;

public class Whobot {
    private static Logger log = LoggerFactory.getLogger(Whobot.class);

    public static void main(String ...args) {
        StatsCollector stats = new StatsCollector();

        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.register("echo", new EchoCommand());

        WordListTracker swearTracker = WordListTracker.builder()
                .wordList(WordList.from(Config.badWords()))
                .duration(Duration.ofMinutes(10L))
                .threshold(5)
                .addCallback((user) -> log.info(user.getName() + " is swearing a lot!"))
                .build();

        try
        {
            log.info("connecting...");
            new JDABuilder(AccountType.BOT)
                .setToken(Config.token())
                .addEventListener(dispatcher)
                .addEventListener(stats)
                .addEventListener(swearTracker)
                .buildBlocking();
            log.info("connected");
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            log.error("error connecting to Discord", e);
            System.exit(1);
        }
    }

}
