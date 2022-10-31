package com.selator.quiz.command;

import com.selator.quiz.data.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Start {@link BotCommand}.
 */
@Component
public class StartBotCommand implements BotCommand {

    @Autowired
    public StartBotCommand(){
    }

    @Override
    public Answer execute(Update update) {
        long telegramId;
        if (update.hasCallbackQuery()) {
            telegramId = update.getCallbackQuery().getFrom().getId();
        } else {
            telegramId = update.getMessage().getChatId();
        }
        return createStartMenu(telegramId);
    }

    private Answer createStartMenu(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> button = new ArrayList<>();
        createButton(chatId, "create_service", "create_service", button);
        createButton(chatId, "search_service", "search_service", button);
        keyboard.add(button);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("choose");
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return new Answer(sendMessage, true);
    }

    private void createButton(Long id, String nameButton,
                              String dataButton, List<InlineKeyboardButton> button) {
        button.add(InlineKeyboardButton.builder()
                .text("Button")
                .callbackData(dataButton)
                .build());
    }
}
