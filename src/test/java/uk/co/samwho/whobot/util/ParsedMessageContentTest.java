package uk.co.samwho.whobot.util;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.junit.Before;
import org.junit.Test;
import uk.co.samwho.whobot.guice.ConfigModule;

import static com.google.common.truth.Truth.assertThat;

public class ParsedMessageContentTest {
    @Inject
    @Named("prefix")
    private String prefix;

    @Before
    public void setUp() {
        Guice.createInjector(new ConfigModule()).injectMembers(this);
    }

    @Test
    public void testIsCommand() {
        assertThat(ParsedMessageContent.from("hello world").isCommand(prefix)).isFalse();
        assertThat(ParsedMessageContent.from(prefix + " hello world").isCommand(prefix)).isTrue();
    }

    @Test
    public void testGetCommandName() {
        assertThat(ParsedMessageContent.from(prefix + " hello world").getCommandName(prefix))
                .isEqualTo("hello");
    }

    @Test
    public void testGetCommandArgs() {
        assertThat(ParsedMessageContent.from(prefix + " hello there world").getCommandArgs())
                .containsExactly("there", "world");
    }
}
