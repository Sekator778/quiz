package com.selator.quiz.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Data
@AllArgsConstructor
@Getter
public class Answer {
    private final SendMessage sendMessage;
    private final boolean isDone;


}
