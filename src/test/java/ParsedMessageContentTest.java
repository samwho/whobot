import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

import uk.co.samwho.whobot.Config;
import uk.co.samwho.whobot.ParsedMessageContent;

public class ParsedMessageContentTest {
    @Test
    public void testIsCommand() {
        assertThat(ParsedMessageContent.from("hello world").isCommand()).isFalse();
        assertThat(ParsedMessageContent.from(Config.COMMAND_PREFIX + " hello world").isCommand()).isTrue();
    }

    @Test
    public void testGetCommandName() {
        assertThat(ParsedMessageContent.from(Config.COMMAND_PREFIX + " hello world").getCommandName())
                .isEqualTo("hello");
    }

    @Test
    public void testGetCommandArgs() {
        assertThat(ParsedMessageContent.from(Config.COMMAND_PREFIX + " hello there world").getCommandArgs())
                .containsExactly("there", "world");
    }
}
