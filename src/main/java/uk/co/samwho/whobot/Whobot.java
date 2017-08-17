package uk.co.samwho.whobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.EventListener;
import uk.co.samwho.whobot.commands.EchoCommand;
import uk.co.samwho.whobot.listeners.CommandDispatcher;
import uk.co.samwho.whobot.listeners.StatsCollector;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Whobot {
    public static void main(String ...args) {
        String token = null;
        try {
            token = new String(Files.readAllBytes(Paths.get("token.txt")));
        } catch (IOException ioe) {
            System.err.println("error reading token: " + ioe);
            System.exit(1);
        }

        StatsCollector stats = new StatsCollector();
        CommandDispatcher dispatcher = new CommandDispatcher();

        dispatcher.register("echo", new EchoCommand());

        try
        {
            new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(dispatcher)
                .addEventListener(stats)
                .buildBlocking();
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
