package com.selator.quiz.reader;

import com.selator.quiz.data.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ReadFile {
    private final String filePath;
    private final Map<Long, ArrayList<Record>> data = new HashMap<>();

    public ReadFile() {
        this.filePath = "data.txt";
    }

    public ReadFile(String filePath) {
        this.filePath = filePath;
    }

    public boolean fillDateMap(Long telegramId) {
        try {
            Files.lines(Path.of(filePath))
                    .map(l -> l.split(";"))
                    .forEach(a -> createGraph(telegramId, new Record(a[0], a[1], a[2])));
        } catch (IOException exception) {
            log.error("file exist {}", exception.getMessage());
            return false;
        }
        return true;
    }

    private void createGraph(Long telegramId, Record record) {
        ArrayList<Record> arrayList = data.computeIfAbsent(telegramId, k -> new ArrayList<>());
        arrayList.add(record);
    }

    public Map<Long, ArrayList<Record>> getData() {
        return data;
    }
}
