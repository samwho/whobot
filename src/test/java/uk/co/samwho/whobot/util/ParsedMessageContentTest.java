package uk.co.samwho.whobot.util;

import org.junit.Test;
import uk.co.samwho.whobot.Config;

import static com.google.common.truth.Truth.assertThat;

public class ParsedMessageContentTest {
    @Test
    public void testIsCommand() {
        assertThat(ParsedMessageContent.from("hello world").isCommand()).isFalse();
        assertThat(ParsedMessageContent.from(Config.prefix() + " hello world").isCommand()).isTrue();
    }

    @Test
    public void testGetCommandName() {
        assertThat(ParsedMessageContent.from(Config.prefix() + " hello world").getCommandName())
                .isEqualTo("hello");
    }

    @Test
    public void testGetCommandArgs() {
        assertThat(ParsedMessageContent.from(Config.prefix() + " hello there world").getCommandArgs())
                .containsExactly("there", "world");
    }
}
