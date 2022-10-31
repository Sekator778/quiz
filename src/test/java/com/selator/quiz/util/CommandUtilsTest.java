package com.selator.quiz.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommandUtilsTest {

    @Test
    void buildCommandName() {
        String commandName = CommandUtils.buildCommandName("/start");
        assertThat(commandName).isEqualTo("startBotCommand");
    }
    @Test
    void whenEmptyCommandName() {
        String commandName = CommandUtils.buildCommandName("");
        assertThat(commandName).isEqualTo("UnknownBotCommand");
    }
}