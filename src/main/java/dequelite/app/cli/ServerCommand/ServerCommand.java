package dequelite.app.cli.ServerCommand;

import dequelite.app.cli.ServerCommand.dto.ServerCommandParams;
import dequelite.app.cli.dto.CliCommand;
import dequelite.infra.chat_handler.ChatHandler;
import dequelite.infra.socket_server.SocketServer;

import java.util.HashMap;
import java.util.Map;

public class ServerCommand extends CliCommand {
    private final ChatHandler chatHandler;

    public ServerCommand(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @Override
    public String name() {
        return "server";
    }

    @Override
    public String commandFlag() {
        return "s";
    }

    @Override
    public void execute(String[] args) {
        ServerCommandParams params = new ServerCommandParams();
        params.setPassword("pw");
        params.setPort("1245");

        Map<String, String> inputParams = this.parseParams(args);

        for(Map.Entry<String, String> param : inputParams.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();

            if(key.equals("password")) {
                params.setPassword(value);
            } else if (key.equals("port")){
                params.setPort(value);
            }
        }

        SocketServer socketServer = new SocketServer(chatHandler);
        socketServer.run(params);
    }
}
