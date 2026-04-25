package dequelite.app.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import dequelite.domain.chat_history.ChatHistory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryService {
    private final ObjectMapper mapper;
    private final File file = new File("history.json");

    private List<ChatHistory> history = new ArrayList<>();

    public HistoryService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private void checkFile() throws IOException {
        if(file.exists()){
            readFile();
        } else {
            createFile();
        }
    }

    private void readFile(){
        try {
            ChatHistory[] history = mapper.readValue(file, ChatHistory[].class);
            this.history.addAll(Arrays.asList(history));
        } catch (IOException e) {
            System.out.println("Error during file reading");
        }
    }

    private void createFile() {
        try {
            mapper.writeValue(file, new ChatHistory[0]);
        } catch (IOException e) {
            System.out.println("Error during file creating");
        }
    }
}