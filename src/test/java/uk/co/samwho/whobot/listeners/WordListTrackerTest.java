package uk.co.samwho.whobot.listeners;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import uk.co.samwho.whobot.util.TestWithMessageEvents;
import uk.co.samwho.whobot.util.WordList;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

@RunWith(JUnit4.class)
public class WordListTrackerTest extends TestWithMessageEvents {
    @Test
    public void testSimpleWordListTracker() {
        AtomicLong counter = new AtomicLong(0);
        WordListTracker tracker = WordListTracker.builder()
            .wordList(WordList.from(Stream.of("foo")))
            .duration(Duration.ofSeconds(60))
            .threshold(2)
            .addCallback((user) -> counter.incrementAndGet())
            .build();

        tracker.onMessageReceived(event(message(user1(), "hello world")));
        tracker.onMessageReceived(event(message(user2(), "foo foo foo")));
        tracker.onMessageReceived(event(message(user2(), "foo foo foo")));
        assertThat(counter.get()).isEqualTo(1);
    }
}
