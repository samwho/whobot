package uk.co.samwho.whobot.util;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.junit.Before;

import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class TestWithMessageEvents extends TestWithTime {
    private JDA jda = null;
    private AtomicLong idCounter = new AtomicLong(0);
    private Guild guild;
    private MessageChannel channel;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        guild = new GuildImpl((JDAImpl)jda, 0);
        channel = new TextChannelImpl(0, (GuildImpl)guild);

        UserImpl user1Impl = new UserImpl(1, (JDAImpl)jda);
        user1Impl.setName("user1");

        UserImpl user2Impl = new UserImpl(2, (JDAImpl)jda);
        user1Impl.setName("user2");

        user1 = user1Impl;
        user2 = user2Impl;
    }

    protected Message message(User author, String content) {
        MessageImpl messageImpl = new MessageImpl(DiscordUtil.idFromTime(now()), channel, false);
        messageImpl.setAuthor(author);
        messageImpl.setContent(content);
        return messageImpl;
    }

    protected MessageReceivedEvent event(Message m) {
        return new MessageReceivedEvent(jda, idCounter.incrementAndGet(), m);
    }

    protected User user1() {
        return user1;
    }

    protected User user2() {
        return user1;
    }
}
