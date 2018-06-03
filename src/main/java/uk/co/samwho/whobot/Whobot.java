package uk.co.samwho.whobot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import net.dv8tion.jda.core.JDABuilder;
import uk.co.samwho.whobot.annotations.Init;
import uk.co.samwho.whobot.guice.CommandModule;
import uk.co.samwho.whobot.guice.ConfigModule;
import uk.co.samwho.whobot.guice.JDAModule;
import uk.co.samwho.whobot.guice.ListenerModule;

import java.util.Set;

public class Whobot {
    public static void main(String ...args) throws Exception {
        Injector injector = Guice.createInjector(
                new ConfigModule(),
                new ListenerModule(),
                new CommandModule(),
                new JDAModule()
        );

        Set<@Init Runnable> inits = injector.getInstance(Key.get(new TypeLiteral<Set<@Init Runnable>>() {}));
        for (Runnable init : inits) {
            init.run();
        }

        injector.getInstance(JDABuilder.class).buildBlocking();
    }
}
