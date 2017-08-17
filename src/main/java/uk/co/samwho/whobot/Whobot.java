package uk.co.samwho.whobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

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

        EventDispatcher dispatcher = new EventDispatcher();

        try
        {
            new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(dispatcher)
                .buildBlocking();
        }
        catch (LoginException | InterruptedException | RateLimitedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
