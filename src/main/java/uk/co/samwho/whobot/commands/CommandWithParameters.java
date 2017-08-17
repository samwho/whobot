package uk.co.samwho.whobot.commands;

import com.beust.jcommander.JCommander;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import uk.co.samwho.whobot.ParsedMessageContent;

public abstract class CommandWithParameters implements Command {
    @Override
    public void accept(MessageReceivedEvent event, ParsedMessageContent pmc) {
        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(pmc.getCommandArgs().toArray(new String[pmc.getCommandArgs().size()]));

        run(event);
    }

    abstract void run(MessageReceivedEvent event);
}
