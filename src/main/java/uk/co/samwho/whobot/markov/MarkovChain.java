package uk.co.samwho.whobot.markov;

import com.google.common.base.Splitter;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MarkovChain {
    private static final Splitter tokenizer = Splitter.on(Pattern.compile("\\s+"));

    private final Map<String, Map<String, AtomicLong>> pairWeights = new HashMap<>();

    public void add(String s) {
        pairs(s).forEach(
                pair -> pairWeights
                    .computeIfAbsent(pair.getFirst(), k -> new HashMap<>())
                    .computeIfAbsent(pair.getSecond(), k -> new AtomicLong(0))
                    .incrementAndGet());
    }

    private Stream<Pair<String, String>> pairs(String s) {
        return pairs(tokenizer.split(s));
    }

    private <T> Stream<Pair<T, T>> pairs(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        return Streams.stream(new Iterator<Pair<T, T>>() {
            T a = null;
            T b = null;

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Pair<T, T> next() {
                a = b;
                b = iterator.next();
                if (a != null && b != null) {
                    return Pair.of(a, b);
                } else {
                    return next();
                }
            }
        });
    }

    private static final class Pair<A, B> {
        private final A a;
        private final B b;

        static <A, B> Pair<A, B> of(A a, B b) {
            return new Pair<>(a, b);
        }

        private Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        A getFirst() {
            return a;
        }

        B getSecond() {
            return b;
        }
    }
}
