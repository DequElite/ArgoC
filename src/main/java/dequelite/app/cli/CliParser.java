package dequelite.app.cli;

import dequelite.app.cli.ClientCommand.ClientCommand;
import dequelite.app.cli.HistoryCommand.HistoryCommand;
import dequelite.app.cli.ServerCommand.ServerCommand;
import dequelite.app.cli.dto.CliCommand;
import dequelite.app.history.HistoryService;
import dequelite.app.ui.UiService;
import dequelite.infra.chat_handler.ChatHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CliParser {
    private Map<String, CliCommand> commands = new HashMap<>();

    public CliParser(ChatHandler chatHandler, HistoryService historyService) {

        ServerCommand serverCommand = new ServerCommand(chatHandler);
        commands.put(serverCommand.commandFlag(), serverCommand);

        ClientCommand clientCommand = new ClientCommand(chatHandler);
        commands.put(clientCommand.commandFlag(), clientCommand);

        HistoryCommand historyCommand = new HistoryCommand(historyService);
        commands.put(historyCommand.commandFlag(), historyCommand);
    }

    public void parse(String[] args){
        if(args.length == 0) throw new RuntimeException("You are stupid");

        String command = args[0];

        if(command.startsWith("-") || command.startsWith("c")) {
            command = command.substring(1);
        } else {
            throw new RuntimeException("You are stupid");
        }

        CliCommand cliCommand = commands.get(command);

        cliCommand.execute(Arrays.copyOfRange(args, 1, args.length));
    }
}
