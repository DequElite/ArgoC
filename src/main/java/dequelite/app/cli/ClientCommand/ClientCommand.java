package dequelite.app.cli.ClientCommand;

import dequelite.app.cli.ClientCommand.dto.ClientCommandParams;
import dequelite.app.cli.dto.CliCommand;
import dequelite.infra.chat_handler.ChatHandler;
import dequelite.infra.socket_client.SocketClient;

import java.util.Map;

public class ClientCommand extends CliCommand {
    private final ChatHandler chatHandler;

    public ClientCommand(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    @Override
    public String name() {
        return "client";
    }

    @Override
    public String commandFlag() {
        return "c";
    }

    @Override
    public void execute(String[] args) {
        ClientCommandParams params = new ClientCommandParams();
        params.setHost("localhost");
        params.setPassword("pw");
        params.setPort("1245");

        Map<String, String> inputParams = this.parseParams(args);

        for(Map.Entry<String, String> param : inputParams.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();

            switch (key) {
                case "password" -> params.setPassword(value);
                case "port" -> params.setPort(value);
                case "host" -> params.setHost(value);
            }
        }

        SocketClient socketClient = new SocketClient(chatHandler);
        socketClient.run(params);
    }
}
