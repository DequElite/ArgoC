package dequelite.app.chat;

import dequelite.app.crypto.CryptoService;
import dequelite.app.history.HistoryService;
import dequelite.app.ui.UiService;
import dequelite.domain.chat_history.ChatMessage;

import java.util.Scanner;

public class ChatService {
    private final CryptoService cryptoService;
    private final HistoryService historyService;
    private final UiService uiService;

    public ChatService(CryptoService cryptoService, HistoryService historyService, UiService uiService) {
        this.cryptoService = cryptoService;
        this.historyService = historyService;
        this.uiService = uiService;
    }

    public void handleIncoming(
            String chatId,
            String encoded,
            String password,
            String clientIp
    ) {
        String decodedMessage = this.cryptoService.decode(encoded, password);

        this.uiService.clientMessage(clientIp, decodedMessage);

        this.historyService.addMessage(
                chatId,
                new ChatMessage(clientIp, decodedMessage)
        );
    }

    public String handleOutgoing( String chatId,
                                  String password ) {
        try (Scanner sc = new Scanner(System.in)) {
            this.uiService.myMessage();
            String message = sc.nextLine();

            String encoded = this.cryptoService.encrypt(message, password);
            this.historyService.addMessage(
                    chatId,
                    new ChatMessage("me", message)
            );

            return encoded;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
