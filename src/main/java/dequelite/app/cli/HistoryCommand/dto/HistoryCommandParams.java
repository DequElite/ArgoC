package dequelite.app.cli.HistoryCommand.dto;

public class HistoryCommandParams {
    private HistoryParamShow show;
    private String chatId;

    public HistoryParamShow getShow() {
        return show;
    }

    public void setShow(HistoryParamShow show) {
        this.show = show;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
