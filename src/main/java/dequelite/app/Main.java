package dequelite.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import dequelite.app.chat.ChatService;
import dequelite.app.crypto.CryptoService;
import dequelite.app.history.HistoryService;
import dequelite.app.ui.UiService;
import dequelite.infra.chat_handler.ChatHandler;
import dequelite.infra.socket_client.SocketClient;
import dequelite.infra.socket_server.SocketServer;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage:");
            System.out.println("server <password> <host> <port>");
            System.out.println("client <password> <host> <port>");
            return;
        }

        String mode = args[0];
        String password = args[1];
        String host = args[2];
        int port = Integer.parseInt(args[3]);

        CryptoService cryptoService = new CryptoService();

        ObjectMapper mapper = new ObjectMapper();
        HistoryService historyService = new HistoryService(mapper);

        UiService uiService = new UiService();
        ChatService chatService = new ChatService(cryptoService, historyService, uiService);

        ChatHandler chatHandler = new ChatHandler(historyService, chatService);

        switch (mode) {
            case "server" -> {
                SocketServer socketServer = new SocketServer(chatHandler);
                socketServer.run(password, port);
            }
            case "client" -> {
                SocketClient socketClient = new SocketClient(chatHandler);
                socketClient.run(password, host, port);
            }
            default -> System.out.println("Unknown mode: " + mode);
        }
    }
}
