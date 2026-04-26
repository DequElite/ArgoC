package dequelite.app.cli;

import dequelite.app.cli.ClientCommand.ClientCommand;
import dequelite.app.cli.ServerCommand.ServerCommand;
import dequelite.app.cli.dto.CliCommand;
import dequelite.infra.chat_handler.ChatHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CliParser {
    private Map<String, CliCommand> commands = new HashMap<>();

    public CliParser(ChatHandler chatHandler) {

        ServerCommand serverCommand = new ServerCommand(chatHandler);
        commands.put(serverCommand.commandFlag(), serverCommand);

        ClientCommand clientCommand = new ClientCommand(chatHandler);
        commands.put(clientCommand.commandFlag(), clientCommand);
    }

    public void parse(String[] args){
        if(args.length == 0) throw new RuntimeException("You are stupid");

        String command = args[0];

        if(command.startsWith("-") || command.startsWith("c")) {
            command = command.substring(1);
            System.out.println(command);
        } else {
            throw new RuntimeException("You are stupid");
        }

        CliCommand cliCommand = commands.get(command);

        cliCommand.execute(Arrays.copyOfRange(args, 1, args.length));
    }
}
