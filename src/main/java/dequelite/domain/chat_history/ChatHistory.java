package dequelite.domain.chat_history;

import java.util.List;

public record ChatHistory(
        String creationDate,
        String host,
        String port,
        String clientIp,
        String serverIp,
        List<ChatMessage> messages
) {
}
