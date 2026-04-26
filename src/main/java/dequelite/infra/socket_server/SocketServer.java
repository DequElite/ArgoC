package dequelite.infra.socket_server;

import dequelite.app.cli.ServerCommand.dto.ServerCommandParams;
import dequelite.infra.chat_handler.ChatHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private final ChatHandler chatHandler;

    public SocketServer(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    public void run(ServerCommandParams serverCommandParams) {

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(serverCommandParams.getPort()))) {

            System.out.println("Server started on port " + serverCommandParams.getPort());

            Socket socket = serverSocket.accept();
            System.out.println("[SYSTEM] Client connected: " + socket.getInetAddress());
            this.chatHandler.handle(socket, serverCommandParams.getPassword());

        } catch (Exception e) {
            System.out.println("Server error: " + e);
        }
    }
}
