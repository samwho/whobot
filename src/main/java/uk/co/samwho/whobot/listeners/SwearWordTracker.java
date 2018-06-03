package uk.co.samwho.whobot.listeners;

import com.google.common.flogger.FluentLogger;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import uk.co.samwho.whobot.util.WordList;

import java.time.Duration;
import java.util.stream.Stream;

@Singleton
public class SwearWordTracker extends ListenerAdapter {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final WordListTracker wordListTracker;

    @Inject
    public SwearWordTracker(@Named("bad_words") Stream<String> badWords) {
       this.wordListTracker = WordListTracker.builder()
               .wordList(WordList.from(badWords))
               .duration(Duration.ofMinutes(10L))
               .threshold(5)
               .addCallback((user) -> logger.atInfo().log(user.getName() + " is swearing a lot!"))
               .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        wordListTracker.onMessageReceived(event);
    }
}
