package com.selator.quiz.util;

import org.apache.commons.lang3.StringUtils;

public final class CommandUtils {

    public static final String BOT_COMMAND = "BotCommand";

    private CommandUtils() {
        /* intentionally */
    }

    public static String buildCommandName(String botCommand) {
        if (StringUtils.isBlank(botCommand)) {
            return "Unknown" + BOT_COMMAND;
        }
        if (botCommand.charAt(0) != '/') {
            return botCommand + BOT_COMMAND;
        }
        return StringUtils.substring(botCommand, 1) + BOT_COMMAND;
    }
}
