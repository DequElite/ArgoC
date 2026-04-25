package dequelite.infra.chat_handler;

import dequelite.app.crypto.CryptoService;
import dequelite.app.history.HistoryService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatHandler {
    //todo: написать сохранение сообщений и чатов в историю


    private final CryptoService cryptoService;
    private final HistoryService historyService;

    public ChatHandler(CryptoService cryptoService, HistoryService historyService) {
        this.cryptoService = cryptoService;
        this.historyService = historyService;
    }

    public void handle(Socket socket, String password) {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

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
                String decodedMsg = this.cryptoService.decode(msg, password);

                System.out.print("\r");
                System.out.println("Message: " + socket.getInetAddress() + " " + decodedMsg);
                System.out.print("> ");
            }
        } catch (IOException e) {
            System.out.println("Connection closed: " + e);
        }
    }

    public void sendMessage(PrintWriter out, String password) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String msg = sc.nextLine();
            String encodedMsg = this.cryptoService.encrypt(msg, password);
            out.println(encodedMsg);
        }
    }
}
