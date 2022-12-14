package com.selator.quiz;


import com.selator.quiz.command.BotCommand;
import com.selator.quiz.command.UnknownBotCommand;
import com.selator.quiz.data.Answer;
import com.selator.quiz.util.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    private final Map<String, BotCommand> iCommands;

    @Autowired
    public Bot(Map<String, BotCommand> iCommands) {
        this.iCommands = iCommands;
    }

    @Value("${telegram.bot.name}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasCallbackQuery()) {
                callbackQueryHandler(update);
            } else if (update.getMessage().hasText() && update.getMessage().isCommand()) {
                commandHandler(update);
            } else {
                textHandler(update);
            }
        } catch (Exception ex) {
            log.warn("error onUpdateReceived {}", ex.getLocalizedMessage());
        }
    }

    private void textHandler(Update update) {
    }

    private void commandHandler(Update update) {
        String messageText = update.getMessage().getText().trim();
        String commandIdentifier = CommandUtils.buildCommandName(messageText);
        log.info("new command here commandHandler {}", commandIdentifier);
        BotCommand botCommand = getBotCommand(commandIdentifier);
        Answer execute = botCommand.execute(update);
        log.info("answer {}", execute);
        try {
            this.execute(execute.getSendMessage());
        } catch (TelegramApiException e) {
            log.error("send error {}", e.getMessage());
        }
    }

    private BotCommand getBotCommand(String botCommand) {
        return iCommands.getOrDefault(botCommand,
                iCommands.get(UnknownBotCommand.class.getSimpleName()));
    }

    private void callbackQueryHandler(Update update) {
        String data = update.getCallbackQuery().getData();
        log.info("update {}", data);
        String commandIdentifier = CommandUtils.buildCommandName(data);
        log.info("new command here callbackQueryHandler {}", commandIdentifier);
        BotCommand botCommand = getBotCommand(commandIdentifier);
        Answer execute = botCommand.execute(update);
        log.info("answer {}", execute);
        try {
            this.execute(execute.getSendMessage());
        } catch (TelegramApiException e) {
            log.error("send error {}", e.getMessage());
        }

    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
