package dequelite.app.ui;

import dequelite.domain.chat_history.ChatHistory;
import dequelite.domain.chat_history.ChatMessage;

public class UiService {
    public void clientMessage(String clientIp, String message) {
        System.out.print("\r");
        System.out.println("Message: " + clientIp + " " + message);
        System.out.print("> ");
    }

    public void myMessage(){
        System.out.print("> ");
    }

    public void showHistory(ChatHistory chatHistory) {
        System.out.println("Chat id: " + chatHistory.id());
        System.out.println("Created at: " + chatHistory.creationDate());
        System.out.println("Address: " + chatHistory.host() + ":" + chatHistory.port());
        System.out.println("Client ip: " + chatHistory.clientIp());
        System.out.println("Messages: ");
        for(ChatMessage message : chatHistory.messages()) {
            System.out.println(message.host() + ": " + message.text());
        }
        System.out.println("-----------------");
    }
}
