package dequelite.app.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import dequelite.domain.chat_history.ChatHistory;
import dequelite.domain.chat_history.ChatMessage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HistoryService {
    private final ObjectMapper mapper;
    private final File file = new File("history.json");

    private List<ChatHistory> history = new ArrayList<>();

    public HistoryService(ObjectMapper mapper) {
        this.mapper = mapper;

        checkFile();
    }

    private void checkFile()  {
        try {
            if(!file.exists()){
                createFile();
            } else {
                readFile();
            }
        } catch (Exception e) {
            System.out.println("Error checking history: " + e);
        }
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, history);
        } catch (IOException e) {
            System.out.println("Error saving history: " + e);
        }
    }

    private String getChatId(String host, String port, String clientIp) {
        return host + ":" + port + ":" + clientIp;
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

    public synchronized String saveChat(String host, String port, String clientIp){
        String id = this.getChatId(host, port, clientIp);

        Optional<ChatHistory> chatHistory = this.history.stream()
                .filter(h -> h.id().equals(id))
                .findFirst();
        if(chatHistory.isPresent()) {
            return chatHistory.get().id();
        }

        ChatHistory newChatHistory = new ChatHistory(
                id,
                System.currentTimeMillis(),
                host,
                port,
                clientIp,
                List.of()
        );

        history.add(newChatHistory);

        saveToFile();

        return id;
    }

    public synchronized void addMessage(String id, ChatMessage message) {
        for (int i = 0; i < history.size(); i++) {
            ChatHistory chat = history.get(i);

            if (chat.id().equals(id)) {

                List<ChatMessage> messages = new ArrayList<>(chat.messages());
                messages.add(message);

                ChatHistory updated = new ChatHistory(
                        chat.id(),
                        chat.creationDate(),
                        chat.host(),
                        chat.port(),
                        chat.clientIp(),
                        messages
                );

                history.set(i, updated);
                saveToFile();
                return;
            }
        }
    }
}