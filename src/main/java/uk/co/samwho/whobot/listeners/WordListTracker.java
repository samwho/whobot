package uk.co.samwho.whobot.listeners;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import uk.co.samwho.whobot.util.EventTracker;
import uk.co.samwho.whobot.util.WordList;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * A ListenerAdapter that tracks how often users say words in a given word list and fires callbacks when they say more
 * than a given number of times in a given amount of time.
 *
 * <p>Example:
 *
 * <pre>
 *   WordListTracker.builder()
 *     .wordList(WordList.from(Stream.of("foo"))
 *     .duration(Duration.ofSeconds(60))
 *     .threshold(5)
 *     .callback((user) -> System.out.println(user.getName() + " is saying foo a lot!")
 *     .build();
 * </pre>
 */
public final class WordListTracker extends ListenerAdapter {
    private final WordList wordList;
    private final Duration duration;
    private final int threshold;
    private final Collection<Consumer<User>> callbacks;
    private final Cache<User, EventTracker> cache;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private WordList wordList;
        private Duration duration;
        private int threshold = 0;
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

        public Builder addCallback(Consumer<User> callback) {
            this.callbacks.add(callback);
            return this;
        }

        public WordListTracker build() {
            Preconditions.checkNotNull(wordList, "you must supply a wordList");
            Preconditions.checkNotNull(duration, "you must supply a duration");
            Preconditions.checkArgument(threshold > 0, "you must supply a non-zero threshold");
            Preconditions.checkArgument(!callbacks.isEmpty(), "you must supply at least one callback");

            return new WordListTracker(wordList, duration, threshold, Collections.unmodifiableCollection(callbacks));
        }
    }

    private WordListTracker(WordList wordList, Duration duration, int threshold, Collection<Consumer<User>> callbacks) {
        this.wordList = wordList;
        this.duration = duration;
        this.threshold = threshold;
        this.callbacks = callbacks;

        this.cache = CacheBuilder.newBuilder()
                .expireAfterAccess(duration.getSeconds(), TimeUnit.SECONDS)
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        EventTracker tracker;
        try {
            tracker = cache.get(event.getAuthor(), () -> EventTracker.over(duration));
        } catch (ExecutionException e) {
            return;
        }

        tracker.inc(
                wordList.numMatches(event.getMessage().getContent()),
                event.getMessage().getCreationTime().toInstant());

        /**
         * This currently triggers too much. If you go over the threshold in a given time window once, it will continue
         * to fire every other time you mention a word in the word list. It would be better if the counter reset after
         * the event fired.
         */
        if (tracker.count() > threshold) {
            for (Consumer<User> callback : callbacks) {
                callback.accept(event.getAuthor());
            }
        }
    }
}
