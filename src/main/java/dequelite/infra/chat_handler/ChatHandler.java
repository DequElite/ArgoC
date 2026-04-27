package dequelite.infra.chat_handler;

import dequelite.app.chat.ChatService;
import dequelite.domain.chat_history.HistoryRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatHandler {
    private final HistoryRepository historyRepository;
    private final ChatService chatService;

    private String chatId;

    public ChatHandler(HistoryRepository historyRepository, ChatService chatService) {
        this.historyRepository = historyRepository;
        this.chatService = chatService;
    }

    public void handle(Socket socket, String password) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String clientIp = socket.getInetAddress().getHostAddress();
            String host = socket.getLocalAddress().getHostAddress();
            int port = socket.getPort();

            this.chatId = historyRepository.saveChat(host, String.valueOf(port), clientIp);

            new Thread(() -> receiveMessage(socket, in, password)).start();
            new Thread(() -> sendMessage(out, password)).start();

        } catch (Exception e) {
            System.out.println("Handler error: " + e);
        }
    }

    public void receiveMessage(Socket socket, BufferedReader in, String password) {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                this.chatService.handleIncoming(this.chatId, msg, password, socket.getInetAddress().getHostAddress());
            }
        } catch (IOException e) {
            System.out.println("Connection closed: " + e);
        }
    }

    public void sendMessage(PrintWriter out, String password) {

        while (true) {
            out.println(this.chatService.handleOutgoing(this.chatId, password));
        }
    }
}
