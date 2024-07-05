package org.javadominicano.jcli;

public class TerminalUtils {

    public static String bold(String text, String world) {
        return text.replace(world, TerminalConstants.ANSI_BOLD+world+TerminalConstants.ANSI_RESET);
    }
}
