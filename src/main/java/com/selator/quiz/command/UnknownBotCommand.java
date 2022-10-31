package com.selator.quiz.command;

import com.selator.quiz.data.Answer;
import com.selator.quiz.icon.Icon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownBotCommand implements BotCommand {

    @Autowired
    public UnknownBotCommand(){
    }

    public static final String UNKNOWN_MESSAGE = "not_understand";

    @Override
    public Answer execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId( update.getMessage().getChatId().toString());
        sendMessage.setText(Icon.SHRUG.get() + UNKNOWN_MESSAGE);
        return new Answer(sendMessage, true);
    }
}
