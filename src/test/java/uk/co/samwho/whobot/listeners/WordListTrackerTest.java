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
            .clock(clock())
            .addCallback((user) -> counter.incrementAndGet())
            .build();

        tracker.onMessageReceived(event(message(user1(), "hello world")));
        assertThat(counter.get()).isEqualTo(0);
        tracker.onMessageReceived(event(message(user2(), "foo foo foo")));
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    public void testCurrentlyTrackedUsers() {
        AtomicLong counter = new AtomicLong(0); // not really needed here but we need to supply a callback
        WordListTracker tracker = WordListTracker.builder()
                .wordList(WordList.from(Stream.of("foo")))
                .duration(Duration.ofSeconds(60))
                .threshold(2)
                .clock(clock())
                .addCallback((user) -> counter.incrementAndGet())
                .build();

        assertThat(tracker.currentlyTrackedUsers()).isEmpty();
        tracker.onMessageReceived(event(message(user1(), "hello world")));
        assertThat(tracker.currentlyTrackedUsers()).isEmpty();
        tracker.onMessageReceived(event(message(user2(), "foo")));
        assertThat(tracker.currentlyTrackedUsers()).containsExactly(user2());
        tracker.onMessageReceived(event(message(user1(), "foo")));
        assertThat(tracker.currentlyTrackedUsers()).containsExactly(user1(), user2());
    }
}
