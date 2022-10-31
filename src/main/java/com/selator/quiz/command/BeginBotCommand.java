package com.selator.quiz.command;

import com.selator.quiz.data.Answer;
import com.selator.quiz.data.Record;
import com.selator.quiz.reader.ReadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeginBotCommand implements BotCommand {
    private ArrayList<Record> records;
    private ReadFile readFile;
    private static int i = 0;
    @Autowired
    public BeginBotCommand() {
    }

    @Override
    public Answer execute(Update update) {
        readFile = new ReadFile();
        long telegramId;
        if (update.hasCallbackQuery()) {
            telegramId = update.getCallbackQuery().getFrom().getId();
        } else {
            telegramId = update.getMessage().getChatId();
        }
        readFile.fillDateMap(telegramId);
        records = readFile.getData().get(telegramId);
        return createQuiz(telegramId);
    }

    private Answer createQuiz(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> button = new ArrayList<>();
        button.add(InlineKeyboardButton.builder()
                .text(records.get(i).getA1())
                .callbackData("a1")
                .build());
        button.add(InlineKeyboardButton.builder()
                .text(records.get(i).getA2())
                .callbackData("a2")
                .build());
        button.add(InlineKeyboardButton.builder()
                .text(records.get(i).getA3())
                .callbackData("a3")
                .build());
        keyboard.add(button);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(records.get(i).getQuestion());
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        i++;
        return new Answer(sendMessage, true);
    }
}
