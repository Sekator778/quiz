package com.selator.quiz.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Record {
    private String question;
    private String a1;
    private String a2;
    private String a3;
    private String answer;
}
