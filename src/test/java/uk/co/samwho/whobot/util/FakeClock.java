package uk.co.samwho.whobot.util;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class FakeClock extends Clock {
    private Instant now;

    public static FakeClock at(Instant now) {
        return new FakeClock(now);
    }

    private FakeClock(Instant now) {
        this.now = now;
    }

    public void advance(Duration duration) {
        now = now.plus(duration);
    }

    public void rewind(Duration duration) {
        now = now.minus(duration);
    }

    @Override
    public Instant instant() {
        return now;
    }

    @Override
    public ZoneId getZone() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        throw new UnsupportedOperationException();
    }
}
