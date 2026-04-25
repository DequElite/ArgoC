package dequelite.infra.socket_client;

import dequelite.infra.chat_handler.ChatHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketClient {
    private final ChatHandler chatHandler;

    public SocketClient(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    public void run(String password, String host, int port) {

        try {
            Socket socket = new Socket(host, port);

            this.chatHandler.handle(socket, password);

        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}
