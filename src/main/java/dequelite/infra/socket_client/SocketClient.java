package dequelite.infra.socket_client;

import dequelite.app.cli.ClientCommand.dto.ClientCommandParams;
import dequelite.infra.chat_handler.ChatHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketClient {
    private final ChatHandler chatHandler;

    public SocketClient(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    public void run(ClientCommandParams params) {

        try {
            Socket socket = new Socket(params.getHost(), Integer.parseInt(params.getPort()));

            this.chatHandler.handle(socket, params.getPassword());

        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}
