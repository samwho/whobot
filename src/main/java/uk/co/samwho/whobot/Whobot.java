package uk.co.samwho.whobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.samwho.whobot.commands.EchoCommand;
import uk.co.samwho.whobot.listeners.CommandDispatcher;
import uk.co.samwho.whobot.listeners.StatsCollector;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Whobot {
    private static Logger log = LoggerFactory.getLogger(Whobot.class);

    public static void main(String ...args) {
        String token = null;
        try {
            token = new String(Files.readAllBytes(Paths.get("token.txt")));
        } catch (IOException ioe) {
            log.error("error reading token", ioe);
            System.exit(1);
        }

        StatsCollector stats = new StatsCollector();
        CommandDispatcher dispatcher = new CommandDispatcher();

        dispatcher.register("echo", new EchoCommand());

        try
        {
            log.info("connecting...");
            new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(dispatcher)
                .addEventListener(stats)
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
