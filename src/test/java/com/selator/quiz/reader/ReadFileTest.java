package com.selator.quiz.reader;

import com.selator.quiz.data.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReadFileTest {
    private Path tempFile;

    @BeforeEach
    void init() {
        // create a temporary file
        try {
            tempFile = Files.createTempFile(null, null);
            String content = "Line 1;Line 2;Line 3";
            Files.write(tempFile, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void fillDateMap() {
        ReadFile readFile = new ReadFile(tempFile.toString());
        readFile.fillDateMap(1L);
        ArrayList<Record> records = readFile.getData().get(1L);
        assertThat(records.get(0).getA1()).isEqualTo("Line 1");
        assertThat(records.get(0).getA2()).isEqualTo("Line 2");
        assertThat(records.get(0).getA3()).isEqualTo("Line 3");
    }
}
