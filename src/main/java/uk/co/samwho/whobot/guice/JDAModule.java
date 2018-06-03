package uk.co.samwho.whobot.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.Set;

public class JDAModule extends AbstractModule {
    @Provides
    private JDABuilder jdaBuilder(@Named("token") String token, Set<ListenerAdapter> listeners) throws LoginException, InterruptedException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(token);
        listeners.forEach(builder::addEventListener);
        return builder;
    }
}
