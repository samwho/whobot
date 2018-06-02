package uk.co.samwho.whobot.listeners;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import uk.co.samwho.whobot.util.EventTracker;
import uk.co.samwho.whobot.util.WordList;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * A ListenerAdapter that tracks how often users say words in a given word list and fires callbacks when they say more
 * than a given number of times in a given amount of time.
 * <p>
 * <p>Example:
 * <p>
 * <pre>
 *   WordListTracker.builder()
 *     .wordList(WordList.from(Stream.of("foo")))
 *     .duration(Duration.ofSeconds(60))
 *     .threshold(5)
 *     .addCallback((user) -> System.out.println(user.getName() + " is saying foo a lot!"))
 *     .build();
 * </pre>
 */
public final class WordListTracker extends ListenerAdapter {
    private static final Logger logger = Logger.getLogger(WordListTracker.class.getName());

    private final WordList wordList;
    private final Duration duration;
    private final int threshold;
    private final Clock clock;
    private final Collection<Consumer<User>> callbacks;
    private final Cache<User, EventTracker> cache;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private WordList wordList;
        private Duration duration;
        private int threshold = 0;
        private Clock clock;
        private Collection<Consumer<User>> callbacks = Lists.newLinkedList();

        public Builder wordList(WordList wordList) {
            this.wordList = wordList;
            return this;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder threshold(int threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder clock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder addCallback(Consumer<User> callback) {
            this.callbacks.add(callback);
            return this;
        }

        public WordListTracker build() {
            Preconditions.checkNotNull(wordList, "you must supply a wordList");
            Preconditions.checkNotNull(duration, "you must supply a duration");
            Preconditions.checkArgument(threshold > 0, "you must supply a non-zero threshold");
            Preconditions.checkArgument(!callbacks.isEmpty(), "you must supply at least one callback");

            if (clock == null) {
                clock = Clock.systemDefaultZone();
            }

            return new WordListTracker(wordList, duration, threshold, clock, Collections.unmodifiableCollection(callbacks));
        }
    }

    private WordListTracker(WordList wordList, Duration duration, int threshold, Clock clock, Collection<Consumer<User>> callbacks) {
        this.wordList = wordList;
        this.duration = duration;
        this.threshold = threshold;
        this.clock = clock;
        this.callbacks = callbacks;

        this.cache = CacheBuilder.newBuilder()
                .expireAfterAccess(duration.getSeconds(), TimeUnit.SECONDS)
                .removalListener((info) -> logger.fine(() -> "removed key " + info.getKey() + " (cause: " + info.getCause() + ")"))
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        EventTracker tracker;
        try {
            tracker = cache.get(
                    event.getAuthor(), () -> EventTracker.builder().clock(clock).duration(duration).build());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        int numMatches = wordList.numMatches(event.getMessage().getContentRaw());
        Instant time = event.getMessage().getCreationTime().toInstant();
        tracker.inc(numMatches, time);

        logger.fine(() -> "added " + numMatches + " to counter for " + event.getAuthor().getName() + " at " + time);

        if (tracker.count() >= threshold) {
            for (Consumer<User> callback : callbacks) {
                callback.accept(event.getAuthor());
            }
            tracker.reset();
        }
    }

    /**
     * Returns users that are currently on the radar for this word list. It doesn't necessarily mean that they have
     * triggered their threshold, just that they've mentioned some of the words in your list recently.
     */
    public Collection<User> currentlyTrackedUsers() {
        Set<User> users = Sets.newHashSet();
        cache.asMap().forEach((user, tracker) -> {
            if (tracker.count() > 0) {
               users.add(user);
            }
        });
        return users;
    }
}
