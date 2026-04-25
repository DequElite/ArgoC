package dequelite.domain.chat_history;

import java.util.List;

public record ChatHistory(
        String id,
        long creationDate,
        String host,
        String port,
        String clientIp,
        List<ChatMessage> messages
) {
}
