package dequelite.app.history;

import dequelite.app.ui.UiService;
import dequelite.domain.chat_history.ChatHistory;
import dequelite.domain.chat_history.HistoryRepository;

import java.util.List;
import java.util.Optional;

public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UiService uiService;

    public HistoryService(HistoryRepository historyRepository, UiService uiService) {
        this.historyRepository = historyRepository;
        this.uiService = uiService;
    }

    public void allHistory() {
        List<ChatHistory> history = this.historyRepository.getHistory();

        for(ChatHistory chat : history) {
            this.uiService.showHistory(chat);
        }
    }

    public void oneChat(String chatId) {
        Optional<ChatHistory> chatHistory = this.historyRepository.findById(chatId);
        if(chatHistory.isEmpty()) {
            throw new RuntimeException("Chat not found");
        }

        this.uiService.showHistory(chatHistory.get());
    }
}
