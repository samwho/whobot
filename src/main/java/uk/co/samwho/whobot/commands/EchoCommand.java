package uk.co.samwho.whobot.commands;

import com.beust.jcommander.Parameter;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

public class EchoCommand extends CommandWithParameters {
    @Parameter
    private List<String> args;

    @Override
    void run(MessageReceivedEvent event) {
        String message = String.join(" ", args);

        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.println(String.format("[private]<%s> %s",
                    event.getAuthor().getName(),
                    message));
        } else if (event.isFromType(ChannelType.TEXT)) {
            System.out.println(String.format("[%s][#%s]<%s> %s",
                    event.getGuild().getName(),
                    event.getChannel().getName(),
                    event.getAuthor().getName(),
                    message));
        }
    }
}
