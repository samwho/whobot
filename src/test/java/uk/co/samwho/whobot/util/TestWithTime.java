package uk.co.samwho.whobot.util;

import org.junit.Before;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class TestWithTime {
    private FakeClock clock = FakeClock.now();

    protected Instant minutesAgo(int mins) {
        return clock.instant().minus(Duration.ofMinutes(mins));
    }

    protected Instant now() {
        return clock.instant();
    }

    protected Clock clock() {
        return clock;
    }

    protected void advance(Duration duration) {
        clock.advance(duration);
    }

    protected void rewind(Duration duration) {
        clock.rewind(duration);
    }

    @Before
    public void setUp() {
        clock.reset();
    }
}
