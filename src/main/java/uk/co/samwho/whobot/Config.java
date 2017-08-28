package uk.co.samwho.whobot;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Config {
    private static final String TOKEN_FILE = "token.txt";
    private static final String BAD_WORD_FILE = "bad_words.txt";
    private static final String OWNER_ID_FILE = "owner.txt";
    private static final String COMMAND_PREFIX = "?who";

    public static String token() {
        return resource(TOKEN_FILE);
    }

    public static long ownerId() {
        return Long.parseLong(resource(OWNER_ID_FILE));
    }

    public static String prefix() {
        return COMMAND_PREFIX;
    }

    public static Stream<String> badWords() {
        return resourceStream(BAD_WORD_FILE);
    }

    private static Stream<String> resourceStream(String path) {
        return new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(path))).lines();
    }

    private static String resource(String path) {
        return resourceStream(path).collect(Collectors.joining());
    }
}
