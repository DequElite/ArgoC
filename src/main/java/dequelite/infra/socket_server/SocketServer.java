package dequelite.infra.socket_server;

import dequelite.infra.chat_handler.ChatHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private final ChatHandler chatHandler;

    public SocketServer(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    public void run(String password, int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);

            Socket socket = serverSocket.accept();
            System.out.println("[SYSTEM] Client connected: " + socket.getInetAddress());
            this.chatHandler.handle(socket, password);

        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }
}
