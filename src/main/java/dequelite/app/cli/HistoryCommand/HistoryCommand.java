package dequelite.app.cli.HistoryCommand;

import dequelite.app.cli.HistoryCommand.dto.HistoryCommandParams;
import dequelite.app.cli.HistoryCommand.dto.HistoryParamShow;
import dequelite.app.cli.dto.CliCommand;
import dequelite.app.history.HistoryService;

import java.util.Map;

public class HistoryCommand extends CliCommand {
    private final HistoryService historyService;

    public HistoryCommand(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Override
    public String name() {
        return "history";
    }

    @Override
    public String commandFlag() {
        return "h";
    }

    @Override
    public void execute(String[] args) {
        HistoryCommandParams params = new HistoryCommandParams();
        params.setChatId("");
        params.setShow(HistoryParamShow.ALL);

        Map<String, String> inputParams = this.parseParams(args);

        for(Map.Entry<String, String> param : inputParams.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();

            if(key.equals("show")) {
                params.setShow(HistoryParamShow.valueOf(value));
            } else if (key.equals("id")){
                params.setChatId(value);
            }
        }

        this.checkShow(params);
    }

    private void checkShow(HistoryCommandParams params) {
        if(params.getShow().equals(HistoryParamShow.ALL)) {
            this.historyService.allHistory();
        } else if (params.getShow().equals(HistoryParamShow.ONE)) {
            this.historyService.oneChat(params.getChatId());
        }
    }
}
