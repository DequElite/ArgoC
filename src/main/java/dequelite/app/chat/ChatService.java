package dequelite.app.chat;

import dequelite.app.crypto.CryptoService;
import dequelite.domain.chat_history.HistoryRepository;
import dequelite.app.ui.UiService;
import dequelite.domain.chat_history.ChatMessage;

import java.util.Scanner;

public class ChatService {
    private final CryptoService cryptoService;
    private final HistoryRepository historyRepository;
    private final UiService uiService;

    private final Scanner sc;

    public ChatService(CryptoService cryptoService, HistoryRepository historyRepository, UiService uiService, Scanner sc) {
        this.cryptoService = cryptoService;
        this.historyRepository = historyRepository;
        this.uiService = uiService;
        this.sc = sc;
    }

    public void handleIncoming(
            String chatId,
            String encoded,
            String password,
            String clientIp
    ) {
        String decodedMessage = this.cryptoService.decode(encoded, password);

        this.uiService.clientMessage(clientIp, decodedMessage);

        this.historyRepository.addMessage(
                chatId,
                new ChatMessage(clientIp, decodedMessage)
        );
    }

    public String handleOutgoing( String chatId,
                                  String password ) {
        this.uiService.myMessage();
        String message = sc.nextLine();

        String encoded = this.cryptoService.encrypt(message, password);
        this.historyRepository.addMessage(
                chatId,
                new ChatMessage("me", message)
        );

        return encoded;
    }
}
