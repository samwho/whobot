package uk.co.samwho.whobot.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import uk.co.samwho.whobot.commands.Command;
import uk.co.samwho.whobot.commands.EchoCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandModule extends AbstractModule {
    @Override
    public void configure() {
        Multibinder<Command> commands = Multibinder.newSetBinder(binder(), Command.class);
        commands.addBinding().to(EchoCommand.class);
    }

    @Provides
    @Singleton
    private Map<String, Command> commandMap(Set<Command> commands) {
        Map<String, Command> commandMap = new HashMap<>(commands.size());

        for (Command command : commands) {
            commandMap.put(command.getName(), command);
        }

        return commandMap;
    }
}
