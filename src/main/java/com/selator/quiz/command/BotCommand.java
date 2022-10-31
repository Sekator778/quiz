package com.selator.quiz.command;

import com.selator.quiz.data.Answer;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Command interface for handling telegram-bot commands.
 */
public interface BotCommand {

    /**
     * Main method, which is executing command logic.
     *
     * @param update provided {@link Update} object with all the needed data for command.
     */
    Answer execute(Update update);
}
