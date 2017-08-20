package uk.co.samwho.whobot.util;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.Duration;

@RunWith(JUnit4.class)
public class EventTrackerTest extends TestWithTime {
    @Test
    public void testSimpleEvent() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .build();

        tracker.inc(1, minutesAgo(5));

        assertThat(tracker.count()).isEqualTo(1);
    }

    @Test
    public void testMultipleEvents() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .build();

        tracker.inc(1, minutesAgo(5));
        tracker.inc(1, minutesAgo(10));

        assertThat(tracker.count()).isEqualTo(2);
    }

    @Test
    public void testAddEventNotInRange() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .build();

        tracker.inc(1, minutesAgo(61));

        assertThat(tracker.count()).isEqualTo(0);
    }

    @Test
    public void testAddingEventsRightNow() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .clock(clock())
                .build();

        tracker.inc(1, now());
        assertThat(tracker.count()).isEqualTo(1);
    }

    @Test
    public void testEventFallingOutOfRange() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .clock(clock())
                .build();

        tracker.inc(1, minutesAgo(0));
        advance(Duration.ofMinutes(61));

        assertThat(tracker.count()).isEqualTo(0);
    }

    @Test
    public void testReset() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .build();

        tracker.inc(1, minutesAgo(5));
        assertThat(tracker.count()).isEqualTo(1);

        tracker.reset();
        assertThat(tracker.count()).isEqualTo(0);
    }

    @Test
    public void testIncrementingByMoreThan1() {
        EventTracker tracker = EventTracker.builder()
                .duration(Duration.ofHours(1))
                .clock(clock())
                .build();

        tracker.inc(10, now());
        assertThat(tracker.count()).isEqualTo(10);
    }
}
